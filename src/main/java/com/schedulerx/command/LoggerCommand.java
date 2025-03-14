package com.schedulerx.command;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

/** Command to log the parameters. */
@RequiredArgsConstructor
@Slf4j
public class LoggerCommand implements Command {
  private final String id;

  /**
   * Returns the id of the command.
   *
   * @return the id of the command
   */
  @Override
  public String id() {
    return id;
  }

  /**
   * Executes the command.
   *
   * @param params the parameters for the command
   */
  @Override
  public void execute(final List<String> params) {
    if (CollectionUtils.isEmpty(params)) {
      log.info("The parameters for command id: {} are empty", id);
      return;
    }
    log.info("The parameters for command id: {} are: {}", id, params);
  }
}
