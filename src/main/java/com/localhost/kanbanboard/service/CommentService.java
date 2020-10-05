package com.localhost.kanbanboard.service;

import com.localhost.kanbanboard.exception.MethodArgumentNotValidException;
import com.localhost.kanbanboard.exception.ResourceNotFoundException;
import com.localhost.kanbanboard.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import com.localhost.kanbanboard.entity.CommentEntity;
import com.localhost.kanbanboard.entity.CardEntity;
import com.localhost.kanbanboard.entity.UserEntity;
import org.springframework.stereotype.Service;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.Optional;

/**
 * CommentService
 */

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private CardService cardService;
    @Autowired
    private ActivityService activityService;

    public CommentEntity getById(Long commentId) throws ResourceNotFoundException {
        Optional<CommentEntity> comment = commentRepository.findById(commentId);

        if(comment.isEmpty())
            throw new ResourceNotFoundException("Invalid comment identification!.");

        return comment.get();
    }

    @Async("threadPoolTaskExecutor")
    public Future<?> create(CommentEntity comment, Long userId, Long cardId) throws ResourceNotFoundException {
        UserEntity user = userService.getById(userId);
        CardEntity card = cardService.getById(cardId);
        String text = user.getFullName() + " comentou " + comment.getText() + " em " + card.getName();

        comment.setUser(user);
        comment.setCard(card);
        commentRepository.save(comment);

        activityService.create(text, card.getList().getBoard());
        return CompletableFuture.completedFuture(null);
    }

    @Async("threadPoolTaskExecutor")
    public Future<?> update(CommentEntity commentEntity, Long userId, Long cardId) throws ResourceNotFoundException, MethodArgumentNotValidException {
        UserEntity user = userService.getById(userId);
        CardEntity card = cardService.getById(cardId);
        CommentEntity comment = getById(commentEntity.getCommentId());
        String text = user.getFullName() + " atualizou o comentario " + comment.getText() + " em " + card.getName();

        if(!comment.getUser().equals(user))
            throw new MethodArgumentNotValidException("User did not write this comment!.");

        if(!comment.getCard().equals(card))
            throw new MethodArgumentNotValidException("Comment does not belong to this card!.");

        comment.setText(commentEntity.getText());
        commentRepository.save(comment);

        activityService.create(text, card.getList().getBoard());
        return CompletableFuture.completedFuture(null);
    }

    @Async("threadPoolTaskExecutor")
    public Future<?> remove(Long commentId, Long userId, Long cardId) throws ResourceNotFoundException, MethodArgumentNotValidException {
        CommentEntity comment = getById(commentId);
        UserEntity user = userService.getById(userId);
        CardEntity card = cardService.getById(cardId);
        String text = user.getFullName() + " removeu o comentario " + comment.getText() + " de " + card.getName();

        if(!comment.getUser().equals(user))
            throw new MethodArgumentNotValidException("User did not write this comment!.");

        if(!comment.getCard().equals(card))
            throw new MethodArgumentNotValidException("Comment does not belong to this card!.");

        commentRepository.delete(comment);

        activityService.create(text, card.getList().getBoard());
        return CompletableFuture.completedFuture(null);
    }
}