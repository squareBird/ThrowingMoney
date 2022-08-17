package com.example.throwingmoney.presentation;

import com.example.throwingmoney.domain.ReceiveInfo;
import com.example.throwingmoney.domain.ThrowMoney;
import lombok.Getter;

import java.util.List;

@Getter
public class LookupMoneyResponseDto {

    private Long createTime;
    private Integer money;
    private Integer receiveMoney;
    private List<ReceiveInfo> receiveInfoList;

    public LookupMoneyResponseDto(ThrowMoney throwMoney, List<ReceiveInfo> receiveInfoList) {
        this.createTime = throwMoney.getCreateTime();
        this.money = throwMoney.getMoney();
        this.receiveMoney = receiveInfoList.stream()
                .map(list -> list.getMoney())
                .reduce(0, Integer::sum);
        this.receiveInfoList = receiveInfoList;
    }

}
