package com.localhost.kanbanboard.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Entity;
import lombok.EqualsAndHashCode;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;

/**
 * BoardInvitationEntity
 */
@Entity
@Getter @Setter
@EqualsAndHashCode
public class BoardInvitationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long boardInvitationId;

    @ManyToOne()
    @JoinColumn(name = "userId", nullable = false)
    private UserEntity user;

    @ManyToOne()
    @JoinColumn(name = "boardId", nullable = false)
    private BoardEntity board;
}