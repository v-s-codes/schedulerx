package com.schedulerx.command;

import com.schedulerx.models.CommandType;
import java.util.List;

/**
 * Represents a command that can be executed with a list of parameters. Implement this interface to
 * define new commands. When a new command is implemented, ensure to add a corresponding entry in
 * the {@link CommandType} enum and update {@link com.schedulerx.utils.CommandFactory} to select the new command.
 */
public interface Command {

  /**
   * Returns the id of the command.
   *
   * @return the id of the command
   */
  String id();

  /**
   * Executes the command.
   *
   * @param params the parameters for the command
   */
  void execute(final List<String> params);
}
