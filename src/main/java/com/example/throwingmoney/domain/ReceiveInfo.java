package com.example.throwingmoney.domain;

import com.example.throwingmoney.presentation.ReceiveMoneyResponseDto;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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


    // Entity -> ResponseDto 변환
    public ReceiveMoneyResponseDto generateReceiveMoneyResponseDto() {
        return new ReceiveMoneyResponseDto(money);
    }

}
