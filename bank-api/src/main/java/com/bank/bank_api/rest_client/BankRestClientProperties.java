package com.bank.bank_api.rest_client;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties(prefix = "bank-api")
@Data
public class BankRestClientProperties {

    private String url;
    private String basePath;
    private String username;
    private String password;

}
