package com.example.personalagendaapp.exception;

public class DuplicateEmailException extends BadRequestException {
    public DuplicateEmailException() {
        super("An user with the same email address already exists.");
    }
}
