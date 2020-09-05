package com.localhost.kanbanboard.repository;

import com.localhost.kanbanboard.entity.BoardInvitationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * BoardInvitationRepository
 */
@Repository
public interface BoardInvitationRepository extends JpaRepository<BoardInvitationEntity, Long> { }