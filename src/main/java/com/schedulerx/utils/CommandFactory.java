package com.schedulerx.utils;

import static com.schedulerx.utils.Constants.STACK_SIZE;

import com.schedulerx.command.Command;
import com.schedulerx.command.LoggerCommand;
import com.schedulerx.command.StackCommand;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/** Factory class to get the command based on the command type. */
@Slf4j
@Component
public class CommandFactory {

  /**
   * Returns the command based on the command type.
   *
   * @param commandType the type of the command
   * @param commandId the id of the command
   * @return the command based on the command type
   */
  public static Optional<Command> getCommand(final String commandType, final String commandId) {
    return switch (commandType) {
      case "LOGGER" -> Optional.of(new LoggerCommand(commandId));
      case "STACK" -> Optional.of(new StackCommand(commandId, new Stack<>(STACK_SIZE)));
      default -> handleUnsupportedCommand(commandType);
    };
  }

  private static Optional<Command> handleUnsupportedCommand(final String commandType) {
    log.error("Unsupported command type encountered: {}", commandType);
    return Optional.empty();
  }
}
