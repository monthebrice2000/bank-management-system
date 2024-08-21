package com.bank.bank_api;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.bank.bank_api.constant.TransactionType;
import com.bank.bank_api.domain.Transaction;
import com.bank.bank_api.repository.TransactionRepository;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class TransactionDataJpaTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TransactionRepository transactionRepo;

    @Test
    public void transactionDataTest() throws Exception {

        Transaction transaction = Transaction.builder() //.id("2c945081916656e501916656f2f70000")
                .notes("best price promo")
                .amount(10.0)
                .type(TransactionType.DEPOSIT)
                .completed(true)
                .build();

        this.entityManager.persist(transaction);
        Iterable<Transaction> transactions = this.transactionRepo.findByNotesContains("best price promo");
        assertThat(transactions.iterator().next().getNotes()).isEqualTo("best price promo");
    }

}
