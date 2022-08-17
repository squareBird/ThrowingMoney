package com.example.throwingmoney.presentation;

import com.example.throwingmoney.domain.ThrowMoney;
import lombok.Getter;

@Getter
public class ThrowMoneyRequestDto {

    private Integer money;
    private Integer targetCount;

    public ThrowMoney generateThrowMoney(long userId, String roomId) {
        return new ThrowMoney(userId, roomId, money, targetCount);
    }

}