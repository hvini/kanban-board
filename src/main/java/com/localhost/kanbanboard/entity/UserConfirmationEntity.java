package com.localhost.kanbanboard.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Entity;
import java.time.LocalDateTime;
import javax.persistence.Id;

/**
 * UserConfirmation
 */
@Entity
public class UserConfirmationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long confirmationId;
    private String token;
    private LocalDateTime createdDate;
    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private UserEntity user;

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