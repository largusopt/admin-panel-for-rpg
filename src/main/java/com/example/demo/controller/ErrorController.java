package com.example.demo.controller;


import com.example.demo.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ErrorController {

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("ID должен быть числового типа");
    }

    @ExceptionHandler
    public ErrorResponse handleMethodArgumentNotValidException(final MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();

        exception.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        return new ErrorResponse(errors, HttpStatus.BAD_REQUEST.name(), LocalDateTime.now());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBadEnum(HttpMessageNotReadableException ex) {
        return new ErrorResponse(
                Map.of("race", "Некорректное значение: допустимые значения — HUMAN, ELF, DWARF, ORC"),
                HttpStatus.BAD_REQUEST.name(),
                LocalDateTime.now()
        );
    }

}