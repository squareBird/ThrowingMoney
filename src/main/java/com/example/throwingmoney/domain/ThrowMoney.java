package com.example.throwingmoney.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.Random;

/**
 * 뿌리기 생성 요청시 ThrowMoney Entity 생성
 * receive시 남은 금액 변경, 현재 받은 인원 변경 및 해당 정보로 ReceiveInfo 생성
 * receiveCount == targetCount이면 더이상 받을 수 없다고 해야함
 */

@Entity
@Getter
public class ThrowMoney {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * userId : 뿌린 사람 ID
     * roomId : 뿌린 방 ID
     * token : 뿌리기에 대한 고유값
     * createTime : 토큰 생성 시간
     * money : 뿌린 금액
     * targetCount : 뿌리기 대상 인원
     */
    private long userId;
    private String roomId;
    private String token;
    private Long createTime;
    private Integer money;
    private Integer targetCount;

    public ThrowMoney() {

    }

    public ThrowMoney(long userId, String roomId, Integer money, Integer targetCount) {
        this.userId = userId;
        this.roomId = roomId;
        this.token = generateToken();
        this.createTime = System.currentTimeMillis();
        this.money = money;
        this.targetCount = targetCount;
    }

    public void validate(Long receiveUserId) {

        validateUserId(receiveUserId);
        validateToken();

    }

    // 자기 자신의 것을 받으려고 하는지 확인
    private void validateUserId(Long receiveUserId) {
        if(this.userId==receiveUserId) {
            throw new SelfReceiveException("자신이 뿌린 것은 받을 수 없습니다.");
        }
    }

    // 토큰 유효성 확인
    private void validateToken() {
        Long checkTime = System.currentTimeMillis();
        Long elapseTime = (createTime - checkTime)*60*10;

        if(elapseTime>=10) {
            throw new InvalidTokenException("유효하지 않은 토큰입니다.");
        }
    }


    /**
     * Token 생성
     * @return token
     */
    private String generateToken() {

        int leftLimit = ConstantList.ASCII_0; // numeral '0'
        int rightLimit = ConstantList.ASCII_Z; // letter 'z'
        int targetStringLength = ConstantList.TOKEN_SIZE;

        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }


}
