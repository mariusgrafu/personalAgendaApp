package com.example.personalagendaapp.exception;

public class EventNotFoundException extends NotFoundException {
    public EventNotFoundException() {
        super("Event not found!");
    }
}
