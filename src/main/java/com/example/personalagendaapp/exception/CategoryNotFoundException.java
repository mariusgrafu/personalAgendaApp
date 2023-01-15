package com.example.personalagendaapp.exception;

public class CategoryNotFoundException extends NotFoundException {
    public CategoryNotFoundException() {
        super("Category not found!");
    }
}
