package com.example.personalagendaapp.exception;

public class CannotDeleteInviteException extends ForbiddenActionException {
    public CannotDeleteInviteException() {
        super("Invite cannot be deleted! Only Author or Invited User can delete an invite!");
    }
}
