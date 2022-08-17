package com.example.throwingmoney.application;

import com.example.throwingmoney.domain.*;
import com.example.throwingmoney.presentation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThrowMoneyService {

    ThrowMoneyRepository throwMoneyRepository;

    @Autowired
    ThrowMoneyService(ThrowMoneyRepository throwMoneyRepository) {
        this.throwMoneyRepository = throwMoneyRepository;
    }

    public ThrowMoneyResponseDto generateThrowMoney(ThrowMoneyRequestDto throwMoneyRequestDto, Long userId, String roomId) {

        // Entity 생성 및 저장
        ThrowMoney throwMoney = throwMoneyRequestDto.generateThrowMoney(userId, roomId);
        throwMoneyRepository.saveThrowMoney(throwMoney);

        // ResponseDTO 생성
        // 이거 Entity가 만들어주는게 맞는지?
        ThrowMoneyResponseDto throwMoneyResponseDto = throwMoney.generateThrowMoneyResponseDto();

        return throwMoneyResponseDto;

    }

    // 조건
    // 1. 자신이 뿌리기한 건은 자신이 받을 수 없음
    // 2. 한번만 받을 수 있음
    // 3. 동일한 대화방에 속한 사용자만 받을 수 있음
    public ReceiveMoneyResponseDto receiveMoney(String token, Long userId, String roomId) {

        // 뿌리기 정보와 해당 뿌리기를 얼마나 받아갔는지 조회
        List<ThrowMoney> throwMoneyList = throwMoneyRepository.findThrowMoneyByTokenAndRoomId(token, roomId);
        if(throwMoneyList.size()==0) { // 해당 token과 roomId로 뿌리기를 찾을 수 없음
            throw new CannotFindThrowMoneyException("해당 뿌리기를 찾을 수 없습니다.");
        }
        List<ReceiveInfo> receiveInfoList = throwMoneyRepository.findReceiveInfoByToken(token);

        ThrowMoney throwMoney = throwMoneyList.get(0);

        // 자신이 뿌린건지 검증
        // 나중에 Entity 쪽으로 넣을까...
        if(throwMoney.getUserId()==userId) {
            throw new SelfReceiveException("자신이 뿌린 것은 받을 수 없습니다.");
        }

        // userId로 조회
        long count = receiveInfoList.stream().map(l -> l.getUserId()).filter(l -> userId.equals(l)).count();
        if(count!=0) {
            throw new AlreadyReceiveException("이미 받은 뿌리기입니다.");
        }

        // 토큰 유효성 검증
        if(!throwMoney.isValidToken())
            throw new InvalidTokenException("유효하지 않은 토큰입니다.");

        // ReceiveInfo 정보 생성
        ReceiveInfo receiveInfo = throwMoney.generateReceiveInfo(receiveInfoList, token, userId);
        throwMoneyRepository.saveReceiveInfo(receiveInfo);

        // ResponseDto 생성
        // 이거 Entity가 만들어주는게 맞는지?
        ReceiveMoneyResponseDto receiveMoneyResponseDto = receiveInfo.generateReceiveMoneyResponseDto();

        return receiveMoneyResponseDto;
    }

    public LookupMoneyResponseDto lookupMoney(String token, Long userId, String roomId) {

        // 뿌리기 정보 조회
        List<ThrowMoney> throwMoneyList = throwMoneyRepository.findThrowMoneyByTokenAndUserIdAndRoomId(token, userId, roomId);
        if(throwMoneyList.size()==0) {
            throw new CannotFindThrowMoneyException("해당 뿌리기를 찾을 수 없습니다.");
        }

        ThrowMoney throwMoney = throwMoneyList.get(0);

        List<ReceiveInfo> receiveInfoList = throwMoneyRepository.findReceiveInfoByToken(token);

        LookupMoneyResponseDto lookupMoneyResponseDto = new LookupMoneyResponseDto(throwMoney, receiveInfoList);

        return lookupMoneyResponseDto;

    }

}
