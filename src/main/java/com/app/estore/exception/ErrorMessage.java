package com.app.estore.exception;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorMessage {
    private LocalDateTime localDateTime;
    private String errorMessage;
    private String exceptionClass;

    public ErrorMessage(String errorMessage, String exceptionClass) {
        this.errorMessage = errorMessage;
        this.exceptionClass = exceptionClass;
        this.localDateTime = LocalDateTime.now();
    }
}
