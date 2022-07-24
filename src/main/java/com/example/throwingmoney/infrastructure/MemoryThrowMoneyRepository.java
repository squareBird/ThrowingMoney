package com.example.throwingmoney.infrastructure;

import com.example.throwingmoney.domain.ThrowMoneyRepository;
import org.springframework.context.annotation.Profile;

@Profile("test")
public class MemoryThrowMoneyRepository implements ThrowMoneyRepository {
}
