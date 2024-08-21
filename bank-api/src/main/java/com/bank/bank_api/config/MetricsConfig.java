package com.bank.bank_api.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.MappedInterceptor;

import com.bank.bank_api.interceptor.TransactionMetricInterceptor;

import io.micrometer.core.instrument.MeterRegistry;

@EnableConfigurationProperties(TransactionProperties.class)
@Configuration
public class MetricsConfig {

    @Bean
    public MappedInterceptor metricInterceptor(MeterRegistry registry) {
        return new MappedInterceptor(new String[] { "/**" },
                new TransactionMetricInterceptor(registry));
    }
}
