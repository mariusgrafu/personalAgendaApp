package com.example.personalagendaapp.exception.advice;

import com.example.personalagendaapp.exception.BadAuthException;
import com.example.personalagendaapp.exception.BadRequestException;
import com.example.personalagendaapp.exception.ForbiddenActionException;
import com.example.personalagendaapp.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handle(MethodArgumentNotValidException e) {
        return ResponseEntity.badRequest()
                .body("Invalid value: \"" + e.getFieldError().getRejectedValue() +
                        "\" for field \"" + e.getFieldError().getField() +
                        "\" with message \"" + e.getFieldError().getDefaultMessage() + "\"");
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> handle(BadRequestException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(BadAuthException.class)
    public ResponseEntity<String> handle(BadAuthException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    @ExceptionHandler(ForbiddenActionException.class)
    public ResponseEntity<String> handle(ForbiddenActionException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(e.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handle(NotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }
}
