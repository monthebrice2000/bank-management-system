package com.bank.bank_api;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

import java.util.Optional;

import com.bank.bank_api.constant.TransactionType;
import com.bank.bank_api.domain.Transaction;
import com.bank.bank_api.repository.TransactionRepository;
import com.bank.bank_api.resource.TransactionController;

@RunWith(SpringRunner.class)
@WebMvcTest(TransactionController.class)
public class TransactionWebMvcTest {
    @MockBean
    private TransactionRepository transactionRepo;

    // @Autowired
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before()
    public void setup() {
        // Init MockMvc Object and build
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void transactionControllerTest() throws Exception {

        // Create static data
        Transaction transaction = Transaction.builder().id("2c945081916656e501916656f2f70001")
                .notes("best price promo")
                .amount(10.0)
                .type(TransactionType.DEPOSIT)
                .completed(true)
                .build();

        given(this.transactionRepo.findById("2c945081916656e501916656f2f70001"))
                .willReturn(Optional.of(transaction));

        this.mvc.perform(get("/api/transactions/2c945081916656e501916656f2f70001").accept(MediaType.APPLICATION_JSON)
                .with(csrf()).with(user("matt@example.com").password("secret").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        "{\"id\":\"2c945081916656e501916656f2f70001\",\"type\":\"DEPOSIT\",\"amount\":10.0,\"notes\":\"best price promo\",\"completed\":true,\"created\":null,\"modified\":null}"));
    }

}
