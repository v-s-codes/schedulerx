package com.schedulerx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/** Main class for the application. */
@SpringBootApplication
@EnableScheduling
public class SchedulerX {
  public static void main(final String[] args) {
    SpringApplication.run(SchedulerX.class, args);
  }
}
