package com.bank.bank_api.messaging.jms_activemq.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ErrorHandler;

public class TransactionErrorHandler implements ErrorHandler {

    private static Logger log = LoggerFactory.getLogger(TransactionErrorHandler.class);

    @Override
    public void handleError(Throwable t) {
        log.warn("Transaction error...");
        log.error(t.getCause().getMessage());
    }

}
