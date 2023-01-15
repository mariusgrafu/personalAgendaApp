package com.example.personalagendaapp.exception;

public class UserBadLoginException extends BadRequestException {
    public UserBadLoginException() {
        super("Authentication failed! Wrong email or password!");
    }
}
