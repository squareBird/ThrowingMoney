package com.example.throwingmoney.presentation;

import lombok.Getter;

@Getter
public class ThrowMoneyResponseDto {

    private String token;

    public ThrowMoneyResponseDto(String token) {
        this.token = token;
    }

}