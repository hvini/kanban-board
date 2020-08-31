package com.localhost.kanbanboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.localhost.kanbanboard.entity.RoleEntity;
import org.springframework.stereotype.Repository;

/**
 * RoleRepository
 */
@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> { 
    RoleEntity findByName(String name);
}