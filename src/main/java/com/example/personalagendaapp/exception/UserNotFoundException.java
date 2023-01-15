package com.example.personalagendaapp.exception;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException() {
        super("User not found!");
    }

    public UserNotFoundException(long id) {
        super("User with id \"" + id + "\" was not found!");
    }
}
