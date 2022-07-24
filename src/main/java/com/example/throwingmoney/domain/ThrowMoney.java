package com.example.throwingmoney.domain;

import com.fasterxml.jackson.annotation.JsonTypeId;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.Random;

@Entity
public class ThrowMoney {

    private final int TOKEN_SIZE = 3;
    private final int ASCII_0 = 48;
    private final int ASCII_Z = 122;

    @Id
    private String token;
    private Long createTime;
    private String userName;
    private String roomId;
    private int money;
    private int remainMoney;
    private int targetCount;
    private int alreadyReceiveCount;

    public ThrowMoney() {

    }

    public ThrowMoney(String userName, String roomId, int money, int targetCount) {
        this.token = generateToken();
        this.createTime = System.currentTimeMillis();
        this.userName = userName;
        this.roomId = roomId;
        this.money = money;
        this.remainMoney = money;
        this.targetCount = targetCount;
        this.alreadyReceiveCount = 0;
    }

    /**
     * Token 생성
     * @return token
     */
    String generateToken() {

        int leftLimit = ASCII_0; // numeral '0'
        int rightLimit = ASCII_Z; // letter 'z'
        int targetStringLength = TOKEN_SIZE;

        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;

    }

    /**
     * 랜덤으로 돈 받기
     * 1. 받을 수 있는 돈의 범위는 remainMoney보다 작아야 함
     * 2. 모두가 받을 수 있어야 하므로 잔액은 최소한 (전체 인원수 - 받아간 인원수) 보다 커야함, (targetCount - alreadyReceiveCount)
     * @return
     */
    int receiveRandomMoney() {

        Random random = new Random();

        return 0;
    }

    /**
     * 토큰 생성 후 경과시간 확인
     * @return
     */
    boolean isValidToken() {

        Long checkTime = System.currentTimeMillis();
        Long elapseTime = (createTime - checkTime)*60*10;

        if(elapseTime<10) {
            return true;
        }
        return false;

    }


}
