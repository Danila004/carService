package ru.vsu.sheluhin.carService.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.vsu.sheluhin.carService.exeption.ValidationException;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<String> handlerValidationException(final ValidationException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }
}
