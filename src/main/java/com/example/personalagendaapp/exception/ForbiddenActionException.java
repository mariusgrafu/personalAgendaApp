package com.example.personalagendaapp.exception;

public class ForbiddenActionException extends RuntimeException{
    public ForbiddenActionException(String message) {
        super(message);
    }
}
