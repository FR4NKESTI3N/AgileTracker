package com.AgileTracker.tracker.repository;

import com.AgileTracker.tracker.model.Sprint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SprintRepository extends JpaRepository<Sprint, Long> {
}
