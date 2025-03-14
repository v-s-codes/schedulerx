package com.schedulerx.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

/** Represents a command entity with schedule, type, and parameters. */
@Entity
@Table(name = "commands")
@Data
public class Command {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @NotBlank(message = "Command schedule is required")
  @Column(name = "schedule", nullable = false)
  private String schedule;

  @Column(name = "command_type", nullable = false)
  private String type;

  @JdbcTypeCode(SqlTypes.ARRAY)
  @Column(name = "parameters")
  private List<String> params;

  @Column(name = "is_active", nullable = false)
  private Boolean isActive = true;
}
