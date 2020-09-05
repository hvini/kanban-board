package com.localhost.kanbanboard.service;

import com.localhost.kanbanboard.repository.BoardInvitationRepository;
import com.localhost.kanbanboard.entity.BoardInvitationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import com.localhost.kanbanboard.entity.BoardEntity;
import com.localhost.kanbanboard.entity.UserEntity;
import org.springframework.stereotype.Service;

/**
 * BoardInvitationService
 */
@Service
public class BoardInvitationService {
    @Autowired
    private BoardInvitationRepository boardInvitationRepository;

    public void create(UserEntity user, BoardEntity board) {
        BoardInvitationEntity boardInvitation = new BoardInvitationEntity();

        boardInvitation.setUser(user);
        boardInvitation.setBoard(board);
        boardInvitationRepository.save(boardInvitation);
    }

    public void remove(BoardInvitationEntity boardInvitation) {
        boardInvitationRepository.delete(boardInvitation);
    }
}