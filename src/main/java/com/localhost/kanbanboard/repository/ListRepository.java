package com.localhost.kanbanboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.localhost.kanbanboard.entity.ListEntity;

import org.springframework.stereotype.Repository;
/**
 * ListRepository
 */
@Repository
public interface ListRepository extends JpaRepository<ListEntity, Long> { }