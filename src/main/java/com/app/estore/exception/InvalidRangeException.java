package com.app.estore.exception;

import lombok.Data;

@Data
public class InvalidRangeException extends RuntimeException {

    private String message;

    public InvalidRangeException(String message) {
        this.message = message;
    }
}
