package com.bank.bank_api.messaging.rabbitmq.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.bank.bank_api.domain.Transaction;

@Component
@Profile("rabbitmq-profile")
public class TransactionProducer {

    private static final Logger log = LoggerFactory.getLogger(TransactionProducer.class);

    @Autowired
    private RabbitTemplate template;

    public void sendTo(String queue, Transaction transaction) {
        this.template.convertAndSend(queue, transaction);
        log.info("Producer> Message Sent");
    }
}
