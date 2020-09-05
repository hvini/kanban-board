package com.localhost.kanbanboard.controller;

import com.localhost.kanbanboard.exception.MethodArgumentNotValidException;
import com.localhost.kanbanboard.exception.ResourceNotFoundException;
import com.localhost.kanbanboard.service.ConfirmationTokenService;
import com.localhost.kanbanboard.entity.ConfirmationTokenEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import com.localhost.kanbanboard.service.BoardService;
import com.localhost.kanbanboard.entity.BoardEntity;
import org.springframework.stereotype.Controller;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.io.IOException;

/**
 * BoardController
 */
@Controller
@RequestMapping("/")
public class BoardController {
    @Autowired
    private BoardService boardService;
    @Autowired
    private ConfirmationTokenService confirmationTokenService;

    @PostMapping("/board/create/")
    public ResponseEntity<?> create(@RequestBody BoardEntity boardEntity, @RequestParam("userId") Long userId) throws Exception {
        try {
            boardService.create(boardEntity, userId);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch(ResourceNotFoundException ex) {
            throw new ResourceNotFoundException(ex.getLocalizedMessage(), ex);
        }
    }

    @PutMapping("/board/{boardId}/update/")
    public ResponseEntity<?> update(@RequestBody BoardEntity boardEntity, @PathVariable("boardId") Long boardId) throws Exception {
        boardEntity.setBoardId(boardId);
        try {
            boardService.update(boardEntity);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch(ResourceNotFoundException ex) {
            throw new ResourceNotFoundException(ex.getLocalizedMessage(), ex);
        }
    }

    @DeleteMapping("/board/{boardId}/delete/")
    public ResponseEntity<?> delete(@PathVariable("boardId") Long boardId, @RequestParam("userId") Long userId) throws Exception {
        try {
            boardService.delete(boardId, userId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch(ResourceNotFoundException ex) {
            throw new ResourceNotFoundException(ex.getLocalizedMessage(), ex);
        } catch(MethodArgumentNotValidException ex) {
            throw new MethodArgumentNotValidException(ex.getLocalizedMessage(), ex);
        }
    }

    @PutMapping("/board/{boardId}/favorite/")
    public ResponseEntity<?> favorite(@PathVariable("boardId") Long boardId, @RequestParam("userId") Long userId) throws Exception {
        try {
            boardService.favorite(userId, boardId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch(ResourceNotFoundException ex) {
            throw new ResourceNotFoundException(ex.getLocalizedMessage(), ex);
        } catch(MethodArgumentNotValidException ex) {
            throw new MethodArgumentNotValidException(ex.getLocalizedMessage(), ex);
        }
    }

    @PutMapping("/board/{boardId}/unfavorite/")
    public ResponseEntity<?> unfavorite(@PathVariable("boardId") Long boardId, @RequestParam("userId") Long userId) throws Exception {
        try {
            boardService.unfavorite(userId, boardId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch(ResourceNotFoundException ex) {
            throw new ResourceNotFoundException(ex.getLocalizedMessage(), ex);
        } catch(MethodArgumentNotValidException ex) {
            throw new MethodArgumentNotValidException(ex.getLocalizedMessage(), ex);
        }
    }

    @PostMapping("/board/send-invitation/")
    public ResponseEntity<?> sendInvitation(@RequestParam("collaboratorEmail") String collaboratorEmail, @RequestParam("boardId") Long boardId) throws Exception {
        try {
            boardService.inviteUserToBoard(collaboratorEmail, boardId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch(IOException ex) {
            throw new IOException(ex.getLocalizedMessage(), ex);
        } catch(ResourceNotFoundException ex) {
            throw new ResourceNotFoundException(ex.getLocalizedMessage(), ex);
        } catch(MethodArgumentNotValidException ex) {
            throw new MethodArgumentNotValidException(ex.getLocalizedMessage(), ex);
        }
    }

    @PostMapping("/board/invitation/{token}/")
    public ResponseEntity<?> acceptInvitation(@PathVariable("token") String token, @RequestParam("boardId") Long boardId) throws Exception {
        try {
            ConfirmationTokenEntity confirmationToken = confirmationTokenService.getByToken(token);
            
            boardService.acceptInvitation(confirmationToken, boardId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch(ResourceNotFoundException ex) {
            throw new ResourceNotFoundException(ex.getLocalizedMessage(), ex);
        } catch(MethodArgumentNotValidException ex) {
            throw new MethodArgumentNotValidException(ex.getLocalizedMessage(), ex);
        }
    }
}