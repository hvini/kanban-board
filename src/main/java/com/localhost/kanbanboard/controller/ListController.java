package com.localhost.kanbanboard.controller;

import com.localhost.kanbanboard.exception.MethodArgumentNotValidException;
import com.localhost.kanbanboard.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import com.localhost.kanbanboard.service.ListService;
import com.localhost.kanbanboard.entity.CardEntity;
import com.localhost.kanbanboard.entity.ListEntity;
import java.util.concurrent.CancellationException;
import org.springframework.stereotype.Controller;
import org.springframework.http.ResponseEntity;
import java.util.concurrent.ExecutionException;
import org.springframework.http.HttpStatus;
import java.util.concurrent.Future;
import java.util.List;

/**
 * ListController
 */
@Controller
@RequestMapping("/l")
public class ListController {
    @Autowired
    private ListService listService;

    @GetMapping("/{listId}/cards")
    public ResponseEntity<?> getAllCards(@PathVariable("listId") Long listId) throws Exception {
        try {
            List<CardEntity> cards = listService.getAllCards(listId);
            return new ResponseEntity<>(cards, HttpStatus.OK);
        } catch(ResourceNotFoundException ex) {
            throw new ResourceNotFoundException(ex.getLocalizedMessage(), ex);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody ListEntity listEntity, @RequestParam("boardId") Long boardId, @RequestParam("userId") Long userId) throws Exception {
        Future<?> list = listService.create(listEntity, boardId, userId);
        try {
            list.get();
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch(InterruptedException | CancellationException | ExecutionException ex) {
            if(ex.getCause() instanceof ResourceNotFoundException) {
                throw new ResourceNotFoundException(ex.getLocalizedMessage(), ex);
            } else if(ex.getCause() instanceof MethodArgumentNotValidException)
                throw new MethodArgumentNotValidException(ex.getLocalizedMessage(), ex);
            throw new Exception(ex.getLocalizedMessage(), ex);
        }
    }

    @PutMapping("/{listId}/update")
    public ResponseEntity<?> update(@RequestBody ListEntity listEntity, @PathVariable("listId") Long listId, @RequestParam("boardId") Long boardId, @RequestParam("userId") Long userId) throws Exception {
        listEntity.setListId(listId);
        Future<?> list = listService.update(listEntity, boardId, userId);
        try {
            list.get();
            return new ResponseEntity<>(HttpStatus.OK);
        } catch(InterruptedException | CancellationException | ExecutionException ex) {
            if(ex.getCause() instanceof ResourceNotFoundException)
                throw new ResourceNotFoundException(ex.getLocalizedMessage(), ex);
            throw new Exception(ex.getLocalizedMessage(), ex);
        }
    }
}