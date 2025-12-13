package com.app.estore.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class ErrorMessage {
    private LocalDateTime localDateTime;
    private String errorMessage;
    private HttpStatus httpStatus;

    public ErrorMessage(String errorMessage, HttpStatus httpStatus) {
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
        this.localDateTime = LocalDateTime.now();
    }
}
