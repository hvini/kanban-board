package com.localhost.kanbanboard.repository;

import com.localhost.kanbanboard.entity.ConfirmationTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * UserConfirmationRepository
 */
@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationTokenEntity, Long> { 
    ConfirmationTokenEntity findByToken(String token);
}