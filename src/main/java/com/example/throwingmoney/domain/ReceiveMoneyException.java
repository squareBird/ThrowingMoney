package com.example.throwingmoney.domain;

public class ReceiveMoneyException extends RuntimeException {

    public ReceiveMoneyException(String msg) {
        super(msg);
    }
}
