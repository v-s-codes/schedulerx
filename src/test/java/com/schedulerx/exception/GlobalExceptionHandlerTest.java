package com.schedulerx.exception;

import static org.junit.jupiter.api.Assertions.*;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;

class GlobalExceptionHandlerTest {
  private final GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

  @Test
  void testHandleSchedulerXException() {
    val schedulerXException = new SchedulerXException(SchedulerXError.INTERNAL_SERVER_ERROR);
    val response = globalExceptionHandler.handleSchedulerXException(schedulerXException);
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    assertEquals(
        "Sorry! An unexpected error has occurred and we were unable to process your request.",
        response.getBody());
  }

  @Test
  void testHandleGenericException() {
    val exception = new Exception("An unknown error occurred");
    val response = globalExceptionHandler.handleGenericException(exception);
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    assertEquals("Something went wrong!", response.getBody());
  }

  @Test
  void testHandleValidationExceptions() {
    val bindingResult = new BindException(new Object(), "objectName");
    val exception = new MethodArgumentNotValidException(null, bindingResult);
    val response = globalExceptionHandler.handleValidationExceptions(exception);
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertTrue(response.getBody().isEmpty());
  }
}
