package com.localhost.kanbanboard.service;

import com.localhost.kanbanboard.exception.MethodArgumentNotValidException;
import com.localhost.kanbanboard.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import com.localhost.kanbanboard.repository.BoardRepository;
import com.localhost.kanbanboard.entity.BoardEntity;
import com.localhost.kanbanboard.entity.UserEntity;
import org.springframework.stereotype.Service;
import java.util.Optional;

/**
 * BoardService
 */
@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private UserService userService;

    public BoardEntity getById(Long boardId) throws ResourceNotFoundException {
        Optional<BoardEntity> board = boardRepository.findById(boardId);

        if(!board.isPresent())
            throw new ResourceNotFoundException("Invalid board identification!.");

        return board.get();
    }

    public void create(BoardEntity boardEntity, Long userId) throws ResourceNotFoundException {
        UserEntity user = userService.getById(userId);

        boardEntity.addUser(user);
        boardRepository.save(boardEntity);
    }

    public void update(BoardEntity boardEntity) throws ResourceNotFoundException {
        BoardEntity board = getById(boardEntity.getBoardId());

        board.setName(boardEntity.getName());
        boardRepository.save(board);
    }

    public void delete(Long boardId) throws ResourceNotFoundException {
        BoardEntity board = getById(boardId);

        boardRepository.delete(board);
    }

    public void favorite(Long userId, Long boardId) throws ResourceNotFoundException, MethodArgumentNotValidException {
        UserEntity user = userService.getById(userId);
        BoardEntity board = getById(boardId);

        userService.addBoardToFavorite(user, board);
    }

    public void unfavorite(Long userId, Long boardId) throws ResourceNotFoundException, MethodArgumentNotValidException {
        UserEntity user = userService.getById(userId);
        BoardEntity board = getById(boardId);

        userService.removeBoardFromFavorite(user, board);
    }
}