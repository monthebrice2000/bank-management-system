package com.bank.bank_api.messaging.websocket.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.bank.bank_api.domain.Transaction;
import com.bank.bank_api.messaging.websocket.config.WebsocketProperties;

@Profile("ws-profile")
@Component
@RepositoryEventHandler(Transaction.class)
public class TransactionEventHandler {

    private Logger log = LoggerFactory.getLogger(TransactionEventHandler.class);

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private WebsocketProperties wsProperties;

    @HandleAfterCreate
    public void handleToDoSave(Transaction transaction) {
        this.simpMessagingTemplate.convertAndSend(this.wsProperties.getBroker() + "/new", transaction);
        log.info(">> Sending Message to WS: ws://transactions/new - " + transaction);
    }

}
