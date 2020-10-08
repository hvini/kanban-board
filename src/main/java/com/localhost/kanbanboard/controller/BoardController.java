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
import org.springframework.web.bind.annotation.GetMapping;
import com.localhost.kanbanboard.entity.ActivityEntity;
import com.localhost.kanbanboard.service.BoardService;
import com.localhost.kanbanboard.entity.BoardEntity;
import com.localhost.kanbanboard.entity.ListEntity;
import java.util.concurrent.CancellationException;
import org.springframework.stereotype.Controller;
import org.springframework.http.ResponseEntity;
import java.util.concurrent.ExecutionException;
import org.springframework.http.HttpStatus;
import java.util.concurrent.Future;
import java.io.IOException;
import java.util.List;

/**
 * BoardController
 */
@Controller
@RequestMapping("/b")
public class BoardController {
    @Autowired
    private BoardService boardService;
    @Autowired
    private ConfirmationTokenService confirmationTokenService;

    @GetMapping("/{boardId}/lists")
    public ResponseEntity<?> getAllLists(@PathVariable("boardId") Long boardId) throws Exception {
        try {
            List<ListEntity> lists = boardService.getAllLists(boardId);
            return new ResponseEntity<>(lists, HttpStatus.OK);
        } catch(ResourceNotFoundException ex) {
            throw new ResourceNotFoundException(ex.getLocalizedMessage(), ex);
        }
    }

    @GetMapping("/{boardId}/activities")
    public ResponseEntity<?> getAllActivities(@PathVariable("boardId") Long boardId) throws Exception {
        try {
            List<ActivityEntity> activities = boardService.getAllActivities(boardId);
            return new ResponseEntity<>(activities, HttpStatus.OK);
        } catch(ResourceNotFoundException ex) {
            throw new ResourceNotFoundException(ex.getLocalizedMessage(), ex);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody BoardEntity boardEntity, @RequestParam("userId") Long userId) throws Exception {
        Future<?> board = boardService.create(boardEntity, userId);
        try {
            board.get();
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch(InterruptedException | CancellationException| ExecutionException ex) {
            if(ex.getCause() instanceof ResourceNotFoundException)
                throw new ResourceNotFoundException(ex.getLocalizedMessage(), ex);
            throw new Exception(ex.getLocalizedMessage(), ex);
        }
    }

    @PutMapping("/{boardId}/update")
    public ResponseEntity<?> update(@RequestBody BoardEntity boardEntity, @PathVariable("boardId") Long boardId, @RequestParam("userId") Long userId) throws Exception {
        boardEntity.setBoardId(boardId);
        Future<?> board = boardService.update(boardEntity, userId);
        try {
            board.get();
            return new ResponseEntity<>(HttpStatus.OK);
        } catch(InterruptedException | CancellationException| ExecutionException ex) {
            if(ex.getCause() instanceof ResourceNotFoundException) {
                throw new ResourceNotFoundException(ex.getLocalizedMessage(), ex);
            } else if(ex.getCause() instanceof MethodArgumentNotValidException)
                throw new MethodArgumentNotValidException(ex.getLocalizedMessage(), ex);
            throw new Exception(ex.getLocalizedMessage(), ex);
        }
    }

    @DeleteMapping("/{boardId}/delete")
    public ResponseEntity<?> delete(@PathVariable("boardId") Long boardId, @RequestParam("userId") Long userId) throws Exception {
        Future<?> board = boardService.delete(boardId, userId);
        try {
            board.get();
            return new ResponseEntity<>(HttpStatus.OK);
        } catch(InterruptedException | CancellationException| ExecutionException ex) {
            if(ex.getCause() instanceof ResourceNotFoundException) {
                throw new ResourceNotFoundException(ex.getLocalizedMessage(), ex);
            } else if(ex.getCause() instanceof MethodArgumentNotValidException)
                throw new MethodArgumentNotValidException(ex.getLocalizedMessage(), ex);
            throw new Exception(ex.getLocalizedMessage(), ex);
        }
    }

    @PutMapping("/{boardId}/favorite")
    public ResponseEntity<?> favorite(@PathVariable("boardId") Long boardId, @RequestParam("userId") Long userId) throws Exception {
        Future<?> board = boardService.favorite(userId, boardId);
        try {
            board.get();
            return new ResponseEntity<>(HttpStatus.OK);
        } catch(InterruptedException | CancellationException| ExecutionException ex) {
            if(ex.getCause() instanceof ResourceNotFoundException) {
                throw new ResourceNotFoundException(ex.getLocalizedMessage(), ex);
            } else if(ex.getCause() instanceof MethodArgumentNotValidException)
                throw new MethodArgumentNotValidException(ex.getLocalizedMessage(), ex);
            throw new Exception(ex.getLocalizedMessage(), ex);
        }
    }

    @PutMapping("/{boardId}/unfavorite")
    public ResponseEntity<?> unfavorite(@PathVariable("boardId") Long boardId, @RequestParam("userId") Long userId) throws Exception {
        Future<?> board = boardService.unfavorite(userId, boardId);
        try {
            board.get();
            return new ResponseEntity<>(HttpStatus.OK);
        } catch(InterruptedException | CancellationException| ExecutionException ex) {
            if(ex.getCause() instanceof ResourceNotFoundException) {
                throw new ResourceNotFoundException(ex.getLocalizedMessage(), ex);
            } else if(ex.getCause() instanceof MethodArgumentNotValidException)
                throw new MethodArgumentNotValidException(ex.getLocalizedMessage(), ex);
            throw new Exception(ex.getLocalizedMessage(), ex);
        }
    }

    @PostMapping("/send-invitation/{collaboratorEmail}")
    public ResponseEntity<?> sendInvitation(@PathVariable("collaboratorEmail") String collaboratorEmail, @RequestParam("boardId") Long boardId) throws Exception {
        Future<?> board = boardService.inviteUserToBoard(collaboratorEmail, boardId);
        try {
            board.get();
            return new ResponseEntity<>(HttpStatus.OK);
        } catch(InterruptedException | CancellationException| ExecutionException ex) {
            if(ex.getCause() instanceof ResourceNotFoundException) {
                throw new ResourceNotFoundException(ex.getLocalizedMessage(), ex);
            } else if(ex.getCause() instanceof MethodArgumentNotValidException) {
                throw new MethodArgumentNotValidException(ex.getLocalizedMessage(), ex);
            } else if(ex.getCause() instanceof IOException)
                throw new IOException(ex.getLocalizedMessage(), ex);
            throw new Exception(ex.getLocalizedMessage(), ex);
        }
    }

    @PostMapping("/invitation/{token}")
    public ResponseEntity<?> acceptInvitation(@PathVariable("token") String token, @RequestParam("boardId") Long boardId) throws Exception {
        try {
            ConfirmationTokenEntity confirmationToken = confirmationTokenService.getByToken(token);   
            Future<?> board = boardService.acceptInvitation(confirmationToken, boardId);
            try {
                board.get();
                return new ResponseEntity<>(HttpStatus.OK);
            } catch(InterruptedException | CancellationException| ExecutionException ex) {
                if(ex.getCause() instanceof ResourceNotFoundException) {
                    throw new ResourceNotFoundException(ex.getLocalizedMessage(), ex);
                } else if(ex.getCause() instanceof MethodArgumentNotValidException)
                    throw new MethodArgumentNotValidException(ex.getLocalizedMessage(), ex);
                throw new Exception(ex.getLocalizedMessage(), ex);
            }
        } catch(ResourceNotFoundException ex) {
            throw new ResourceNotFoundException(ex.getLocalizedMessage(), ex);
        }
    }
}