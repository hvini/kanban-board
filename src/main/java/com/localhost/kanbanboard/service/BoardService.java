package com.localhost.kanbanboard.service;

import com.localhost.kanbanboard.exception.MethodArgumentNotValidException;
import com.localhost.kanbanboard.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import com.localhost.kanbanboard.repository.BoardRepository;
import com.localhost.kanbanboard.entity.BoardEntity;
import com.localhost.kanbanboard.entity.RoleEntity;
import com.localhost.kanbanboard.entity.UserEntity;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.HashMap;
import java.util.Map;

/**
 * BoardService
 */
@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private RoleService roleService;
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
        RoleEntity role = roleService.createRoleIfNotFound("admin");

        Map<UserEntity, RoleEntity> boardRole = new HashMap<>();
        boardRole.put(user, role);

        boardEntity.setIsFavorite(false);
        boardEntity.setBoardRole(boardRole);
        boardRepository.save(boardEntity);
    }

    public void update(BoardEntity boardEntity) throws ResourceNotFoundException {
        BoardEntity board = getById(boardEntity.getBoardId());

        board.setName(boardEntity.getName());
        boardRepository.save(board);
    }

    public void delete(Long boardId, Long userId) throws ResourceNotFoundException, MethodArgumentNotValidException {
        BoardEntity board = getById(boardId);
        UserEntity user = userService.getById(userId);

        RoleEntity role = board.getBoardRole().get(user);
        if(!role.getName().contains("admin"))
            throw new MethodArgumentNotValidException("Only the board administrator can remove it!.");

        boardRepository.delete(board);
    }

    public void favorite(Long boardId) throws ResourceNotFoundException, MethodArgumentNotValidException {
        BoardEntity board = getById(boardId);
        
        if(board.getIsFavorite())
            throw new MethodArgumentNotValidException("Board is already marked as favorite!.");

        board.setIsFavorite(true);
        boardRepository.save(board);
    }

    public void unfavorite(Long boardId) throws ResourceNotFoundException, MethodArgumentNotValidException {
        BoardEntity board = getById(boardId);
        
        if(!board.getIsFavorite())
            throw new MethodArgumentNotValidException("Board is not marked as favorite!.");

        board.setIsFavorite(false);
        boardRepository.save(board);
    }
}