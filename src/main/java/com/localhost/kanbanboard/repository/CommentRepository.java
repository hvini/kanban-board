package com.localhost.kanbanboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.localhost.kanbanboard.entity.CommentEntity;
import org.springframework.stereotype.Repository;

/**
 * CommentRepository
 */
@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> { }