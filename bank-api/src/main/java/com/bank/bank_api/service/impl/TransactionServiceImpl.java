package com.bank.bank_api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.bank_api.domain.Transaction;
import com.bank.bank_api.repository.TransactionRepository;
import com.bank.bank_api.service.TransactionService;

@Service
public class TransactionServiceImpl implements TransactionService{

    @Autowired
    private TransactionRepository transactionRepo;

    @Override
    public Transaction findById(String id) {
        return transactionRepo.findById(id).get();
    }

}
