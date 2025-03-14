package com.schedulerx.repository;

import com.schedulerx.models.Job;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

  /**
   * Find jobs by timestamp and status.
   *
   * @param lowerBound the lower bound of the timestamp
   * @param upperBound the upper bound of the timestamp
   * @param status the status of the job
   * @return the list of jobs, which have the timestamp between the lower and upper bounds and the
   *     status is the same as the given status
   */
  @Query(
      "SELECT j FROM Job j WHERE j.timestamp BETWEEN :lowerBound AND :upperBound AND j.status = :status")
  List<Job> findByTimestampAndStatus(
      final @Param("lowerBound") LocalDateTime lowerBound,
      final @Param("upperBound") LocalDateTime upperBound,
      final @Param("status") String status);

  /**
   * Lock a job by id.
   *
   * @param id the id of the job
   * @return 1 if the job is successfully locked, 0 otherwise
   */
  @Modifying
  @Transactional
  @Query("UPDATE Job j SET j.status = 'RUNNING' WHERE j.id = :id AND j.status != 'RUNNING'")
  int lockJob(final @Param("id") Long id);

  /**
   * Find jobs by command id.
   *
   * @param commandId the id of the command
   * @return the list of jobs, which have the same command id as the given command id
   */
  List<Job> findByCommandId(final Long commandId);

  /**
   * Delete jobs by command id and status.
   *
   * @param commandId the id of the command
   * @param status the status of the job
   */
  @Modifying
  @Transactional
  @Query("DELETE FROM Job j WHERE j.commandId = :commandId AND j.status = :status")
  void deleteByCommandIdAndStatus(Long commandId, String status);
}
