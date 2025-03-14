package com.schedulerx.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.schedulerx.models.Job;
import com.schedulerx.repository.JobRepository;
import java.util.List;
import lombok.val;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class JobServiceTest {
  private static final JobRepository jobRepository = mock(JobRepository.class);
  private final JobService jobService = new JobService(jobRepository);

  @BeforeAll
  static void setUp() {
    val job = new Job();
    job.setId(1L);
    when(jobRepository.findByCommandId(anyLong())).thenReturn(List.of(job));
  }

  @Test
  void testGetJobsByCommandId() {
    val jobs = jobService.getJobsByCommandId("1");
    assertNotNull(jobs);
    assertEquals(1, jobs.size());
    assertEquals(1L, jobs.get(0).getId());
  }
}
