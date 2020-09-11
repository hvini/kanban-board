package com.localhost.kanbanboard.controller;

import com.localhost.kanbanboard.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import com.localhost.kanbanboard.service.ListService;
import com.localhost.kanbanboard.entity.ListEntity;
import org.springframework.stereotype.Controller;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

/**
 * ListController
 */
@Controller
@RequestMapping("/")
public class ListController {
    @Autowired
    private ListService listService;

    @PostMapping("/l/create/")
    public ResponseEntity<?> create(@RequestBody ListEntity listEntity, @RequestParam("boardId") Long boardId, @RequestParam("userId") Long userId) throws Exception {
        try {
            listService.create(listEntity, boardId, userId);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch(ResourceNotFoundException ex) {
            throw new ResourceNotFoundException(ex.getLocalizedMessage(), ex);
        }
    }

    @PutMapping("/l/{listId}/update/")
    public ResponseEntity<?> update(@RequestBody ListEntity listEntity, @PathVariable("listId") Long listId) throws Exception {
        listEntity.setListId(listId);
        try {
            listService.update(listEntity);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch(ResourceNotFoundException ex) {
            throw new ResourceNotFoundException(ex.getLocalizedMessage(), ex);
        }
    }
}