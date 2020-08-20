package com.localhost.kanbanboard.repository;

import com.localhost.kanbanboard.entity.UserConfirmationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * UserConfirmationRepository
 */
@Repository
public interface UserConfirmationRepository extends JpaRepository<UserConfirmationEntity, Long> { }