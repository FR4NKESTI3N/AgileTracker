package com.AgileTracker.tracker.repository;

import com.AgileTracker.tracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
