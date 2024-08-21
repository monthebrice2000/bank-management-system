package com.bank.bank_api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "transaction.authentication")
@Component
public class TransactionProperties {
    // @Value("${transaction.authentication.email}")
    private String findByEmailUri;

    // @Value("${transaction.authentication.username}")
    private String username;

    // @Value("${transaction.authentication.password}")
    private String password;
}
