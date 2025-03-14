package com.schedulerx.models;

import lombok.Data;

/** Represents a response class for the get job request. */
@Data
public class JobResponse {
  private String id;
  private JobStatus jobStatus;
}
