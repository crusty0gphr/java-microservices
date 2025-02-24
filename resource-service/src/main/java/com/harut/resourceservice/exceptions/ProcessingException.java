package com.harut.resourceservice.exceptions;

public class ProcessingException extends RuntimeException {
    public ProcessingException(String message, Exception ex) {
        super(message);
    }
}
