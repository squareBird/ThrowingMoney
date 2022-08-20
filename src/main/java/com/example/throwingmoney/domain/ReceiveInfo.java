package com.example.throwingmoney.domain;

import com.example.throwingmoney.presentation.ReceiveMoneyResponseDto;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;
import java.util.Random;

@Entity
@Getter
public class ReceiveInfo {

    public ReceiveInfo() {

    }

    public ReceiveInfo(String token, Long userId, Integer money) {

        this.token = token;
        this.userId = userId;
        this.money = money;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String token;
    private Long userId;
    private Integer money;


    public ReceiveInfo generateReceiveInfo(List<ReceiveInfo> receiveInfoList, ThrowMoney throwMoney, String token, Long userId) {

        // 받은 인원수랑 받은 금액
        int receiveCount = receiveInfoList.size();
        int receiveMoney = receiveInfoList.stream()
                .map(list -> list.getMoney())
                .reduce(0, Integer::sum);

        ReceiveInfo result = new ReceiveInfo(token, userId, generateRandomMoney(throwMoney.getTargetCount(), receiveCount, receiveMoney));

        return result;

    }

    /**
     * 랜덤으로 돈 받기
     * 1. 받을 수 있는 돈의 범위는 remainMoney보다 작아야 함
     * 2. 모두가 받을 수 있어야 하므로 잔액은 최소한 (전체 인원수 - 받아간 인원수) 보다 커야함, (targetCount - alreadyReceiveCount)
     * @return
     */
    int generateRandomMoney(int targetCount, int receiveCount, int receiveMoney) {

        int remainMoney = this.money - receiveMoney;

        if(receiveCount>=targetCount) {
            throw new ReceiveMoneyException("받을 수 있는 횟수가 초과되었습니다.");
        }

        if((targetCount-receiveCount)==1) {
            return money-receiveMoney;
        }

        Random random = new Random();
        return random.nextInt(remainMoney-1+1)+1;

    }

}
