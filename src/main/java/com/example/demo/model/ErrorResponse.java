package com.example.demo.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@RequiredArgsConstructor
@Getter
@Setter
public class ErrorResponse {
    private final String message;
    private final String status;
    private final LocalDateTime timestamp;
    private final Map<String, String> errors;

    public ErrorResponse(String message, String status, LocalDateTime timestamp) {
        this.message = message;
        this.status = status;
        this.timestamp = timestamp;
        this.errors = null;
    }

    public ErrorResponse(Map<String, String> errors, String status, LocalDateTime timestamp) {
        this.errors = errors;
        this.message = "Не удалось выполнить действие.";
        this.status = status;
        this.timestamp = timestamp;
    }
}
