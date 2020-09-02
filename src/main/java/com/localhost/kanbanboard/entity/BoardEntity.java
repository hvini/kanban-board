package com.localhost.kanbanboard.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.validation.constraints.NotEmpty;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToMany;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

/**
 * BoardEntity
 */
@Entity
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
    private List<UserEntity> users;

    public BoardEntity() {
        this.users = new ArrayList<>();
    }

    public Long getBoardId() {
        return boardId;
    }

    public void setBoardId(Long boardId) {
        this.boardId = boardId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public List<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(List<UserEntity> users) {
        this.users = users;
    }

    public void addUser(UserEntity user) {
        this.users.add(user);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((boardId == null) ? 0 : boardId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BoardEntity other = (BoardEntity) obj;
        if (boardId == null) {
            if (other.boardId != null)
                return false;
        } else if (!boardId.equals(other.boardId))
            return false;
        return true;
    }
}