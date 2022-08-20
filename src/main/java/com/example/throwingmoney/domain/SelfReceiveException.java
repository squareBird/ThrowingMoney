package com.example.throwingmoney.domain;

public class SelfReceiveException extends RuntimeException{

    public SelfReceiveException(String msg) {
        super(msg);
    }
}
