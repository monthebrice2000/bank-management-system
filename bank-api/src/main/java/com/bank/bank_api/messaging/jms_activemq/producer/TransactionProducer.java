package com.bank.bank_api.messaging.jms_activemq.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.jms.core.JmsTemplate;

import com.bank.bank_api.domain.Transaction;

@Component
@Profile("activemq-profile")
public class TransactionProducer {

    private static final Logger log = LoggerFactory.getLogger(TransactionProducer.class);

    @Autowired
    private JmsTemplate jmsTemplate;

    public void sendTo(String destination, Transaction transaction) {
        this.jmsTemplate.convertAndSend(destination, transaction);
        log.info("Producer> Message Sent");
    }

}
