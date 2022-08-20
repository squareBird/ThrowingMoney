package com.example.throwingmoney.domain;

import com.example.throwingmoney.presentation.ReceiveMoneyResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DomainService {

    private final ThrowMoneyRepository throwMoneyRepository;

    @Autowired
    DomainService(ThrowMoneyRepository throwMoneyRepository) {
        this.throwMoneyRepository = throwMoneyRepository;
    }

    public ReceiveMoneyResponseDto receive(ThrowMoney throwMoney, String token, Long userId, String roomId) {

        // 뿌리기 정보와 해당 뿌리기를 얼마나 받아갔는지 조회
        List<ReceiveInfo> receiveInfoList = throwMoneyRepository.findReceiveInfoByToken(token);

        // userId로 조회
        long count = receiveInfoList.stream().map(l -> l.getUserId()).filter(l -> userId.equals(l)).count();
        if(count!=0) {
            throw new AlreadyReceiveException("이미 받은 뿌리기입니다.");
        }

        // ReceiveInfo 정보 생성
        ReceiveInfo receiveInfo = new ReceiveInfo();
        receiveInfo.generateReceiveInfo(receiveInfoList, throwMoney, token, userId);
        throwMoneyRepository.saveReceiveInfo(receiveInfo);

        // ResponseDto 생성
        ReceiveMoneyResponseDto receiveMoneyResponseDto = ReceiveMoneyResponseDto.of(receiveInfo);

        return receiveMoneyResponseDto;

    }


}
