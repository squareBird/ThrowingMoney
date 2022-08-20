package com.example.throwingmoney.presentation;

import com.example.throwingmoney.domain.ThrowMoney;
import lombok.Builder;

@Builder
public class ThrowMoneyResponseDto {

    private String token;

    public static ThrowMoneyResponseDto of(ThrowMoney throwMoney) {
        return ThrowMoneyResponseDto.builder()
                .token(throwMoney.getToken())
                .build();
    }

}