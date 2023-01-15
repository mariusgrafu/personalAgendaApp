package com.example.personalagendaapp.exception;

public class OnlyAuthorAllowedException extends ForbiddenActionException {
    public OnlyAuthorAllowedException() {
        super("Action not allowed! Only the author is allowed to perform this action.");
    }
}
