package com.localhost.kanbanboard.entity;

import javax.validation.constraints.NotEmpty;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToMany;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

/**
 * UserEntity
 */
@Entity
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;
    @NotEmpty(message = "Nome deve ser informado!.")
    private String fullName;
    @NotEmpty(message = "Email deve ser informado!.")
    private String email;
    @NotEmpty(message = "Senha deve ser informada!.")
    private String password;
    private Boolean isEnabled;
    @OneToMany(mappedBy = "user")
    private List<UserConfirmationEntity> userConfirmations;

    public Long getUserId() {
        return userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public List<UserConfirmationEntity> getUserConfirmations() {
        return userConfirmations;
    }
    
    public void setUserConfirmations(List<UserConfirmationEntity> userConfirmations) {
        this.userConfirmations = userConfirmations;
    }

    public void addUserConfirmation(UserConfirmationEntity userConfirmation) {
        this.userConfirmations.add(userConfirmation);
    }
}