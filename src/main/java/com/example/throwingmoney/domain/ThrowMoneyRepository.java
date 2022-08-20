package com.example.throwingmoney.domain;

import java.util.List;

public interface ThrowMoneyRepository {

    void saveThrowMoney(ThrowMoney throwMoney);

    void saveReceiveInfo(ReceiveInfo receiveInfo);

    ThrowMoney getThrowMoneyByTokenAndRoomId(String token, String roomId);

    List<ReceiveInfo> findReceiveInfoByToken(String token);

    ThrowMoney getThrowMoneyByTokenAndUserIdAndRoomId(String token, Long userId,String roomId);

}
