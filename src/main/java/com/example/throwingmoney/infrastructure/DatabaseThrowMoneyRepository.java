package com.example.throwingmoney.infrastructure;

import com.example.throwingmoney.domain.ThrowMoneyRepository;
import org.springframework.context.annotation.Profile;

@Profile({"dev","prod"})
public class DatabaseThrowMoneyRepository implements ThrowMoneyRepository {
}
