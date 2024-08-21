package com.bank.bank_api.integration.loader;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

import com.bank.bank_api.constant.TransactionType;
import com.bank.bank_api.domain.Transaction;

@Configuration
public class TransactionIntegrationFlow {

    @Bean
    @Order(6)
    public ApplicationRunner runner(MessageChannel input) {
        return args -> {
            input.send(
                    MessageBuilder
                            .withPayload(Transaction.builder()
                                    .amount(137.0)
                                    .type(TransactionType.DEPOSIT)
                                    .notes("Summer price promo")
                                    .build())
                            .build());
        };
    }
}
