package com.localhost.kanbanboard.service;

import com.localhost.kanbanboard.exception.MethodArgumentNotValidException;
import com.localhost.kanbanboard.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import com.localhost.kanbanboard.repository.ListRepository;
import org.springframework.scheduling.annotation.Async;
import com.localhost.kanbanboard.entity.BoardEntity;
import com.localhost.kanbanboard.entity.ListEntity;
import com.localhost.kanbanboard.entity.UserEntity;
import org.springframework.stereotype.Service;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.Optional;

/**
 * ListService
 */
@Service
public class ListService {
    @Autowired
    private ListRepository listRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private BoardService boardService;
    @Autowired
    private ActivityService activityService;

    public ListEntity getById(Long listId) throws ResourceNotFoundException {
        Optional<ListEntity> list = listRepository.findById(listId);

        if(!list.isPresent())
            throw new ResourceNotFoundException("Invalid list identification!.");

        return list.get();
    }

    @Async("threadPoolTaskExecutor")
    public Future<?> create(ListEntity list, Long boardId, Long userId) throws ResourceNotFoundException, MethodArgumentNotValidException { 
        BoardEntity board = boardService.getById(boardId);
        UserEntity user   = userService.getById(userId);
        String text       = user.getFullName() + " adicionou " + list.getName() + " a este quadro";

        if(!userService.userIsInTheBoard(user, board))
            throw new MethodArgumentNotValidException("User does not belong to this board!.");

        list.setBoard(board);
        listRepository.save(list);

        activityService.create(text, board);
        return CompletableFuture.completedFuture(null);
    }

    @Async("threadPoolTaskExecutor")
    public Future<?> update(ListEntity listEntity, Long boardId, Long userId) throws ResourceNotFoundException, MethodArgumentNotValidException {
        ListEntity list = getById(listEntity.getListId());
        BoardEntity board = boardService.getById(boardId);
        UserEntity user = userService.getById(userId);

        if(!userService.userIsInTheBoard(user, board))
            throw new MethodArgumentNotValidException("User does not belong to this board!.");

        if(!boardService.boardHasList(board, list))
            throw new MethodArgumentNotValidException("Board does not have this list!.");

        list.setName(listEntity.getName());
        list.setPosition(listEntity.getPosition());
        listRepository.save(list);
        return CompletableFuture.completedFuture(null);
    }
}