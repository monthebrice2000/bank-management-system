package com.bank.bank_api.rest_client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.bank.bank_api.exception.TransactionErrorHandler;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class RestTemplateConfig {

    @Autowired
    private BankRestClientProperties properties;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        log.info("> +++ Auth Rest Client : " + properties);
        RestTemplate restTemplate = builder.basicAuthentication(properties.getUsername(), properties.getPassword()).build();
        restTemplate.setErrorHandler(new TransactionErrorHandler());
        return restTemplate;


    }

}
