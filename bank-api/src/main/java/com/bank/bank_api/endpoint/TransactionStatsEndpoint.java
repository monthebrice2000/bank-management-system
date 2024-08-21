package com.bank.bank_api.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.DeleteOperation;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.stereotype.Component;

import com.bank.bank_api.domain.Transaction;
import com.bank.bank_api.repository.TransactionRepository;

import lombok.AllArgsConstructor;
import lombok.Data;

@Component
@Endpoint(id = "transaction-stats")
public class TransactionStatsEndpoint {

    @Autowired
    private TransactionRepository repo;

    @ReadOperation
    public Stats stats() {
        return new Stats(this.repo.count(), this.repo.countByCompleted(true));
    }

    @ReadOperation
    public Transaction getToDo(@Selector String id) {
        return this.repo.findById(id).orElse(null);
    }

    @WriteOperation
    public Operation completeToDo(@Selector String id) {
        Transaction toDo = this.repo.findById(id).orElse(null);
        if (null != toDo) {
            toDo.setCompleted(true);
            this.repo.save(toDo);
            return new Operation("COMPLETED", true);
        }

        return new Operation("COMPLETED", false);
    }

    @DeleteOperation
    public Operation removeToDo(@Selector String id) {
        try {
            this.repo.deleteById(id);
            return new Operation("DELETED", true);
        } catch (Exception ex) {
            return new Operation("DELETED", false);
        }
    }

    @AllArgsConstructor
    @Data
    public class Stats {
        private long count;
        private long completed;
    }

    @AllArgsConstructor
    @Data
    public class Operation {
        private String name;
        private boolean successful;
    }

}
