package com.example.personalagendaapp.exception;

public class CannotAcceptInviteException extends ForbiddenActionException {
    public CannotAcceptInviteException() {
        super("Cannot accept invite! Only invited user may accept an invite!");
    }
}
