package com.bank.bank_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.JmsAutoConfiguration;

@SpringBootApplication //(exclude = ActiveMQAutoConfiguration.class)
public class BankApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankApiApplication.class, args);

		// SpringApplication app = new SpringApplication(BankApiApplication.class);
		// app.setWebApplicationType(WebApplicationType.NONE);
		// app.run(args);
	}

}
