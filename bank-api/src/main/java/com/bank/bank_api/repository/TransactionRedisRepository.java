package com.bank.bank_api.repository;

import org.springframework.data.repository.CrudRepository;

import com.bank.bank_api.domain.Transaction;
import com.bank.bank_api.domain.TransactionRedis;

public interface TransactionRedisRepository extends CrudRepository<TransactionRedis, String> {

}
