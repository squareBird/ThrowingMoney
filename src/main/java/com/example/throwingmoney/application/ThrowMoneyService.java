package com.example.throwingmoney.application;

import com.example.throwingmoney.domain.*;
import com.example.throwingmoney.presentation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThrowMoneyService {

    private final ThrowMoneyRepository throwMoneyRepository;

    private final DomainService domainService;

    @Autowired
    ThrowMoneyService(ThrowMoneyRepository throwMoneyRepository, DomainService domainService) {
        this.throwMoneyRepository = throwMoneyRepository;
        this.domainService = domainService;
    }

    public ThrowMoneyResponseDto generateThrowMoney(ThrowMoneyRequestDto throwMoneyRequestDto, Long userId, String roomId) {

        // Entity 생성 및 저장
        ThrowMoney throwMoney = throwMoneyRequestDto.generateThrowMoney(userId, roomId);
        throwMoneyRepository.saveThrowMoney(throwMoney);

        // ResponseDTO 생성
        ThrowMoneyResponseDto throwMoneyResponseDto = ThrowMoneyResponseDto.of(throwMoney);

        return throwMoneyResponseDto;

    }

    // 조건
    // 1. 자신이 뿌리기한 건은 자신이 받을 수 없음
    // 2. 한번만 받을 수 있음
    // 3. 동일한 대화방에 속한 사용자만 받을 수 있음
    public ReceiveMoneyResponseDto receiveMoney(String token, Long userId, String roomId) {

        ThrowMoney throwMoney = throwMoneyRepository.getThrowMoneyByTokenAndRoomId(token, roomId);

        // 1. 정보 있는지 없는지 확인
        // validate를 static으로 만들어서 이 mull 체크를 그 안에 넣어도 되나?
        if(throwMoney == null) {
            throw new CannotFindThrowMoneyException("해당 뿌리기를 찾을 수 없습니다.");
        }
        throwMoney.validate(userId);

        // 받기 정보 생성 및 결과 생성
        ReceiveMoneyResponseDto receiveMoneyResponseDto= domainService.receive(throwMoney, token, userId, roomId);

        return receiveMoneyResponseDto;
    }

    public LookupMoneyResponseDto lookupMoney(String token, Long userId, String roomId) {

        // 뿌리기 정보 조회
        ThrowMoney throwMoney = throwMoneyRepository.getThrowMoneyByTokenAndUserIdAndRoomId(token, userId, roomId);
        if(throwMoney == null) {
            throw new CannotFindThrowMoneyException("해당 뿌리기를 찾을 수 없습니다.");
        }

        List<ReceiveInfo> receiveInfoList = throwMoneyRepository.findReceiveInfoByToken(token);

        LookupMoneyResponseDto lookupMoneyResponseDto = new LookupMoneyResponseDto(throwMoney, receiveInfoList);

        return lookupMoneyResponseDto;

    }

}
