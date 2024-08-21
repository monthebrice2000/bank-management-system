package com.bank.bank_api.messaging.websocket.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import lombok.Data;

@Profile("ws-profile")
@Data
@ConfigurationProperties(prefix = "bank.ws.transaction")
@Component
public class WebsocketProperties {

    private String app; // = "/transactions-api-ws";
    private String broker; // = "/transactions";
    private String endpoint; // = "/stomp";

}
