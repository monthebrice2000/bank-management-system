package com.bank.bank_api.builder;

import com.bank.bank_api.constant.TransactionType;
import com.bank.bank_api.domain.Transaction;

public class TransactionBuilder {

    private static TransactionBuilder instance = new TransactionBuilder();

    private String id = null;
    private TransactionType type = null;
    private double amount = 0;
    private String notes = "";
    private boolean completed = false;

    private TransactionBuilder() {
    }

    public static TransactionBuilder create() {
        return instance;
    }

    public TransactionBuilder withNotes(String notes) {
        this.notes = notes;
        return instance;
    }

    public TransactionBuilder withAmount(double amount) {
        this.amount = amount;
        return instance;
    }

    public TransactionBuilder withType(TransactionType type) {
        this.type = type;
        return instance;
    }

    public TransactionBuilder withId(String id) {
        this.id = id;
        return instance;
    }

    public TransactionBuilder withCompleted(boolean completed) {
        this.completed = completed;
        return instance;
    }

    public Transaction build() {
        Transaction result = new Transaction();
        if (id != null)
            result.setId(id);

        if (type != null)
            result.setType(type);

        result.setAmount(amount);

        if (notes != null || notes != "")
            result.setNotes(notes); 
        
        result.setCompleted(completed);

        return result;
    }

}
