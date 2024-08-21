// package com.bank.bank_api;

// import static org.assertj.core.api.Assertions.assertThat;

// import org.junit.Test;
// import org.junit.runner.RunWith;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.json.JsonTest;
// import org.springframework.boot.test.json.JacksonTester;
// import org.springframework.test.context.junit4.SpringRunner;

// import com.bank.bank_api.domain.Transaction;

// @RunWith(SpringRunner.class)
// @JsonTest
// public class TransactionJsonTests {

//     @Autowired
//     private JacksonTester<Transaction> json;

//     @Test
//     public void transactionSerializeTest() throws Exception {
//         Transaction transaction = Transaction.builder().notes("Read a Book").build();
//         assertThat(this.json.write(transaction))
//                 .isEqualToJson("todo.json");
//         assertThat(this.json.write(transaction))
//                 .hasJsonPathStringValue("@.description");
//         assertThat(this.json.write(transaction))
//                 .extractingJsonPathStringValue("@.description")
//                 .isEqualTo("Read a Book");
//     }

//     @Test
//     public void transactionDeserializeTest() throws Exception {
//         String content = "{\"description\":\"Read a Book\",\"completed\":true }";

//         assertThat(this.json.parse(content))
//                 .isEqualTo(Transaction.builder().notes("Read a Book").completed(true).build());
//         assertThat(
//                 this.json.parseObject(content).getNotes())
//                         .isEqualTo("Read a Book");
//     }
// }
