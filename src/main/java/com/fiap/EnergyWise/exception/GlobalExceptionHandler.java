package com.FIAP.EnergyWise.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.Date;
import java.time.LocalDate;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetail> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ErrorDetail
                erro = new ErrorDetail(Date.valueOf(LocalDate.now()), ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(erro, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorDetail> handleBadRequestException(BadRequestException ex) {
        ErrorDetail
                erro = new ErrorDetail(Date.valueOf(LocalDate.now()), ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(erro, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDetail> handleIllegalArgumentException(IllegalArgumentException ex) {
        ErrorDetail
                erro = new ErrorDetail(Date.valueOf(LocalDate.now()), ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(erro, HttpStatus.BAD_REQUEST);
    }


}

