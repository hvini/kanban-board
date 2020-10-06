package com.localhost.kanbanboard.controller;

import com.localhost.kanbanboard.exception.MethodArgumentNotValidException;
import com.localhost.kanbanboard.exception.ResourceNotFoundException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import com.localhost.kanbanboard.service.CommentService;
import com.localhost.kanbanboard.entity.CommentEntity;
import com.localhost.kanbanboard.service.CardService;
import com.localhost.kanbanboard.entity.CardEntity;
import java.util.concurrent.CancellationException;
import org.springframework.stereotype.Controller;
import java.util.concurrent.ExecutionException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.util.concurrent.Future;

/**
 * CardController
 */
@Controller
@RequestMapping("/c")
public class CardController {
    @Autowired
    private CardService cardService;
    @Autowired
    private CommentService commentService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody CardEntity cardEntity, @RequestParam("listId") Long listId, @RequestParam("userId") Long userId, @RequestParam("boardId") Long boardId) throws Exception {
        Future<?> card = cardService.create(cardEntity, listId, userId, boardId);
        try {
            card.get();
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch(InterruptedException | CancellationException | ExecutionException ex) {
            if(ex.getCause() instanceof ResourceNotFoundException) {
                throw new ResourceNotFoundException(ex.getLocalizedMessage(), ex);
            } else if(ex.getCause() instanceof MethodArgumentNotValidException)
                throw new MethodArgumentNotValidException(ex.getLocalizedMessage(), ex);
            throw new Exception(ex.getLocalizedMessage(), ex);
        }
    }

    @PutMapping("/{cardId}/update")
    public ResponseEntity<?> update(@RequestBody CardEntity cardEntity, @PathVariable("cardId") Long cardId, @RequestParam("boardId") Long boardId, @RequestParam("userId") Long userId) throws Exception {
        cardEntity.setCardId(cardId);
        Future<?> card = cardService.update(cardEntity, boardId, userId);
        try {
            card.get();
            return new ResponseEntity<>(HttpStatus.OK);
        } catch(InterruptedException | CancellationException | ExecutionException ex) {
            if(ex.getCause() instanceof ResourceNotFoundException) {
                throw new ResourceNotFoundException(ex.getLocalizedMessage(), ex);
            } else if(ex.getCause() instanceof MethodArgumentNotValidException)
                throw new MethodArgumentNotValidException(ex.getLocalizedMessage(), ex);
            throw new Exception(ex.getLocalizedMessage(), ex);
        }
    }

    @PutMapping("/{cardId}/close")
    public ResponseEntity<?> close(@PathVariable("cardId") Long cardId, @RequestParam("boardId") Long boardId, @RequestParam("userId") Long userId) throws Exception {
        Future<?> card = cardService.closeCard(cardId, boardId, userId);
        try {
            card.get();
            return new ResponseEntity<>(HttpStatus.OK);
        } catch(InterruptedException | CancellationException | ExecutionException ex) {
            if(ex.getCause() instanceof ResourceNotFoundException) {
                throw new ResourceNotFoundException(ex.getLocalizedMessage(), ex);
            } else if(ex.getCause() instanceof MethodArgumentNotValidException)
                throw new MethodArgumentNotValidException(ex.getLocalizedMessage(), ex);
            throw new Exception(ex.getLocalizedMessage(), ex);
        }
    }

    @PutMapping("/{cardId}/open")
    public ResponseEntity<?> open(@PathVariable("cardId") Long cardId, @RequestParam("boardId") Long boardId, @RequestParam("userId") Long userId) throws Exception {
        Future<?> card = cardService.openCard(cardId, boardId, userId);
        try {
            card.get();
            return new ResponseEntity<>(HttpStatus.OK);
        } catch(InterruptedException | CancellationException | ExecutionException ex) {
            if(ex.getCause() instanceof ResourceNotFoundException) {
                throw new ResourceNotFoundException(ex.getLocalizedMessage(), ex);
            } else if(ex.getCause() instanceof MethodArgumentNotValidException)
                throw new MethodArgumentNotValidException(ex.getLocalizedMessage(), ex);
            throw new Exception(ex.getLocalizedMessage(), ex);
        }
    }

    @PostMapping("/{cardId}/comment")
    public ResponseEntity<?> addComment(@RequestBody CommentEntity commentEntity, @PathVariable("cardId") Long cardId, @RequestParam("boardId") Long boardId, @RequestParam("userId") Long userId) throws Exception {
        Future<?> comment = commentService.create(commentEntity, userId, boardId, cardId);
        try {
            comment.get();
            return new ResponseEntity<>(HttpStatus.OK);
        } catch(InterruptedException | CancellationException | ExecutionException ex) {
            if(ex.getCause() instanceof ResourceNotFoundException) {
                throw new ResourceNotFoundException(ex.getLocalizedMessage(), ex);
            } else if(ex.getCause() instanceof MethodArgumentNotValidException)
                throw new MethodArgumentNotValidException(ex.getLocalizedMessage(), ex);
            throw new Exception(ex.getLocalizedMessage(), ex);
        }
    }

    @PutMapping("/{cardId}/comment/update")
    public ResponseEntity<?> updateComment(@RequestBody CommentEntity commentEntity, @PathVariable("cardId") Long cardId, @RequestParam("boardId") Long boardId, @RequestParam("userId") Long userId, @RequestParam("commentId") Long commentId) throws Exception {
        commentEntity.setCommentId(commentId);
        Future<?> comment = commentService.update(commentEntity, userId, boardId, cardId);
        try {
            comment.get();
            return new ResponseEntity<>(HttpStatus.OK);
        } catch(InterruptedException | CancellationException | ExecutionException ex) {
            if(ex.getCause() instanceof ResourceNotFoundException) {
                throw new ResourceNotFoundException(ex.getLocalizedMessage(), ex);
            } else if(ex.getCause() instanceof MethodArgumentNotValidException)
                throw new MethodArgumentNotValidException(ex.getLocalizedMessage(), ex);
            throw new Exception(ex.getLocalizedMessage(), ex);
        }
    }

    @DeleteMapping("/{cardId}/comment/delete")
    public ResponseEntity<?> removeComment(@PathVariable("cardId") Long cardId, @RequestParam("boardId") Long boardId, @RequestParam("userId") Long userId, @RequestParam("commentId") Long commentId) throws Exception {
        Future<?> comment = commentService.remove(commentId, userId, boardId, cardId);
        try {
            comment.get();
            return new ResponseEntity<>(HttpStatus.OK);
        } catch(InterruptedException | CancellationException | ExecutionException ex) {
            if(ex.getCause() instanceof ResourceNotFoundException) {
                throw new ResourceNotFoundException(ex.getLocalizedMessage(), ex);
            } else if(ex.getCause() instanceof MethodArgumentNotValidException)
                throw new MethodArgumentNotValidException(ex.getLocalizedMessage(), ex);
            throw new Exception(ex.getLocalizedMessage(), ex);
        }
    }
}