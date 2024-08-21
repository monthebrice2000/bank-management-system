package com.bank.bank_api.loader;

import java.net.URI;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.annotation.Order;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.bank.bank_api.builder.TransactionBuilder;
import com.bank.bank_api.builder.TransactionRedisBuilder;
import com.bank.bank_api.config.TransactionProperties;
import com.bank.bank_api.constant.TransactionType;
import com.bank.bank_api.domain.Transaction;
import com.bank.bank_api.domain.TransactionRedis;
import com.bank.bank_api.domain.User;
import com.bank.bank_api.repository.TransactionRepository;
import com.bank.bank_api.repository.TransactionRedisRepository;
import com.bank.bank_api.repository.UserRepository;
import com.bank.bank_api.rest_client.TransactionRestClient;

// import com.ecommerce.burgers_models.models.Burger;
// import com.ecommerce.burgers_models.models.Ingredient;
// import com.ecommerce.burgers_models.models.User;
// import com.ecommerce.burgers_models.models.Ingredient.Type;
// import com.ecommerce.burgers_repository.repository.BurgerRepository;
// import com.ecommerce.burgers_repository.repository.IngredientRepository;
// import com.ecommerce.burgers_repository.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoProcessor;
import reactor.core.scheduler.Schedulers;

// @Profile("!prod")
@Configuration
@Slf4j
public class DevelopmentDataLoader {

        private Logger log = LoggerFactory.getLogger(DevelopmentDataLoader.class);

        @Bean
        @Order(1)
        public CommandLineRunner dataLoader( 
                        TransactionRepository repo
                        // TransactionRedisRepository repoRedis
                        ) {
                log.info("----------------------- GET -------------------------");
                log.info("INSERT TRANSACTIONS ...");
                return args -> {
                        Transaction transactionOne = TransactionBuilder.create()
                                        .withAmount(10)
                                        .withType(TransactionType.DEPOSIT)
                                        .withNotes("best price promo")
                                        .build();

                        // TransactionRedis transactionRedisOne = TransactionRedisBuilder.create()
                        //                 .withTransaction(transactionOne)
                        //                 .build();

                        Transaction transactionTwo = TransactionBuilder.create()
                                        .withAmount(101)
                                        .withType(TransactionType.DEPOSIT)
                                        .withNotes("cool price promo")
                                        .build();
                        Transaction transactionThree = TransactionBuilder.create()
                                        .withAmount(30)
                                        .withType(TransactionType.WITHDRAW)
                                        .withNotes("rude price promo")
                                        .build();
                        Transaction transactionFour = TransactionBuilder.create()
                                        .withAmount(1034)
                                        .withType(TransactionType.DEPOSIT)
                                        .withNotes("high price promo")
                                        .build();

                        repo.save(transactionOne);
                        // repoRedis.save(transactionRedisOne);

                        repo.save(transactionTwo);
                        repo.save(transactionThree);
                        repo.save(transactionFour);

                        log.info("END INSERT TRANSACTIONS ...");

                };
        }

        @Bean
        @Order(2)
        public CommandLineRunner customDataLoader(
                        UserRepository repo, PasswordEncoder encoder) {
                log.info("----------------------- GET -------------------------");
                log.info("GETTING INGREDIENT BY IDE");
                return args -> {
                        repo.save(User.builder().name("Mark").email("mark@example.com")
                                        .password(encoder.encode("secret"))
                                        .role("USER").enabled(true).birthday(LocalDate.parse("1960-03-29")).build());
                        repo.save(User.builder().name("Matt").email("matt@example.com")
                                        .password(encoder.encode("secret"))
                                        .role("ADMIN").enabled(true).birthday(LocalDate.parse("1980-07-03")).build());
                };
        }

        @Bean
        @Order(10)
        public CommandLineRunner processRestTemplate(TransactionRestClient client) {
                log.info("----------------------- START REST CLIENT -------------------------");
                return args -> {
                        Iterable<Transaction> transactions = client.findAll();
                        assert transactions != null;
                        transactions.forEach(transaction -> log.info("Find Transactions : " + transaction.toString()));

                        Transaction newTransaction = client
                                        .upsert(Transaction.builder()
                                                        .amount(100.00)
                                                        .type(TransactionType.WITHDRAW)
                                                        .notes("Drink plenty of Water daily!")
                                                        .build());
                        // Transaction newTransaction = transactions.iterator().next();
                        assert newTransaction != null;
                        log.info("Post Transaction " + newTransaction.toString());

                        Transaction toDo = client.findById(newTransaction.getId());
                        assert transactions != null;
                        log.info("Get Transaction " + toDo.toString());

                        Transaction completed = client.setCompleted(newTransaction.getId());
                        assert completed.isCompleted();
                        log.info("Update Transaction " + completed.toString());

                        client.delete(newTransaction.getId());
                        assert client.findById(newTransaction.getId()) == null;
                        log.info("Delete Transaction " + completed.toString());
                        log.info("----------------------- END REST CLIENT -------------------------");
                };

        }

        // @Bean
        // @Order(3)
        // public CommandLineRunner getUserRestClient(RestTemplateBuilder restTemplateBuilder, TransactionProperties transactionProperties) {
        //         return args -> {
        //                 log.info("++++++++++++++++++++++++ : " + transactionProperties);
        //                 String username = transactionProperties.getUsername();
        //                 RestTemplate restTemplate = restTemplateBuilder.basicAuthentication(transactionProperties.getUsername(),
        //                         transactionProperties.getPassword()).build();
        //                 try {
                                
        //                         UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(transactionProperties.getFindByEmailUri())
        //                                         .queryParam("email", username);
        //                         log.info("Querying: " + builder.toUriString());
        //                         ResponseEntity<EntityModel<com.bank.bank_api.domain.User>> responseEntity = restTemplate
        //                                         .exchange(
        //                                                         RequestEntity.get(URI.create(builder.toUriString()))
        //                                                                         .accept(MediaTypes.HAL_JSON).build(),
        //                                                         new ParameterizedTypeReference<EntityModel<com.bank.bank_api.domain.User>>() {
        //                                                         });
        //                         if (responseEntity.getStatusCode() == HttpStatus.OK) {
        //                                 EntityModel<com.bank.bank_api.domain.User> resource = responseEntity.getBody();
        //                                 com.bank.bank_api.domain.User user = resource.getContent(); 
        //                                 // PasswordEncoder encoder =
        //                                 // PasswordEncoderFactories.createDelegatingPasswordEncoder();
        //                                 // String password = encoder.encode(person.getPassword());
        //                                 UserDetails userTwo = org.springframework.security.core.userdetails.User
        //                                                 .withUsername(user.getEmail()).password(user.getPassword())
        //                                                 .accountLocked(!user.isEnabled()).roles(user.getRole()).build();
        //                                 log.info("Querying: " + userTwo);
        //                         }

        //                 } catch (Exception ex) {
        //                         // ex.printStackTrace();
        //                         throw new UsernameNotFoundException("User '" + username + "' not found");
        //                 }
        //         };

        // }

        // @Bean
        // public CommandLineRunner runMonoExample() {
        // return args -> {
        // MonoProcessor<Transaction> promise = MonoProcessor.create();
        // Mono<Transaction> result = promise
        // .doOnSuccess(p -> log.info("MONO >> ToDo: {}", p.getNotes()))
        // .doOnTerminate(() -> log.info("MONO >> Done"))
        // .doOnError(t -> log.error(t.getMessage(), t))
        // .subscribeOn(Schedulers.single());
        // promise.onNext(
        // TransactionBuilder.create()
        // .withNotes("Buy my ticket for SpringOne Platform 2018")
        // .build());
        // // promise.onError(
        // new IllegalArgumentException("There is an error processing the ToDo...");
        // result.block(Duration.ofMillis(1000));
        // };
        // }

        // @SuppressWarnings("deprecation")
        // @Bean
        // public CommandLineRunner runFluxExample() {
        // return args -> {
        // EmitterProcessor<Transaction> stream = EmitterProcessor.create();
        // Mono<List<Transaction>> promise = stream
        // .filter(s -> s.isCompleted())
        // .doOnNext(s -> log.info("FLUX >>> ToDo: {}",
        // s.getNotes()))
        // .collectList()
        // .subscribeOn(Schedulers.single());
        // stream.onNext(TransactionBuilder.create().withNotes("Read a
        // Book").withCompleted(true).build());
        // stream.onNext(TransactionBuilder.create().withNotes("Listen Classical Music")
        // .withCompleted(true).build());
        // stream.onNext(TransactionBuilder.create().withNotes("Workout in the
        // Mornings").build());
        // stream.onNext(TransactionBuilder.create().withNotes("Organize my
        // room").withCompleted(true)
        // .build());
        // stream.onNext(TransactionBuilder.create().withNotes("Go to the Car
        // Wash").withCompleted(true)
        // .build());
        // stream.onNext(TransactionBuilder.create().withNotes("SP1 2018 is
        // coming").withCompleted(true)
        // .build());
        // stream.onComplete();
        // promise.block();
        // };
        // }

}
