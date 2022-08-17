package com.example.throwingmoney.domain;

public class AlreadyReceiveException extends RuntimeException{

    public AlreadyReceiveException(String msg) {
        super(msg);
    }
}
