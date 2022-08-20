package com.example.throwingmoney.presentation;


import com.example.throwingmoney.domain.ReceiveInfo;
import lombok.Builder;

@Builder
public class ReceiveMoneyResponseDto {

    Integer money;

    public static ReceiveMoneyResponseDto of(ReceiveInfo receiveInfo) {
        return ReceiveMoneyResponseDto.builder()
                .money(receiveInfo.getMoney())
                .build();
    }

}
