package com.intrum.exception;

public class ValidationException extends RuntimeException {

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String fieldName, String message) {
        super("Validation error. " + message + ": " + fieldName);
    }
}
