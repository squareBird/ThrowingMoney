//package com.example.throwingmoney.infrastructure;
//
//import com.example.throwingmoney.domain.ReceiveInfo;
//import com.example.throwingmoney.domain.ThrowMoney;
//import com.example.throwingmoney.domain.ThrowMoneyRepository;
//import org.springframework.context.annotation.Profile;
//import org.springframework.stereotype.Repository;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Profile("test")
//@Repository
//public class MemoryThrowMoneyRepository implements ThrowMoneyRepository {
//
//    private final Map<String, ThrowMoney> store = new HashMap<>();
//
//    @Override
//    public void saveThrowMoney(ThrowMoney throwMoney) {
//        store.put(throwMoney.getToken(),throwMoney);
//    }
//
//    @Override
//    public void saveReceiveInfo(ReceiveInfo receiveInfo) {
//
//    }
//
//    @Override
//    public ThrowMoney findByToken(String token) {
//        return store.get(token);
//    }
//
//}
