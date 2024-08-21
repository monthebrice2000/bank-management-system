package com.bank.bank_api.messaging.rabbitmq.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.bank.bank_api.domain.Transaction;
import com.bank.bank_api.repository.TransactionRepository;

import jakarta.validation.Valid;

@Component
@Profile("rabbitmq-profile")
public class TransactionConsumer {

    private Logger log = LoggerFactory.getLogger(TransactionConsumer.class);

    @Autowired
    private TransactionRepository repository;

    @RabbitListener(queues = "${bank.amqp.transaction.queue}")
    public void processToDo(@Valid Transaction transaction) {
        log.info("Consumer> " + transaction);
        log.info("Transaction created> " + this.repository.save(transaction));
    }
}
