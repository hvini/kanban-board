package com.localhost.kanbanboard.handler.exception;

import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.localhost.kanbanboard.exception.MethodArgumentNotValidException;
import com.localhost.kanbanboard.exception.ResourceNotFoundException;
import com.localhost.kanbanboard.exception.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import com.localhost.kanbanboard.exception.ApiException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

/**
 * CustomRestExceptionHandler
 */
@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {
    // 400 bad request
    @ExceptionHandler({ MethodArgumentNotValidException.class })
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        ApiException apiException = new ApiException(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), "An entry does not exist or is invalid!.");
        return buildResponseEntity(apiException);
    }

    // 404 not found
    @ExceptionHandler({ ResourceNotFoundException.class })
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ApiException apiException = new ApiException(HttpStatus.NOT_FOUND, ex.getLocalizedMessage(), "The requested resource was not found!.");
        return buildResponseEntity(apiException);
    }

    // 500 internal server error
    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
        ApiException apiException = new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage(), "Error processing the request!.");
        return buildResponseEntity(apiException);
    }

    // 404 not found
    @ExceptionHandler({ BadCredentialsException.class })
    public ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException ex, WebRequest request) {
        ApiException apiException = new ApiException(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), "Bad Credentials!.");
        return buildResponseEntity(apiException);
    }

    private ResponseEntity<Object> buildResponseEntity(ApiException apiException) {
        return new ResponseEntity<Object>(apiException, new HttpHeaders(), apiException.getStatus());
    }
}