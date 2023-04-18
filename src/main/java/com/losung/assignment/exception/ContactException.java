package com.losung.assignment.exception;

public class ContactException extends RuntimeException {

    public ContactException(String message) {
        super(message);
    }

    private String message;
}
