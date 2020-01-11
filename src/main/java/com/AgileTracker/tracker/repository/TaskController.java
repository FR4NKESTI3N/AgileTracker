package com.AgileTracker.tracker.repository;

import com.AgileTracker.tracker.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskController extends JpaRepository<Task, Long> {
}
