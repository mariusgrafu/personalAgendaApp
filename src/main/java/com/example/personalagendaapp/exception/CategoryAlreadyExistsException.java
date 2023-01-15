package com.example.personalagendaapp.exception;

public class CategoryAlreadyExistsException extends BadRequestException {
    public CategoryAlreadyExistsException() {
        super("Category already exists!");
    }
}
