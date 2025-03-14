package com.schedulerx.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SchedulerXError {
  INTERNAL_SERVER_ERROR(
      HttpStatus.INTERNAL_SERVER_ERROR,
      "Sorry! An unexpected error has occurred and we were unable to process your request."),
  COMMAND_NOT_FOUND(HttpStatus.NOT_FOUND, "The command you are looking for does not exist."),
  INVALID_CRON_EXPRESSION(HttpStatus.BAD_REQUEST, "The cron expression is invalid."),
  INVALID_COMMAND_TYPE(HttpStatus.BAD_REQUEST, "The command type is invalid.");
  private final HttpStatus httpStatus;
  private final String desc;
}
