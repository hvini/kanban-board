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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import com.localhost.kanbanboard.service.CommentService;
import com.localhost.kanbanboard.entity.CommentEntity;
import com.localhost.kanbanboard.service.BoardService;
import com.localhost.kanbanboard.service.CardService;
import com.localhost.kanbanboard.entity.ListEntity;
import com.localhost.kanbanboard.entity.CardEntity;
import java.util.concurrent.CancellationException;
import org.springframework.stereotype.Controller;
import java.util.concurrent.ExecutionException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;
import java.util.concurrent.Future;
import java.util.List;

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
    @Autowired
    private BoardService boardService;

    @GetMapping("/{cardId}")
    public ResponseEntity<?> getById(@PathVariable("cardId") Long cardId) throws Exception {
        try {
            CardEntity card = cardService.getById(cardId);
            return new ResponseEntity<>(card, HttpStatus.OK);
        } catch(ResourceNotFoundException ex) {
            throw new ResourceNotFoundException(ex.getLocalizedMessage(), ex);
        }
    }

    @ApiOperation(value = "Find all comments in a card", notes = "Returns all comments from the informed card")
    @ApiResponses(value  = {
        @ApiResponse(code = 200, message = "Ok"),
        @ApiResponse(code = 401, message = "Not authenticated"),
        @ApiResponse(code = 404, message = "Not found"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    @GetMapping("/{cardId}/comments")
    public ResponseEntity<?> getAllComments(@PathVariable("cardId") Long cardId) throws Exception {
        try {
            List<CommentEntity> comments = cardService.getAllComments(cardId);
            return new ResponseEntity<>(comments, HttpStatus.OK);
        } catch(ResourceNotFoundException ex) {
            throw new ResourceNotFoundException(ex.getLocalizedMessage(), ex);
        }
    }

    @ApiOperation(value = "Create a card", notes = "Creates a new card in the provided list")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Card created successfully"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Not authenticated"),
        @ApiResponse(code = 404, message = "Not found"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody CardEntity cardEntity, @RequestParam("listId") Long listId, @RequestParam("userEmail") String userEmail, @RequestParam("boardId") Long boardId) throws Exception {
        Future<?> card = cardService.create(cardEntity, listId, userEmail, boardId);
        try {
            return new ResponseEntity<>(card.get(), HttpStatus.CREATED);
        } catch(InterruptedException | CancellationException | ExecutionException ex) {
            if(ex.getCause() instanceof ResourceNotFoundException) {
                throw new ResourceNotFoundException(ex.getLocalizedMessage(), ex);
            } else if(ex.getCause() instanceof MethodArgumentNotValidException)
                throw new MethodArgumentNotValidException(ex.getLocalizedMessage(), ex);
            throw new Exception(ex.getLocalizedMessage(), ex);
        }
    }

    @ApiOperation(value = "Update a card", notes = "Updates the given id card")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Card successfully updated"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Not authenticated"),
        @ApiResponse(code = 404, message = "Not found"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    @PutMapping("/{cardId}/update")
    public ResponseEntity<?> update(@RequestBody CardEntity cardEntity, @PathVariable("cardId") Long cardId, @RequestParam("boardId") Long boardId, @RequestParam("userEmail") String userEmail) throws Exception {
        cardEntity.setCardId(cardId);
        Future<?> card = cardService.update(cardEntity, boardId, userEmail);
        try {
            return new ResponseEntity<>(card.get(), HttpStatus.OK);
        } catch(InterruptedException | CancellationException | ExecutionException ex) {
            if(ex.getCause() instanceof ResourceNotFoundException) {
                throw new ResourceNotFoundException(ex.getLocalizedMessage(), ex);
            } else if(ex.getCause() instanceof MethodArgumentNotValidException)
                throw new MethodArgumentNotValidException(ex.getLocalizedMessage(), ex);
            throw new Exception(ex.getLocalizedMessage(), ex);
        }
    }


    @ApiOperation(value = "Close a card", notes = "Sets the given id card as closed")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Card successfully closed"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Not authenticated"),
        @ApiResponse(code = 404, message = "Not found"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    @PutMapping("/{cardId}/close")
    public ResponseEntity<?> close(@PathVariable("cardId") Long cardId, @RequestParam("boardId") Long boardId, @RequestParam("userEmail") String userEmail) throws Exception {
        Future<?> card = cardService.closeCard(cardId, boardId, userEmail);
        try {
            return new ResponseEntity<>(card.get(), HttpStatus.OK);
        } catch(InterruptedException | CancellationException | ExecutionException ex) {
            if(ex.getCause() instanceof ResourceNotFoundException) {
                throw new ResourceNotFoundException(ex.getLocalizedMessage(), ex);
            } else if(ex.getCause() instanceof MethodArgumentNotValidException)
                throw new MethodArgumentNotValidException(ex.getLocalizedMessage(), ex);
            throw new Exception(ex.getLocalizedMessage(), ex);
        }
    }

    @ApiOperation(value = "Open a card", notes = "Sets the given id card as opened")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Card successfully opened"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Not authenticated"),
        @ApiResponse(code = 404, message = "Not found"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    @PutMapping("/{cardId}/open")
    public ResponseEntity<?> open(@PathVariable("cardId") Long cardId, @RequestParam("boardId") Long boardId, @RequestParam("userEmail") String userEmail) throws Exception {
        Future<?> card = cardService.openCard(cardId, boardId, userEmail);
        try {
            return new ResponseEntity<>(card.get(), HttpStatus.OK);
        } catch(InterruptedException | CancellationException | ExecutionException ex) {
            if(ex.getCause() instanceof ResourceNotFoundException) {
                throw new ResourceNotFoundException(ex.getLocalizedMessage(), ex);
            } else if(ex.getCause() instanceof MethodArgumentNotValidException)
                throw new MethodArgumentNotValidException(ex.getLocalizedMessage(), ex);
            throw new Exception(ex.getLocalizedMessage(), ex);
        }
    }

    @ApiOperation(value = "Comment in a card", notes = "Add a comment in the given id card")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Comment successfully added to the card"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Not authenticated"),
        @ApiResponse(code = 404, message = "Not found"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    @PostMapping("/{cardId}/comment")
    public ResponseEntity<?> addComment(@RequestBody CommentEntity commentEntity, @PathVariable("cardId") Long cardId, @RequestParam("boardId") Long boardId, @RequestParam("userEmail") String userEmail) throws Exception {
        Future<?> comment = commentService.create(commentEntity, userEmail, boardId, cardId);
        try {
            return new ResponseEntity<>(comment.get(), HttpStatus.OK);
        } catch(InterruptedException | CancellationException | ExecutionException ex) {
            if(ex.getCause() instanceof ResourceNotFoundException) {
                throw new ResourceNotFoundException(ex.getLocalizedMessage(), ex);
            } else if(ex.getCause() instanceof MethodArgumentNotValidException)
                throw new MethodArgumentNotValidException(ex.getLocalizedMessage(), ex);
            throw new Exception(ex.getLocalizedMessage(), ex);
        }
    }

    @ApiOperation(value = "Update a comment", notes = "Updates the given id card comment")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Comment successfully updated"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Not authenticated"),
        @ApiResponse(code = 404, message = "Not found"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    @PutMapping("/{cardId}/comment/update")
    public ResponseEntity<?> updateComment(@RequestBody CommentEntity commentEntity, @PathVariable("cardId") Long cardId, @RequestParam("boardId") Long boardId, @RequestParam("userEmail") String userEmail, @RequestParam("commentId") Long commentId) throws Exception {
        commentEntity.setCommentId(commentId);
        Future<?> comment = commentService.update(commentEntity, userEmail, boardId, cardId);
        try {
            return new ResponseEntity<>(comment.get(), HttpStatus.OK);
        } catch(InterruptedException | CancellationException | ExecutionException ex) {
            if(ex.getCause() instanceof ResourceNotFoundException) {
                throw new ResourceNotFoundException(ex.getLocalizedMessage(), ex);
            } else if(ex.getCause() instanceof MethodArgumentNotValidException)
                throw new MethodArgumentNotValidException(ex.getLocalizedMessage(), ex);
            throw new Exception(ex.getLocalizedMessage(), ex);
        }
    }

    @ApiOperation(value = "Delete a comment", notes = "Deletes the given id card comment")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Comment successfully deleted"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Not authenticated"),
        @ApiResponse(code = 404, message = "Not found"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    @DeleteMapping("/{cardId}/comment/delete")
    public ResponseEntity<?> removeComment(@PathVariable("cardId") Long cardId, @RequestParam("boardId") Long boardId, @RequestParam("userEmail") String userEmail, @RequestParam("commentId") Long commentId) throws Exception {
        Future<?> comment = commentService.remove(commentId, userEmail, boardId, cardId);
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

    @ApiOperation(value = "Move a card", notes = "Move cards between lists")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Card successfully moved"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 401, message = "Not authenticated"),
        @ApiResponse(code = 404, message = "Not found"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    @PutMapping("/{cardId}/move")
    public ResponseEntity<?> moveCard(@RequestBody CardEntity cardEntity, @PathVariable("cardId") Long cardId, @RequestParam("listId") Long listId, @RequestParam("boardId") Long boardId, @RequestParam("userEmail") String userEmail) throws Exception {
        cardEntity.setCardId(cardId);
        Future<?> card = cardService.moveCard(cardEntity, listId, boardId, userEmail);
        try {
            card.get();
            List<ListEntity> lists = boardService.getAllLists(boardId);
            return new ResponseEntity<>(lists, HttpStatus.OK);
        } catch(InterruptedException | CancellationException | ExecutionException ex) {
            if(ex.getCause() instanceof ResourceNotFoundException) {
                throw new ResourceNotFoundException(ex.getLocalizedMessage(), ex);
            } else if(ex.getCause() instanceof MethodArgumentNotValidException)
                throw new MethodArgumentNotValidException(ex.getLocalizedMessage(), ex);
            throw new Exception(ex.getLocalizedMessage(), ex);
        }
    }
}