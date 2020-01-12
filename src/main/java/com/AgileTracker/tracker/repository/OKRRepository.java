package com.AgileTracker.tracker.repository;

import com.AgileTracker.tracker.model.OKR;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OKRRepository extends JpaRepository<OKR, Long> {
}
