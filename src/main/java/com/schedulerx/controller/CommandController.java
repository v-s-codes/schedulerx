package com.schedulerx.controller;

import com.schedulerx.mappers.JobResponseMapper;
import com.schedulerx.models.CommandResponse;
import com.schedulerx.models.CreateCommandRequest;
import com.schedulerx.models.JobResponse;
import com.schedulerx.models.UpdateCommandRequest;
import com.schedulerx.service.CommandService;
import com.schedulerx.service.JobService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Controller for handling command-related API requests. */
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CommandController {
  private final CommandService commandService;
  private final JobService jobService;

  /**
   * Creates a new command.
   *
   * @param request the request to create a command
   * @return a ResponseEntity containing a CommandResponse object and an HTTP status code
   */
  @PostMapping("/commands")
  public ResponseEntity<CommandResponse> createCommand(
      final @Valid @RequestBody CreateCommandRequest request) {
    val response = commandService.createCommand(request);
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  /**
   * Updates a command.
   *
   * @param request the request to update a command
   * @return a ResponseEntity containing a CommandResponse object and an HTTP status code
   */
  @PutMapping("/commands")
  public ResponseEntity<CommandResponse> updateCommand(
      final @Valid @RequestBody UpdateCommandRequest request) {
    val response = commandService.updateCommand(request);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  /**
   * Retrieves a list of jobs associated with a specific command ID.
   *
   * @param commandId the ID of the command
   * @return a ResponseEntity containing a list of JobResponse objects and an HTTP status code
   */
  @GetMapping("/jobs/command/{commandId}")
  public ResponseEntity<List<JobResponse>> getJobsByCommandId(
      final @NotBlank @PathVariable String commandId) {
    return new ResponseEntity<>(
        JobResponseMapper.mapJobsToJobResponses(jobService.getJobsByCommandId(commandId)),
        HttpStatus.OK);
  }

  /**
   * Deletes a command.
   *
   * @param id the ID of the command to delete
   * @return a ResponseEntity with an HTTP status code
   */
  @DeleteMapping("/commands/{id}")
  public ResponseEntity<Void> deleteCommand(final @PathVariable Long id) {
    commandService.deleteCommand(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
