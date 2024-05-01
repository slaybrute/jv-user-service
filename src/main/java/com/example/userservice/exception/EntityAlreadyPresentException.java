package com.example.userservice.exception;

public class EntityAlreadyPresentException extends RuntimeException {
    public EntityAlreadyPresentException(String message) {
        super(message);
    }
}
