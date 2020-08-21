package com.localhost.kanbanboard.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Entity;
import java.time.LocalDateTime;
import javax.persistence.Id;
import java.util.UUID;

/**
 * UserConfirmation
 */
@Entity
public class ConfirmationTokenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long confirmationId;
    private String token;
    private LocalDateTime createdDate;
    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private UserEntity user;

    public ConfirmationTokenEntity() { }

    public ConfirmationTokenEntity(UserEntity user) {
        this.user = user;
        this.createdDate = LocalDateTime.now();
        this.token = UUID.randomUUID().toString();
    }

    public Long getConfirmationId() {
        return confirmationId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}