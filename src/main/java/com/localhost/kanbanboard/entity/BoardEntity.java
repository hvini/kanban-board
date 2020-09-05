package com.localhost.kanbanboard.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.validation.constraints.NotEmpty;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToMany;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Entity;
import lombok.EqualsAndHashCode;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * BoardEntity
 */
@Entity
@Getter @Setter
@EqualsAndHashCode
public class BoardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long boardId;

    @NotEmpty(message = "Nome deve ser informado!.")
    private String name;

    @ManyToMany
    @JoinTable(name = "user_boards",
        joinColumns = @JoinColumn(name = "boardId", referencedColumnName = "boardId"),
        inverseJoinColumns = @JoinColumn(name = "userId", referencedColumnName = "userId"))
    @Getter(onMethod = @__(@JsonIgnore))
    private List<UserEntity> users;

    @OneToMany(mappedBy = "board")
    @Getter(onMethod = @__(@JsonIgnore))
    private List<RoleEntity> roles;

    @OneToMany(mappedBy = "board")
    @Getter(onMethod = @__(@JsonIgnore))
    private List<BoardInvitationEntity> boardInvitations;

    public BoardEntity() {
        this.users = new ArrayList<>();
        this.roles = new ArrayList<>();
        this.boardInvitations = new ArrayList<>();
    }

    public void addUser(UserEntity user) {
        this.users.add(user);
    }
}