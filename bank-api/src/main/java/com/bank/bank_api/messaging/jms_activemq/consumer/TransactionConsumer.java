package com.bank.bank_api.messaging.jms_activemq.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.bank.bank_api.domain.Transaction;
import com.bank.bank_api.repository.TransactionRepository;

import jakarta.validation.Valid;

@Component
@Profile("activemq-profile")
public class TransactionConsumer {

    private Logger log = LoggerFactory.getLogger(TransactionConsumer.class);

    @Autowired
    private TransactionRepository repository;

    @JmsListener(destination = "${bank.jms.transaction.destination}", containerFactory = "jmsFactory")
    public void processTransaction(@Valid Transaction transaction) {
        log.info("Consumer> " + transaction);
        log.info("Transaction created> " + this.repository.save(transaction));
    }

}
