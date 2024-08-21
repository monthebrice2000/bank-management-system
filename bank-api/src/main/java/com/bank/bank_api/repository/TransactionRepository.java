package com.bank.bank_api.repository;

import org.springframework.data.repository.CrudRepository;

import com.bank.bank_api.domain.Transaction;

public interface TransactionRepository extends CrudRepository<Transaction, String> {

    Iterable<Transaction> findByNotesContains(String string);
    public long countByCompleted(boolean completed);

}
