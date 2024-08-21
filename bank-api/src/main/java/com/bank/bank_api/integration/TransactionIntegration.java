package com.bank.bank_api.integration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.MessageChannels;

import com.bank.bank_api.domain.Transaction;

@EnableIntegration
@Configuration
public class TransactionIntegration {

    @Bean
    public DirectChannel input() {
        return MessageChannels.direct().getObject();
    }

    @Bean
    public IntegrationFlow simpleFlow() {
        return IntegrationFlow
                .from(input())
                .filter(Transaction.class, Transaction::isCompleted)
                .transform(Transaction.class,
                        transaction -> transaction.getNotes().toUpperCase())
                .handle(System.out::println)
                .get();
    }
}
