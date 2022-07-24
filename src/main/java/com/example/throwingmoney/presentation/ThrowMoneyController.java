package com.example.throwingmoney.presentation;

import com.example.throwingmoney.application.ThrowMoneyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ThrowMoneyController {

    private final ThrowMoneyService throwMoneyService;

    @Autowired
    ThrowMoneyController(ThrowMoneyService throwMoneyService) {
        this.throwMoneyService = throwMoneyService;
    }

}
