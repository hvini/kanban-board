package com.localhost.kanbanboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.localhost.kanbanboard.entity.BoardEntity;
import org.springframework.stereotype.Repository;

/**
 * BoardRepository
 */
@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Long> { }