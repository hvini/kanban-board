package com.localhost.kanbanboard.entity;

import javax.validation.constraints.NotEmpty;
import javax.persistence.ElementCollection;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.CollectionTable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Map;

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
    private Boolean isFavorite;
    @ElementCollection
    @CollectionTable(name = "board_role", 
        joinColumns = @JoinColumn(name = "boardId"))
    @MapKeyJoinColumn(name = "userId")
    @Column(name = "role_id")
    private Map<UserEntity, RoleEntity> boardRole;

    public Long getBoardId() {
        return boardId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(Boolean isFavorite) {
        this.isFavorite = isFavorite;
    }

    public Map<UserEntity, RoleEntity> getBoardRole() {
        return boardRole;
    }

    public void setBoardRole(Map<UserEntity, RoleEntity> boardRole) {
        this.boardRole = boardRole;
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