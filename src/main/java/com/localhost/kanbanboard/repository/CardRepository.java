package com.localhost.kanbanboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.localhost.kanbanboard.entity.CardEntity;
import org.springframework.stereotype.Repository;

/**
 * CardRepository
 */
@Repository
public interface CardRepository extends JpaRepository<CardEntity, Long> { }