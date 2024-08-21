package com.bank.bank_api.messaging.redis.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.bank.bank_api.domain.Transaction;
import com.bank.bank_api.repository.TransactionRepository;

@Component
@Profile("redis-profile")
public class TransactionConsumer {
    private static final Logger log = LoggerFactory.getLogger(TransactionConsumer.class);

    @Autowired
    private TransactionRepository repository;

    public void handleMessage(Transaction transaction) {
        log.info("Consumer> " + transaction);
        log.info("Transaction created> " + this.repository.save(transaction));
    }
}
