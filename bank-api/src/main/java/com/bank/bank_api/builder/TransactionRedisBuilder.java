package com.bank.bank_api.builder;

import com.bank.bank_api.constant.TransactionType;
import com.bank.bank_api.domain.Transaction;
import com.bank.bank_api.domain.TransactionRedis;

public class TransactionRedisBuilder {

    private static TransactionRedisBuilder instance = new TransactionRedisBuilder();

    private String id = null;
    private TransactionType type = null;
    private double amount = 0;
    private String notes = "";

    private TransactionRedisBuilder() {
    }

    public static TransactionRedisBuilder create() {
        return instance;
    }

    public TransactionRedisBuilder withNotes(String notes) {
        this.notes = notes;
        return instance;
    }

    public TransactionRedisBuilder withAmount(double amount) {
        this.amount = amount;
        return instance;
    }

    public TransactionRedisBuilder withType(TransactionType type) {
        this.type = type;
        return instance;
    }

    public TransactionRedisBuilder withId(String id) {
        this.id = id;
        return instance;
    }

    public TransactionRedisBuilder withTransaction(Transaction transaction) {
        if (transaction.getId() != null)
            this.id = transaction.getId();

        if (transaction.getType() != null)
            this.type = transaction.getType();

        this.amount = transaction.getAmount();

        if (transaction.getNotes() != null)
            this.notes = transaction.getNotes();
        return instance;
    }

    public TransactionRedis build() {
        TransactionRedis result = new TransactionRedis();
        if (id != null)
            result.setId(id);

        if (type != null)
            result.setType(type);

        result.setAmount(amount);

        if (notes != null || notes != "")
            result.setNotes(notes);

        return result;
    }

}
