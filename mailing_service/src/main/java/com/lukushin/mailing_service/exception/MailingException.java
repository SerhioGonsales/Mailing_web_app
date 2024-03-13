package com.lukushin.mailing_service.exception;

public class MailingException extends RuntimeException{

    public MailingException(String message, Throwable cause) {
        super(message, cause);
    }
}
