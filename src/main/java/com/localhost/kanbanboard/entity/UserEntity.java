package com.localhost.kanbanboard.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.validation.constraints.NotEmpty;
import javax.persistence.ElementCollection;
import javax.persistence.CollectionTable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
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
    private List<ConfirmationTokenEntity> confirmationToken;
    @ManyToMany(mappedBy = "users")
    private List<BoardEntity> boards;
    @ElementCollection
    @CollectionTable(name = "favorite_boards")
    private List<BoardEntity> favoriteBoards;
    @OneToMany(mappedBy = "user")
    private List<RoleEntity> roles;
    @OneToMany(mappedBy = "user")
    private List<BoardInvitationEntity> boardInvitations;

    public UserEntity() {
        this.boards = new ArrayList<>();
        this.favoriteBoards = new ArrayList<>();
        this.roles = new ArrayList<>();
        this.boardInvitations = new ArrayList<>();
    }

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
    
    @JsonIgnore
    public List<ConfirmationTokenEntity> getConfirmationToken() {
        return confirmationToken;
    }
    
    public void setConfirmationToken(List<ConfirmationTokenEntity> confirmationToken) {
        this.confirmationToken = confirmationToken;
    }

    public void addConfirmationToken(ConfirmationTokenEntity confirmationToken) {
        this.confirmationToken.add(confirmationToken);
    }

    public List<BoardEntity> getBoards() {
        return boards;
    }

    public void setBoards(List<BoardEntity> boards) {
        this.boards = boards;
    }

    public List<BoardEntity> getFavoriteBoards() {
        return favoriteBoards;
    }

    public void setFavoriteBoards(List<BoardEntity> favoriteBoards) {
        this.favoriteBoards = favoriteBoards;
    }

    public void addFavoriteBoard(BoardEntity board) {
        this.favoriteBoards.add(board);
    }

    public void removeFavoriteBoard(BoardEntity board) {
        this.favoriteBoards.remove(board);
    }

    public List<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleEntity> roles) {
        this.roles = roles;
    }

    @JsonIgnore
    public List<BoardInvitationEntity> getBoardInvitations() {
        return boardInvitations;
    }

    public void setBoardInvitations(List<BoardInvitationEntity> boardInvitations) {
        this.boardInvitations = boardInvitations;
    }
}