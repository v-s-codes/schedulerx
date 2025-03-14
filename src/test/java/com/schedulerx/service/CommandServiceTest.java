package com.schedulerx.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.schedulerx.exception.SchedulerXException;
import com.schedulerx.models.Command;
import com.schedulerx.models.CreateCommandRequest;
import com.schedulerx.models.UpdateCommandRequest;
import com.schedulerx.repository.JobRepository;
import com.schedulerx.repository.ScheduleFetcher;
import java.util.Optional;
import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CommandServiceTest {
  private static final ScheduleFetcher scheduleFetcher = mock(ScheduleFetcher.class);
  private static final JobRepository jobRepository = mock(JobRepository.class);
  private final CommandService commandService = new CommandService(scheduleFetcher, jobRepository);

  @BeforeAll
  static void setUp() {
    val command = new Command();
    command.setId(1L);
    command.setSchedule("* * * * *");
    command.setType("test");
    when(scheduleFetcher.save(any())).thenReturn(command);
    when(scheduleFetcher.findById(any())).thenReturn(Optional.of(command));
  }

  @Test
  void testCreateCommand() {
    val request = new CreateCommandRequest();
    // No command type
    Assertions.assertThrows(SchedulerXException.class, () -> commandService.createCommand(request));
    request.setType("test");
    // Invalid command type
    Assertions.assertThrows(SchedulerXException.class, () -> commandService.createCommand(request));
    request.setType("LOGGER");
    // No schedule
    Assertions.assertThrows(SchedulerXException.class, () -> commandService.createCommand(request));
    request.setSchedule("* * * * *");
    val response = commandService.createCommand(request);
    assertNotNull(response);
    Assertions.assertEquals("1", response.getId());
  }

  @Test
  void testUpdateCommand() {
    val request = new UpdateCommandRequest();
    // No command type
    Assertions.assertThrows(SchedulerXException.class, () -> commandService.updateCommand(request));
    request.setType("test");
    // Invalid command type
    Assertions.assertThrows(SchedulerXException.class, () -> commandService.updateCommand(request));
    request.setType("LOGGER");
    // No schedule
    Assertions.assertThrows(SchedulerXException.class, () -> commandService.updateCommand(request));
    request.setSchedule("* * * * *");
    request.setId("1");
    val response = commandService.updateCommand(request);
    assertNotNull(response);
    Assertions.assertEquals("1", response.getId());
  }

  @Test
  void testDeleteCommand() {
    commandService.deleteCommand(1L);
    verify(jobRepository, times(1)).deleteByCommandIdAndStatus(any(), any());
    verify(scheduleFetcher, times(1)).save(any());
  }
}
