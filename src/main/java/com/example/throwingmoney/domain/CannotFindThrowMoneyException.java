package com.example.throwingmoney.domain;

public class CannotFindThrowMoneyException extends RuntimeException{

    public CannotFindThrowMoneyException(String msg) {
        super(msg);
    }
}
