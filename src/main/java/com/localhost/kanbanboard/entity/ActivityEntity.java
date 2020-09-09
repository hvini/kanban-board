package com.localhost.kanbanboard.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.EqualsAndHashCode;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;

/**
 * ActivityEntity
 */
@Entity
@Getter @Setter
@EqualsAndHashCode
public class ActivityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long activityId;

    private String text;
    
    @ManyToOne
    @JoinColumn(name = "boardId", nullable = false)
    private BoardEntity board;
}