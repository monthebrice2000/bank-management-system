package com.bank.bank_api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.Optional;

import com.bank.bank_api.domain.Transaction;
import com.bank.bank_api.repository.TransactionRepository;



@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionMockBeanTests {

    @MockBean
    private TransactionRepository transactionRepo;

    @Test
    public void testFindById() {
        // Create static data
        Transaction transaction = new Transaction();
        transaction.setId("2c945081916656e501916656f2f70000");
        transaction.setNotes("best price promo");
        transaction.setAmount(10.0);

        // Mock the repository's findById method to return the static data
        given(transactionRepo.findById("2c945081916656e501916656f2f70000"))
                .willReturn(Optional.of(transaction));

        // Call the service method
        Optional<Transaction> foundTransaction = transactionRepo.findById("2c945081916656e501916656f2f70000");

        // Verify the result
        assertThat(foundTransaction).isPresent();
        assertThat(foundTransaction.get().getNotes()).isEqualTo("best price promo");
        assertThat(foundTransaction.get().getAmount()).isEqualTo(10.0);
    }

}
