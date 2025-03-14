package com.schedulerx.tasks;

import static com.schedulerx.utils.Constants.CRON_PARSER;
import static com.schedulerx.utils.Constants.JOB_COUNT;
import static com.schedulerx.utils.Constants.SECONDS_AGO;
import static com.schedulerx.utils.Constants.SECONDS_LATER;
import static com.schedulerx.utils.Constants.THREAD_POOL_SIZE;

import com.schedulerx.exception.SchedulerXError;
import com.schedulerx.exception.SchedulerXException;
import com.schedulerx.models.Command;
import com.schedulerx.models.Job;
import com.schedulerx.models.JobStatus;
import com.schedulerx.repository.JobRepository;
import com.schedulerx.repository.ScheduleFetcher;
import com.schedulerx.utils.CommandFactory;
import com.schedulerx.utils.CronUtils;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/** Job scheduler that executes scheduled jobs. */
@Component
@Slf4j
public class JobScheduler {
  private final JobRepository jobRepository;
  private final ExecutorService executorService;
  private final ScheduleFetcher scheduleFetcher;

  public JobScheduler(final JobRepository jobRepository, final ScheduleFetcher scheduleFetcher) {
    this.jobRepository = jobRepository;
    this.executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
    this.scheduleFetcher = scheduleFetcher;
  }

  /** Executes scheduled jobs. */
  @Scheduled(cron = "0 * * * * *")
  public void executeScheduledJobs() {
    val now = LocalDateTime.now(ZoneOffset.UTC);
    val jobsToExecute =
        jobRepository.findByTimestampAndStatus(
            now.minusSeconds(SECONDS_AGO),
            now.plusSeconds(SECONDS_LATER),
            JobStatus.SCHEDULED.toString());

    for (val job : jobsToExecute) {
      if (jobRepository.lockJob(job.getId()) == 1) {
        executorService.submit(() -> executeJob(job));
      }
    }
  }

  private void executeJob(final Job job) {
    try {
      val commandOpt = scheduleFetcher.findById(job.getCommandId());
      if (commandOpt.isEmpty()) {
        log.error("Command not found for job ID: {}", job.getId());
        return;
      }
      val command = commandOpt.get();
      log.info("Executing job with ID: {}", job.getId());
      executeCommand(command);
      updateJobStatus(job, JobStatus.COMPLETED);
      scheduleNextInstance(command);
    } catch (final Exception e) {
      log.error("Failed to execute job with ID: {}", job.getId(), e);
    }
    if (JobStatus.RUNNING.name().equals(job.getStatus())) {
      updateJobStatus(job, JobStatus.FAILED);
    }
  }

  private void updateJobStatus(final Job job, final JobStatus jobStatus) {
    job.setStatus(jobStatus.toString());
    jobRepository.save(job);
  }

  private void executeCommand(final Command command) {
    CommandFactory.getCommand(command.getType(), String.valueOf(command.getId()))
        .ifPresentOrElse(
            executor -> executor.execute(command.getParams()),
            () -> {
              throw new SchedulerXException(SchedulerXError.COMMAND_NOT_FOUND);
            });
  }

  private void scheduleNextInstance(final Command command) {
    try {
      val cron = CRON_PARSER.parse(command.getSchedule());
      val nextExecutionTime = CronUtils.getNextExecutionTimes(cron, JOB_COUNT).get(JOB_COUNT - 1);
      val nextJob = new Job();
      nextJob.setCommandId(command.getId());
      nextJob.setTimestamp(nextExecutionTime);
      nextJob.setStatus(JobStatus.SCHEDULED.toString());
      jobRepository.save(nextJob);
    } catch (final Exception e) {
      log.error("Failed to schedule next instance for command ID: {}", command.getId(), e);
    }
  }
}
