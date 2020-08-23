package com.localhost.kanbanboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.localhost.kanbanboard.entity.UserEntity;
import org.springframework.stereotype.Repository;

/**
 * UserRepository
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> { 
    UserEntity findByEmail(String email);
}