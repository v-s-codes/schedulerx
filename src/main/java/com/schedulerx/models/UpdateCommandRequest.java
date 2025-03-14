package com.schedulerx.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Data;

@Data
public class UpdateCommandRequest {
  @NotBlank(message = "Command ID is mandatory")
  private String id;

  @NotBlank(message = "Schedule is mandatory")
  private String schedule;

  @NotNull(message = "Type is mandatory")
  private String type;

  private List<String> params;
}
