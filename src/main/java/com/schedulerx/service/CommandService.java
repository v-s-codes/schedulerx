package com.schedulerx.service;

import static com.schedulerx.utils.Constants.CRON_PARSER;
import static com.schedulerx.utils.Constants.JOB_COUNT;

import com.cronutils.model.Cron;
import com.schedulerx.exception.SchedulerXError;
import com.schedulerx.exception.SchedulerXException;
import com.schedulerx.models.Command;
import com.schedulerx.models.CommandResponse;
import com.schedulerx.models.CommandType;
import com.schedulerx.models.CreateCommandRequest;
import com.schedulerx.models.Job;
import com.schedulerx.models.JobStatus;
import com.schedulerx.models.UpdateCommandRequest;
import com.schedulerx.repository.JobRepository;
import com.schedulerx.repository.ScheduleFetcher;
import com.schedulerx.utils.CronUtils;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

/** Service class for handling command-related operations. */
@Service
@RequiredArgsConstructor
public class CommandService {

  private final ScheduleFetcher scheduleFetcher;
  private final JobRepository jobRepository;

  /**
   * Creates a new command, and schedules next 2 jobs for the command.
   *
   * @param request the request to create a command
   * @return the response containing the ID of the created command
   */
  public CommandResponse createCommand(final CreateCommandRequest request) {
    val cron = validateAndParseCronExpression(request.getType(), request.getSchedule());
    try {
      val command = new Command();
      mapCommand(request.getSchedule(), request.getType(), request.getParams(), command);
      val savedCommand = scheduleFetcher.save(command);

      saveJobs(savedCommand.getId(), cron);

      val response = new CommandResponse();
      response.setId(String.valueOf(savedCommand.getId()));
      return response;
    } catch (final SchedulerXException e) {
      throw e;
    } catch (final Exception e) {
      throw new SchedulerXException(SchedulerXError.INTERNAL_SERVER_ERROR);
    }
  }

  private void mapCommand(
      final String schedule, final String type, final List<String> params, final Command command) {
    command.setSchedule(schedule);
    command.setType(type);
    command.setParams(params);
  }

  /**
   * Updates a command.
   *
   * @param request the request to update a command
   * @return the response containing the ID of the updated command
   */
  public CommandResponse updateCommand(final UpdateCommandRequest request) {
    val cron = validateAndParseCronExpression(request.getType(), request.getSchedule());
    val command =
        scheduleFetcher
            .findById(Long.parseLong(request.getId()))
            .orElseThrow(() -> new SchedulerXException(SchedulerXError.COMMAND_NOT_FOUND));
    mapCommand(request.getSchedule(), request.getType(), request.getParams(), command);

    scheduleFetcher.save(command);
    jobRepository.deleteByCommandIdAndStatus(command.getId(), JobStatus.SCHEDULED.toString());
    saveJobs(command.getId(), cron);
    val response = new CommandResponse();
    response.setId(String.valueOf(command.getId()));

    return response;
  }

  /**
   * Deletes a command.
   *
   * @param commandId the ID of the command to delete
   */
  public void deleteCommand(final Long commandId) {
    val command =
        scheduleFetcher
            .findById(commandId)
            .orElseThrow(() -> new SchedulerXException(SchedulerXError.COMMAND_NOT_FOUND));
    jobRepository.deleteByCommandIdAndStatus(command.getId(), JobStatus.SCHEDULED.toString());
    command.setIsActive(false);
    scheduleFetcher.save(command);
  }

  private Cron validateAndParseCronExpression(final String type, final String schedule) {
    Arrays.stream(CommandType.values())
        .filter(commandType -> commandType.name().equals(type))
        .findAny()
        .orElseThrow(() -> new SchedulerXException(SchedulerXError.INVALID_COMMAND_TYPE));
    try {
      return CRON_PARSER.parse(schedule);
    } catch (final Exception e) {
      throw new SchedulerXException(SchedulerXError.INVALID_CRON_EXPRESSION);
    }
  }

  private void saveJobs(final Long commandId, final Cron cron) {
    val nextExecutionTimes = CronUtils.getNextExecutionTimes(cron, JOB_COUNT);
    for (val executionTime : nextExecutionTimes) {
      val job = new Job();
      job.setCommandId(commandId);
      job.setStatus(JobStatus.SCHEDULED.toString());
      job.setTimestamp(executionTime);
      jobRepository.save(job);
    }
  }
}
