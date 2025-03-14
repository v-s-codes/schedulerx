package com.schedulerx.tasks;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.schedulerx.models.Command;
import com.schedulerx.models.Job;
import com.schedulerx.repository.JobRepository;
import com.schedulerx.repository.ScheduleFetcher;
import java.util.List;
import java.util.Optional;
import lombok.val;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class JobSchedulerTest {
  private static final ScheduleFetcher scheduleFetcher = mock(ScheduleFetcher.class);
  private static final JobRepository jobRepository = mock(JobRepository.class);
  private final JobScheduler jobScheduler = new JobScheduler(jobRepository, scheduleFetcher);

  @BeforeAll
  static void setUp() {
    val job = new Job();
    job.setId(1L);
    job.setStatus("SCHEDULED");
    job.setCommandId(1L);
    when(jobRepository.findByTimestampAndStatus(any(), any(), any())).thenReturn(List.of(job));
    when(jobRepository.lockJob(any())).thenReturn(1);
    val command = new Command();
    command.setId(1L);
    command.setSchedule("* * * * *");
    command.setType("LOGGER");
    command.setParams(List.of("p1", "p2"));
    when(scheduleFetcher.findById(any())).thenReturn(Optional.of(command));
  }

  @Test
  void testExecuteScheduledJobs() {
    jobScheduler.executeScheduledJobs();
    verify(jobRepository, times(1)).findByTimestampAndStatus(any(), any(), any());
    verify(jobRepository, times(1)).lockJob(any());
    verify(scheduleFetcher, times(1)).findById(any());
  }
}
