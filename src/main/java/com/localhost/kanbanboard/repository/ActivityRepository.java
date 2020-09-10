package com.localhost.kanbanboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.localhost.kanbanboard.entity.ActivityEntity;
import org.springframework.stereotype.Repository;

/**
 * ActivityRepository
 */
@Repository
public interface ActivityRepository extends JpaRepository<ActivityEntity, Long> { }