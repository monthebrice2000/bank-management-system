package com.bank.bank_api.messaging.jms_activemq.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "bank.jms.transaction")
@Profile("activemq-profile")
public class JmsProperties {

    private String destination;

}
