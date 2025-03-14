package com.schedulerx.exception;

import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * This class handles exceptions thrown by the application, and returns an appropriate response to
 * the client.
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  /**
   * Handles SchedulerXException.
   *
   * @param ex the exception to handle
   * @return a ResponseEntity containing the error message and an HTTP status code
   */
  @ExceptionHandler(SchedulerXException.class)
  public ResponseEntity<String> handleSchedulerXException(final SchedulerXException ex) {
    return ResponseEntity.status(ex.getHttpStatus()).body(ex.getMessage());
  }

  /**
   * Handles generic exceptions.
   *
   * @param ex the exception to handle
   * @return a ResponseEntity containing the error message and an HTTP status code
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleGenericException(final Exception ex) {
    log.error("An unknown error occurred", ex);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong!");
  }

  /**
   * Handles validation exceptions.
   *
   * @param ex the exception to handle
   * @return a ResponseEntity containing the error message and an HTTP status code
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> handleValidationExceptions(
      final MethodArgumentNotValidException ex) {
    val errors = new HashMap<String, String>();
    for (val error : ex.getBindingResult().getFieldErrors()) {
      errors.put(error.getField(), error.getDefaultMessage());
    }
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
  }
}
