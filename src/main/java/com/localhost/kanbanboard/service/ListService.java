package com.localhost.kanbanboard.service;

import com.localhost.kanbanboard.exception.MethodArgumentNotValidException;
import com.localhost.kanbanboard.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import com.localhost.kanbanboard.repository.ListRepository;
import org.springframework.scheduling.annotation.Async;
import com.localhost.kanbanboard.entity.BoardEntity;
import com.localhost.kanbanboard.entity.CardEntity;
import com.localhost.kanbanboard.entity.ListEntity;
import com.localhost.kanbanboard.entity.UserEntity;
import org.springframework.stereotype.Service;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.Optional;
import java.util.List;

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

    public List<CardEntity> getAllCards(Long listId) throws ResourceNotFoundException {
        ListEntity list = getById(listId);

        if(list.getCards().isEmpty())
            throw new ResourceNotFoundException("List has no registered cards!.");

        return list.getCards();
    }

    @Async("threadPoolTaskExecutor")
    public Future<?> create(ListEntity list, Long boardId, String userEmail) throws ResourceNotFoundException, MethodArgumentNotValidException { 
        BoardEntity board = boardService.getById(boardId);
        UserEntity user   = userService.getByEmail(userEmail);
        String text       = user.getFullName() + " adicionou " + list.getName() + " a este quadro";

        if(!userService.userIsInTheBoard(user, board))
            throw new MethodArgumentNotValidException("User does not belong to this board!.");

        list.setBoard(board);
        listRepository.save(list);

        activityService.create(text, board);
        return CompletableFuture.completedFuture(list);
    }

    @Async("threadPoolTaskExecutor")
    public Future<?> update(ListEntity listEntity, Long boardId, String userEmail) throws ResourceNotFoundException, MethodArgumentNotValidException {
        ListEntity list = getById(listEntity.getListId());
        BoardEntity board = boardService.getById(boardId);
        UserEntity user = userService.getByEmail(userEmail);

        if(!userService.userIsInTheBoard(user, board))
            throw new MethodArgumentNotValidException("User does not belong to this board!.");

        if(!boardService.boardHasList(board, list))
            throw new MethodArgumentNotValidException("Board does not have this list!.");

        list.setName(listEntity.getName());
        list.setPosition(listEntity.getPosition());
        listRepository.save(list);
        return CompletableFuture.completedFuture(list);
    }
}