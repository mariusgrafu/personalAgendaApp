package com.example.personalagendaapp.exception;

public class TaskNotFoundException extends NotFoundException {
    public TaskNotFoundException() {
        super("Task not found!");
    }
}
