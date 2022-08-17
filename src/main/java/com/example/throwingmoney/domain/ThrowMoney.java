package com.example.throwingmoney.domain;

import com.example.throwingmoney.presentation.ThrowMoneyResponseDto;
import lombok.Getter;

import javax.persistence.*;
import java.util.List;
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

    /**
     * Token 생성
     * @return token
     */
    String generateToken() {

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

    public ReceiveInfo generateReceiveInfo(List<ReceiveInfo> receiveInfoList, String token, Long userId) {

        // 받은 인원수랑 받은 금액
        int receiveCount = receiveInfoList.size();
        int receiveMoney = receiveInfoList.stream()
                .map(list -> list.getMoney())
                .reduce(0, Integer::sum);

        ReceiveInfo result = new ReceiveInfo(token, userId, generateRandomMoney(receiveCount, receiveMoney));

        return result;

    }

    /**
     * 랜덤으로 돈 받기
     * 1. 받을 수 있는 돈의 범위는 remainMoney보다 작아야 함
     * 2. 모두가 받을 수 있어야 하므로 잔액은 최소한 (전체 인원수 - 받아간 인원수) 보다 커야함, (targetCount - alreadyReceiveCount)
     * @return
     */
    int generateRandomMoney(int receiveCount, int receiveMoney) {

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

    /**
     * 토큰 생성 후 경과시간 확인
     * @return
     */
    public boolean isValidToken() {

        Long checkTime = System.currentTimeMillis();
        Long elapseTime = (createTime - checkTime)*60*10;

        if(elapseTime<10) {
            return true;
        }
        return false;

    }

    // Entity -> ResponseDto 변환
    public ThrowMoneyResponseDto generateThrowMoneyResponseDto() {
        return new ThrowMoneyResponseDto(this.token);
    }

}
