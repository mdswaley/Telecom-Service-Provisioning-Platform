package com.example.Customer.Service.Advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleResourceNotFound(ResourceNotFoundException ex) {

        ApiError error = ApiError.builder()
                .timeStamp(LocalDateTime.now())
                .error(ex.getMessage())
                .statusCode(HttpStatus.NOT_FOUND)
                .build();

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiError> handleRuntimeException(RuntimeException ex) {

        ApiError error = ApiError.builder()
                .timeStamp(LocalDateTime.now())
                .error(ex.getMessage())
                .statusCode(HttpStatus.BAD_REQUEST)
                .build();

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleException(Exception ex) {

        ApiError error = ApiError.builder()
                .timeStamp(LocalDateTime.now())
                .error("Internal Server Error")
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
