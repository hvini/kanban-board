package com.localhost.kanbanboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.localhost.kanbanboard.entity.ListEntity;

/**
 * ListRepository
 */
public interface ListRepository extends JpaRepository<ListEntity, Long> { }