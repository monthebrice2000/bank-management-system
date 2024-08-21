package com.bank.bank_api.messaging.rabbitmq.loader;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.bank.bank_api.builder.TransactionBuilder;
import com.bank.bank_api.constant.TransactionType;
import com.bank.bank_api.messaging.rabbitmq.producer.TransactionProducer;

// @EnableScheduling
@Configuration
@Profile("rabbitmq-profile")
public class TransactionProducerConsumer {

    // @Value("${bank.amqp.transaction.queue}")
    // String destination;

    // @Autowired
    // private TransactionProducer producer;

    // private SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    // @Scheduled(fixedRate = 500L)
    // public CommandLineRunner sendTransactions() {
    //     return args -> {
    //         producer.sendTo(destination, TransactionBuilder.create()
    //                 .withAmount(137)
    //                 .withType(TransactionType.DEPOSIT)
    //                 .withNotes("Summer price promo" + dateFormat.format(new Date()) )
    //                 .build());
    //     };
    // }

    @Bean
    public CommandLineRunner sendTransactions(@Value("${bank.amqp.transaction.queue}") String destination,
            TransactionProducer producer) {
        return args -> {
            producer.sendTo(destination, TransactionBuilder.create()
                    .withAmount(137)
                    .withType(TransactionType.DEPOSIT)
                    .withNotes("Summer price promo")
                    .build());
        };
    }
}
