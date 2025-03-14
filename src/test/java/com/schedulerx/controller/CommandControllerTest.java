package com.schedulerx.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.schedulerx.models.CommandResponse;
import com.schedulerx.models.CreateCommandRequest;
import com.schedulerx.models.Job;
import com.schedulerx.models.JobStatus;
import com.schedulerx.models.UpdateCommandRequest;
import com.schedulerx.service.CommandService;
import com.schedulerx.service.JobService;
import java.util.List;
import lombok.val;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class CommandControllerTest {
  private static final CommandService commandService = mock(CommandService.class);
  private static final JobService jobService = mock(JobService.class);
  private final CommandController commandController =
      new CommandController(commandService, jobService);

  @BeforeAll
  static void setUp() {
    val commandResponse = new CommandResponse();
    commandResponse.setId("id");
    when(commandService.createCommand(any())).thenReturn(commandResponse);
    when(commandService.updateCommand(any())).thenReturn(commandResponse);
    val job = new Job();
    job.setId(1L);
    job.setStatus(JobStatus.SCHEDULED.name());
    when(jobService.getJobsByCommandId(any())).thenReturn(List.of(job));
  }

  @Test
  void testCreateCommand() {
    val commandCreateRequest = new CreateCommandRequest();
    commandCreateRequest.setType("LOGGER");
    commandCreateRequest.setParams(List.of("param1", "param2"));
    commandCreateRequest.setSchedule("0 0 0 0 0");
    assertEquals("id", commandController.createCommand(commandCreateRequest).getBody().getId());
  }

  @Test
  void testUpdateCommand() {
    val commandUpdateRequest = new UpdateCommandRequest();
    assertEquals("id", commandController.updateCommand(commandUpdateRequest).getBody().getId());
  }

  @Test
  void testGetJobsByCommandId() {
    val jobResponse = commandController.getJobsByCommandId("1").getBody();
    assertEquals(1, jobResponse.size());
    assertEquals("1", jobResponse.get(0).getId());
  }

  @Test
  void testDeleteCommand() {
    assertEquals(HttpStatus.NO_CONTENT, commandController.deleteCommand(1L).getStatusCode());
  }
}
