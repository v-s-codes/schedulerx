package com.schedulerx.repository;

import com.schedulerx.models.Command;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** Repository for fetching commands. */
@Repository
public interface ScheduleFetcher extends JpaRepository<Command, Long> {}
