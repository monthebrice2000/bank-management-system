package com.bank.bank_api.messaging.jms_activemq.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.bank.bank_api.domain.Transaction;

public class TransactionValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(Transaction.class);

    }

    @Override
    public void validate(Object target, Errors errors) {
        Transaction transaction = (Transaction) target;
        if (transaction == null) {
            errors.reject(null, "Transaction cannot be null");
        } else {
            if (transaction.getNotes() == null || transaction.getNotes().isEmpty())
                errors.rejectValue("Notes", null, "Notes cannot be null or empty");
        }
    }

}
