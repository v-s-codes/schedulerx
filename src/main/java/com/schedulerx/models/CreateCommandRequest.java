package com.schedulerx.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Data;

/** Represents a request to create a command. */
@Data
public class CreateCommandRequest {
  @NotBlank(message = "Schedule is mandatory")
  private String schedule;

  @NotNull(message = "Type is mandatory")
  private String type;

  private List<String> params;
}
