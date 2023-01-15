package com.example.personalagendaapp.exception;

public class CannotAddNoteException extends ForbiddenActionException {
    public CannotAddNoteException() {
        super("Cannot add note! Only a collaborator may add a note.");
    }
}
