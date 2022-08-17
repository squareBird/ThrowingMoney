package com.example.throwingmoney.domain;

import java.util.List;

public interface ThrowMoneyRepository {

    void saveThrowMoney(ThrowMoney throwMoney);

    void saveReceiveInfo(ReceiveInfo receiveInfo);

    List<ThrowMoney> findThrowMoneyByTokenAndRoomId(String token, String roomId);

    List<ReceiveInfo> findReceiveInfoByToken(String token);

    List<ThrowMoney> findThrowMoneyByTokenAndUserIdAndRoomId(String token, Long userId,String roomId);

}
