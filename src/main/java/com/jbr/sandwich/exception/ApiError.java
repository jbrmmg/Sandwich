package com.jbr.sandwich.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@SuppressWarnings("unused")
class ApiError {
    private HttpStatus status;
    private final LocalDateTime timestamp;
    private final String message;
    private final String debugMessage;

    private ApiError() {
        this.message = "";
        this.debugMessage = "";
        this.timestamp = LocalDateTime.now();
    }

    ApiError(HttpStatus status, String message, Throwable ex) {
        this.status = status;
        this.message = message;
        this.debugMessage = ex.getLocalizedMessage();
        this.timestamp = LocalDateTime.now();
    }

    HttpStatus getStatus() { return this.status; }
}
