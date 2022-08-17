package com.example.throwingmoney.presentation;

import com.example.throwingmoney.application.ThrowMoneyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ThrowMoneyController {

    private final ThrowMoneyService throwMoneyService;

    @Autowired
    ThrowMoneyController(ThrowMoneyService throwMoneyService) {
        this.throwMoneyService = throwMoneyService;
    }

    /**
     * Header
     * - X-USER-ID 사용자 식별 값
     * - x-ROOM-ID 대화방 ID
     * Body
     * - 뿌릴 금액
     * - 뿌릴 인원
     */
    @RequestMapping(value = "/throwmoney", method = RequestMethod.POST)
    ResponseEntity throwMoney(@RequestBody ThrowMoneyRequestDto throwMoneyRequestDto,
                              @RequestHeader(value = "X-USER-ID") Long userId,
                              @RequestHeader(value = "X-ROOM-ID") String roomId) {

        // 토큰생성
        ThrowMoneyResponseDto throwMoneyResponseDto = throwMoneyService.generateThrowMoney(throwMoneyRequestDto, userId, roomId);

        return new ResponseEntity(throwMoneyResponseDto, HttpStatus.OK);

    }

    @RequestMapping(value = "/receive/{token}", method=RequestMethod.GET)
    ResponseEntity receiveMoney(@PathVariable String token,
                              @RequestHeader(value = "X-USER-ID") Long userId,
                              @RequestHeader(value = "X-ROOM-ID") String roomId) {

        ReceiveMoneyResponseDto receiveMoneyResponseDto = throwMoneyService.receiveMoney(token, userId, roomId);


        return new ResponseEntity(receiveMoneyResponseDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/lookup/{token}", method=RequestMethod.GET)
    ResponseEntity lookupMoney(@PathVariable String token,
                             @RequestHeader(value = "X-USER-ID") Long userId,
                             @RequestHeader(value = "X-ROOM-ID") String roomId) {

        System.out.println(token);

        LookupMoneyResponseDto lookupMoneyResponseDto = throwMoneyService.lookupMoney(token, userId, roomId);

        return new ResponseEntity(lookupMoneyResponseDto, HttpStatus.OK);
    }

}
