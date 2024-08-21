package com.bank.bank_api.messaging.redis.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.bank.bank_api.domain.Transaction;

@Component
@Profile("redis-profile")
public class TransactionProducer {

    private static final Logger log = LoggerFactory.getLogger(TransactionProducer.class);

    @Autowired
    private RedisTemplate redisTemplate;

    public void sendTo(String topic, Transaction transaction) {
        log.info("Producer> ToDo sent");
        this.redisTemplate.convertAndSend(topic, transaction);
    }

}
