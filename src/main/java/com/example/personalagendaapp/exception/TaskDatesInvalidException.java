package com.example.personalagendaapp.exception;

public class TaskDatesInvalidException extends BadRequestException {
    public TaskDatesInvalidException() {
        super("Task dates are invalid! End date must be set after start date");
    }
}
