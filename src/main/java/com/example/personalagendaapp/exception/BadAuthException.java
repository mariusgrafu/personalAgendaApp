package com.example.personalagendaapp.exception;

public class BadAuthException extends RuntimeException {
    public BadAuthException() {
        super("Authentication failed!");
    }
}
