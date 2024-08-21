// package com.bank.bank_api;

// import org.junit.Test;
// import org.junit.Before;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.junit.runner.RunWith;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.http.MediaType;
// import org.springframework.test.context.junit.jupiter.SpringExtension;
// import org.springframework.test.context.junit4.SpringRunner;
// import org.springframework.test.web.client.MockRestServiceServer;
// import org.springframework.web.client.RestTemplate;

// import com.bank.bank_api.config.ApplicationConfig;
// import com.bank.bank_api.domain.Transaction;
// import com.bank.bank_api.repository.TransactionRepository;
// import com.bank.bank_api.rest_client.TransactionRestClient;
// import com.bank.bank_api.service.TransactionService;

// import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
// import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
// import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

// @RunWith(SpringRunner.class)
// // @ExtendWith(SpringExtension.class)
// // @RestClientTest(ApplicationConfig.class)
// @SpringBootTest

// public class TransactionRestClientTests {

//     @Autowired
//     private TransactionRestClient service;

//     @Autowired
//     private RestTemplate template;

//     @Autowired
//     private MockRestServiceServer mockServer;


//     @Before
//     public void init() {
//         mockServer = MockRestServiceServer.createServer(template);
//     }

//     @Test
//     public void transactionRestClientTest() throws Exception {
//         String content = "{\"id\":\"2c945081916656e501916656f2f70000\",\"type\":\"DEPOSIT\",\"amount\":10.0,\"notes\":\"best price promo\",\"completed\":true,\"created\":null,\"modified\":null}";
        
//         // mockServer = MockRestServiceServer.bindTo(template).build();
//         mockServer.expect(requestTo("/api/transactions/2c945081916656e501916656f2f70000"))
//                 .andRespond(withSuccess(content, MediaType.APPLICATION_JSON));
//         Transaction result = this.service.findById("2c945081916656e501916656f2f70000");
//         assertThat(result).isNotNull();
//         assertThat(result.getNotes()).contains("best price promo");
//     }

// }
