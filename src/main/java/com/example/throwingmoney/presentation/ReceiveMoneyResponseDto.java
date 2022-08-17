package com.example.throwingmoney.presentation;

import lombok.Getter;

@Getter
public class ReceiveMoneyResponseDto {

    Integer money;

    public ReceiveMoneyResponseDto(Integer money) {
        this.money = money;
    }

}
