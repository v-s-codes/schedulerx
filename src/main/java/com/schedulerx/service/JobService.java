package com.schedulerx.service;

import com.schedulerx.models.Job;
import com.schedulerx.repository.JobRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

/** Service class for handling job-related operations. */
@RequiredArgsConstructor
@Service
public class JobService {

  private final JobRepository jobRepository;

  /**
   * Returns the list of jobs by command id.
   *
   * @param commandId the id of the command
   * @return the list of jobs, which have the same command id as the given command id
   */
  public List<Job> getJobsByCommandId(final String commandId) {
    val id = Long.parseLong(commandId);
    return jobRepository.findByCommandId(id);
  }
}
