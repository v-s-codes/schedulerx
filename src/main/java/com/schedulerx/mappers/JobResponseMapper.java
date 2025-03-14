package com.schedulerx.mappers;

import com.schedulerx.models.Job;
import com.schedulerx.models.JobResponse;
import com.schedulerx.models.JobStatus;
import java.util.List;
import lombok.experimental.UtilityClass;
import lombok.val;

/** Utility class for mapping jobs to job responses. */
@UtilityClass
public class JobResponseMapper {

  /**
   * Maps a list of jobs to a list of job responses.
   *
   * @param jobs the list of jobs to map
   * @return a list of job responses
   */
  public static List<JobResponse> mapJobsToJobResponses(final List<Job> jobs) {
    return jobs.stream().map(JobResponseMapper::mapJobToJobResponse).toList();
  }

  private static JobResponse mapJobToJobResponse(final Job job) {
    val jobResponse = new JobResponse();
    jobResponse.setId(job.getId().toString());
    jobResponse.setJobStatus(JobStatus.valueOf(job.getStatus()));
    return jobResponse;
  }
}
