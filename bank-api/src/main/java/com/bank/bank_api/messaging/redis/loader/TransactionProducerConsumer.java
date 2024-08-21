package com.bank.bank_api.messaging.redis.loader;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.bank.bank_api.builder.TransactionBuilder;
import com.bank.bank_api.constant.TransactionType;
import com.bank.bank_api.domain.Transaction;
import com.bank.bank_api.messaging.redis.producer.TransactionProducer;

@Configuration
@Profile("redis-profile")
public class TransactionProducerConsumer {

    @Bean
    CommandLineRunner sendMessage(TransactionProducer producer, @Value("${bank.redis.transaction.topic}")
            String topic){
        return args -> {
            producer.sendTo(topic,Transaction.builder()
                    .amount(137.0)
                    .type(TransactionType.DEPOSIT)
                    .notes("Summer price promo")
                    .build());
        };
    }

}
