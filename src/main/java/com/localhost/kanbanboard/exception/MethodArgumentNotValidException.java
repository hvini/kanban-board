package com.localhost.kanbanboard.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

/**
 * MethodArgumentNotValidException
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class MethodArgumentNotValidException extends Exception {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public MethodArgumentNotValidException(String message) {
        super(message);
    }
}