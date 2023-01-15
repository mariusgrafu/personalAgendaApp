package com.example.personalagendaapp.exception;

public class InviteNotFoundException extends NotFoundException {
    public InviteNotFoundException() {
        super("Invite not found!");
    }
}
