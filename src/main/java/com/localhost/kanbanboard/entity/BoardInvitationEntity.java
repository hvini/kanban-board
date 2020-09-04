package com.localhost.kanbanboard.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * BoardInvitationEntity
 */
@Entity
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

    public Long getBoardInvitationId() {
        return boardInvitationId;
    }

    public BoardEntity getBoard() {
        return board;
    }

    public void setBoard(BoardEntity board) {
        this.board = board;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((board == null) ? 0 : board.hashCode());
        result = prime * result + ((boardInvitationId == null) ? 0 : boardInvitationId.hashCode());
        result = prime * result + ((user == null) ? 0 : user.hashCode());
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
        BoardInvitationEntity other = (BoardInvitationEntity) obj;
        if (board == null) {
            if (other.board != null)
                return false;
        } else if (!board.equals(other.board))
            return false;
        if (boardInvitationId == null) {
            if (other.boardInvitationId != null)
                return false;
        } else if (!boardInvitationId.equals(other.boardInvitationId))
            return false;
        if (user == null) {
            if (other.user != null)
                return false;
        } else if (!user.equals(other.user))
            return false;
        return true;
    }
}