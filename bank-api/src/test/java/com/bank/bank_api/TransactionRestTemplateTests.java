package com.bank.bank_api;

import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.bank.bank_api.constant.TransactionType;
import com.bank.bank_api.domain.Transaction;
import com.bank.bank_api.rest_client.TransactionRestClient;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
// @ExtendWith(MockitoExtension.class)
public class TransactionRestTemplateTests {

    @MockBean
    private RestTemplate restTemplate;

    @MockBean
    private TransactionRestClient service;

    @Test
    public void givenMockingIsDoneByMockito_whenGetForEntityIsCalled_thenReturnMockedObject() {
        Transaction transaction = Transaction.builder().id("2c945081916656e501916656f2f70001")
                .notes("best price promo")
                .amount(10.0)
                .type(TransactionType.DEPOSIT)
                .completed(true)
                .build();
        Mockito
          .when(restTemplate.getForEntity(
            "http://localhost:8081/api/transactions/2c945081916656e501916656f2f70000", Transaction.class))
          .thenReturn(new ResponseEntity(transaction, HttpStatus.OK));

        Transaction transaction2 = restTemplate.getForEntity("http://localhost:8081/api/transactions/2c945081916656e501916656f2f70000", Transaction.class).getBody(); //service.findById("2c945081916656e501916656f2f70000");
        Assertions.assertEquals(transaction, transaction2);
    }

}
