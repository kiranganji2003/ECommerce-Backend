package com.app.estore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorMessage> handleBadCred(BadCredentialsException ex) {
        return new ResponseEntity<>(new ErrorMessage("Invalid username or password", "BadCredentialsException.class"), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorMessage> handleAccess(AccessDeniedException ex) {
        return new ResponseEntity<>(new ErrorMessage(ex.getMessage(), "AccessDeniedException.class"), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleOther(Exception ex) {
        return new ResponseEntity<>(new ErrorMessage(ex.getMessage(), "Exception.class"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
