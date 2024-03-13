package com.lukushin.email_service.exception;

public class NoSuchEmailException extends RuntimeException {

    public NoSuchEmailException(String message) {
        super(message);
    }
}
