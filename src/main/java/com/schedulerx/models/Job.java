package com.schedulerx.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Data;

/** Represents a job entity with command ID, timestamp, status, and locked status. */
@Entity
@Table(name = "jobs")
@Data
public class Job {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "command_id", nullable = false)
  private Long commandId;

  @Column(name = "timestamp", nullable = false)
  private LocalDateTime timestamp;

  @Column(name = "status", nullable = false)
  private String status;
}
