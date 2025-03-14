package com.schedulerx.command;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import lombok.val;
import nl.altindag.log.LogCaptor;
import org.junit.jupiter.api.Test;

class LoggerCommandTest {
  private final LoggerCommand loggerCommand = new LoggerCommand("id");

  @Test
  void testId() {
    assertEquals("id", loggerCommand.id());
  }

  @Test
  void testExecute() {
    val logCaptor = LogCaptor.forClass(LoggerCommand.class);
    loggerCommand.execute(List.of("param1", "param2"));
    val logs = logCaptor.getLogs();
    assertTrue(logs.contains("The parameters for command id: id are: [param1, param2]"));
  }
}
