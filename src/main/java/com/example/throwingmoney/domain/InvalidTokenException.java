package com.example.throwingmoney.domain;

public class InvalidTokenException extends RuntimeException{

    public InvalidTokenException(String msg) {
        super(msg);
    }
}
