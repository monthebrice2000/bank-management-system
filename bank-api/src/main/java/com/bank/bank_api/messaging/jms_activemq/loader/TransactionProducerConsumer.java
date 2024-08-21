package com.bank.bank_api.messaging.jms_activemq.loader;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;

import com.bank.bank_api.builder.TransactionBuilder;
import com.bank.bank_api.constant.TransactionType;
import com.bank.bank_api.domain.Transaction;
import com.bank.bank_api.messaging.jms_activemq.producer.TransactionProducer;

@Configuration
@Profile("activemq-profile")
public class TransactionProducerConsumer {

    @Bean
    @Order(5)
    public CommandLineRunner sendToDos(@Value("${bank.jms.transaction.destination}") String destination,
            TransactionProducer producer) {
        return args -> {
            producer.sendTo(destination, TransactionBuilder.create()
                    .withAmount(137)
                    .withType(TransactionType.DEPOSIT)
                    .withNotes("jo price promo")
                    .build());
        };
    }

}
