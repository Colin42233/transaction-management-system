package com.home.work.trans.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TransactionBusinessException.class)
    @ResponseBody
    public ResponseEntity<String> handleTransactionError(TransactionBusinessException re){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(re.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<String> handleValidationError(MethodArgumentNotValidException ex){
        return ResponseEntity.badRequest().body("Validation error: " + ex.getMessage());
    }

}
