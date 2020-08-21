package com.localhost.kanbanboard.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

/**
 * ResourceNotFoundException
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends Exception {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException(String message) {
        super(message);
    }
}