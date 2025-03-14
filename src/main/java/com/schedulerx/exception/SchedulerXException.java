package com.schedulerx.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/** Custom exception class for SchedulerX application. */
@Getter
public class SchedulerXException extends RuntimeException {
  private final HttpStatus httpStatus;
  private final String errorDetails;
  private final String errorCode;

  /**
   * Constructor for SchedulerXException.
   *
   * @param error the error details
   */
  public SchedulerXException(final SchedulerXError error) {
    super(error.getDesc());
    this.httpStatus = error.getHttpStatus();
    this.errorDetails = error.getDesc();
    this.errorCode = error.name();
  }
}
