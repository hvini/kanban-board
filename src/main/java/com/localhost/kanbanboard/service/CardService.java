package com.localhost.kanbanboard.service;

import com.localhost.kanbanboard.exception.MethodArgumentNotValidException;
import com.localhost.kanbanboard.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import com.localhost.kanbanboard.repository.CardRepository;
import org.springframework.scheduling.annotation.Async;
import com.localhost.kanbanboard.entity.BoardEntity;
import com.localhost.kanbanboard.entity.CardEntity;
import com.localhost.kanbanboard.entity.ListEntity;
import com.localhost.kanbanboard.entity.UserEntity;
import org.springframework.stereotype.Service;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * CardService
 */

@Service
public class CardService {
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private ListService listService;
    @Autowired
    private UserService userService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private BoardService boardService;

    public CardEntity getById(Long cardId) throws ResourceNotFoundException {
        Optional<CardEntity> card = cardRepository.findById(cardId);

        if(card.isEmpty())
            throw new ResourceNotFoundException("Invalid card identification!.");

        return card.get();
    }

    @Async("threadPoolTaskExecutor")
    public Future<?> create(CardEntity card, Long listId, Long userId, Long boardId) throws ResourceNotFoundException, MethodArgumentNotValidException {
        ListEntity list = listService.getById(listId);
        UserEntity user = userService.getById(userId);
        BoardEntity board = boardService.getById(boardId);
        String text = user.getFullName() + " adicionou " + card.getName() + " a " + list.getName();

        if(!userService.userIsInTheBoard(user, board))
            throw new MethodArgumentNotValidException("User is not in this board!.");

        if(!boardService.boardHasList(board, list))
            throw new MethodArgumentNotValidException("Board does not have this list!.");

        card.setList(list);
        card.setCreatedDate(LocalDateTime.now());
        cardRepository.save(card);

        activityService.create(text, list.getBoard());
        return CompletableFuture.completedFuture(null);
    }

    @Async("threadPoolTaskExecutor")
    public Future<?> update(CardEntity cardEntity, Long boardId, Long userId) throws ResourceNotFoundException, MethodArgumentNotValidException {
        CardEntity card = getById(cardEntity.getCardId());
        UserEntity user = userService.getById(userId);
        BoardEntity board = boardService.getById(boardId);
        ListEntity list = listService.getById(card.getList().getListId());

        if(!userService.userIsInTheBoard(user, board))
            throw new MethodArgumentNotValidException("User is not in this board!.");

        if(!boardService.boardHasList(board, list))
            throw new MethodArgumentNotValidException("Board does not have this list card!.");

        card.setName(cardEntity.getName());
        card.setDueDate(cardEntity.getDueDate());
        card.setDescription(cardEntity.getDescription());
        cardRepository.save(card);
        return CompletableFuture.completedFuture(null);
    }

    @Async("threadPoolTaskExecutor")
    public Future<?> closeCard(Long cardId, Long boardId, Long userId) throws ResourceNotFoundException, MethodArgumentNotValidException {
        UserEntity user = userService.getById(userId);
        BoardEntity board = boardService.getById(boardId);
        CardEntity card = getById(cardId);
        ListEntity list = listService.getById(card.getList().getListId());

        if(!userService.userIsInTheBoard(user, board))
            throw new MethodArgumentNotValidException("User is not in this board!.");

        if(!boardService.boardHasList(board, list))
            throw new MethodArgumentNotValidException("Board does not have this list card!.");

        if(card.getIsFinished())
        throw new MethodArgumentNotValidException("Card is already closed!.");

        card.setIsFinished(true);
        card.setFinishedDate(LocalDateTime.now());
        cardRepository.save(card);
        return CompletableFuture.completedFuture(null);
    }

    @Async("threadPoolTaskExecutor")
    public Future<?> openCard(Long cardId, Long boardId, Long userId) throws ResourceNotFoundException, MethodArgumentNotValidException {
        UserEntity user = userService.getById(userId);
        BoardEntity board = boardService.getById(boardId);
        CardEntity card = getById(cardId);
        ListEntity list = listService.getById(card.getList().getListId());

        if(!userService.userIsInTheBoard(user, board))
            throw new MethodArgumentNotValidException("User is not in this board!.");

        if(!boardService.boardHasList(board, list))
            throw new MethodArgumentNotValidException("Board does not have this list card!.");

        if(!card.getIsFinished())
        throw new MethodArgumentNotValidException("Card is not closed!.");

        card.setIsFinished(false);
        cardRepository.save(card);
        return CompletableFuture.completedFuture(null);
    }

    @Async("threadPoolTaskExecutor")
    public Future<?> moveCard(CardEntity cardEntity, Long listId, Long boardId, Long userId) throws ResourceNotFoundException, MethodArgumentNotValidException {
        UserEntity user = userService.getById(userId);
        BoardEntity board = boardService.getById(boardId);
        CardEntity card = getById(cardEntity.getCardId());
        ListEntity list = listService.getById(listId);
        String text = user.getFullName() + " moveu " + card.getName() + " de " + card.getList().getName() + " para " + list.getName();

        if(!userService.userIsInTheBoard(user, board))
            throw new MethodArgumentNotValidException("User is not in this board!.");

        if(!boardService.boardHasList(board, list))
            throw new MethodArgumentNotValidException("Board does not have this list!.");

        card.setPosition(cardEntity.getPosition());
        card.setList(list);
        cardRepository.save(card);

        activityService.create(text, board);
        
        return CompletableFuture.completedFuture(null);
    }
}