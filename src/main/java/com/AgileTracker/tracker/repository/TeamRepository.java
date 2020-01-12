package com.AgileTracker.tracker.repository;

import com.AgileTracker.tracker.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
