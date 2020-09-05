package com.localhost.kanbanboard.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;
import javax.persistence.Id;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

/**
 * UserConfirmation
 */
@Entity
@Getter @Setter
@NoArgsConstructor
@EqualsAndHashCode
public class ConfirmationTokenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long confirmationId;

    private String token;

    private LocalDateTime createdDate;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private UserEntity user;

    public ConfirmationTokenEntity(UserEntity user) {
        this.user = user;
        this.createdDate = LocalDateTime.now();
        this.token = UUID.randomUUID().toString();
    }
}