package com.bank.bank_api.service;

import com.bank.bank_api.domain.Transaction;

public interface TransactionService {

    public Transaction findById(String id);

}
