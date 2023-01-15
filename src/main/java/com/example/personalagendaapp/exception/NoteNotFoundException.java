package com.example.personalagendaapp.exception;

public class NoteNotFoundException extends NotFoundException {
    public NoteNotFoundException() {
        super("Note not found!");
    }
}
