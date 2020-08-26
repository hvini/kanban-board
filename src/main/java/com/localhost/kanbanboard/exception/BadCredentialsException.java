package com.localhost.kanbanboard.exception;

/**
 * BadCredentialsException
 */
public class BadCredentialsException extends Exception {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public BadCredentialsException() {
        super();
    }

    public BadCredentialsException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public BadCredentialsException(String message) {
        super(message);
    }

    public BadCredentialsException(Throwable cause) {
        super(cause);
    }
}