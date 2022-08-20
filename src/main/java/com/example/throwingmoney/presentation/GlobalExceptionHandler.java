package com.example.throwingmoney.presentation;

import com.example.throwingmoney.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity handleCannotFindUrlException(InvalidTokenException e) {
        return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ReceiveMoneyException.class)
    public ResponseEntity handleCannotFindUrlException(ReceiveMoneyException e) {
        return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SelfReceiveException.class)
    public ResponseEntity handleCannotFindUrlException(SelfReceiveException e) {
        return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AlreadyReceiveException.class)
    public ResponseEntity handleCannotFindUrlException(AlreadyReceiveException e) {
        return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CannotFindThrowMoneyException.class)
    public ResponseEntity handleCannotFindUrlException(CannotFindThrowMoneyException e) {
        return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
    }





}