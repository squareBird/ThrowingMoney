package com.example.throwingmoney.infrastructure;

import com.example.throwingmoney.domain.ReceiveInfo;
import com.example.throwingmoney.domain.ThrowMoney;
import com.example.throwingmoney.domain.ThrowMoneyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import java.util.List;

@Profile({"dev","prod"})
@Repository
@Transactional
public class DatabaseThrowMoneyRepository implements ThrowMoneyRepository {

    private final EntityManager em;

    @Autowired
    public DatabaseThrowMoneyRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public void saveThrowMoney(ThrowMoney throwMoney) {
        em.persist(throwMoney);
    }

    @Override
    public void saveReceiveInfo(ReceiveInfo receiveInfo) {
        em.persist(receiveInfo);
    }

    @Override
    public ThrowMoney getThrowMoneyByTokenAndRoomId(String token, String roomId) {

        ThrowMoney result = em
                .createQuery("select t from ThrowMoney t where t.token = :token and t.roomId = :roomId", ThrowMoney.class)
                .setParameter("token", token)
                .setParameter("roomId", roomId)
                .getResultList()
                .get(0);

        return result;

    }

    @Override
    public List<ReceiveInfo> findReceiveInfoByToken(String token) {

        List<ReceiveInfo> result = em
                .createQuery("select r from ReceiveInfo r where r.token = :token", ReceiveInfo.class)
                .setParameter("token",token)
                .getResultList();

        return result;

    }

    @Override
    public ThrowMoney getThrowMoneyByTokenAndUserIdAndRoomId(String token, Long userId, String roomId) {

        ThrowMoney result = em
                .createQuery("select t from ThrowMoney t where t.token = :token and t.userId = :userId and t.roomId = :roomId", ThrowMoney.class)
                .setParameter("token", token)
                .setParameter("roomId", roomId)
                .setParameter("userId", userId)
                .getResultList()
                .get(0);

        return result;

    }

}
