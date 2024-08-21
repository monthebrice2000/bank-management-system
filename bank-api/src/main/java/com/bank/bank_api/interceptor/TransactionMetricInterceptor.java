package com.bank.bank_api.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import io.micrometer.core.instrument.MeterRegistry;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class TransactionMetricInterceptor implements HandlerInterceptor {

    private static Logger log = LoggerFactory.getLogger(TransactionMetricInterceptor.class);

    // @Autowired
    private MeterRegistry registry;

    private String URI, pathKey, METHOD;

    
    @Autowired
    public TransactionMetricInterceptor(MeterRegistry registry) {
        this.registry = registry;
    }



    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        URI = request.getRequestURI();
        METHOD = request.getMethod();
        if (!URI.contains("prometheus")) {
            log.info(" >> PATH: {}", URI);
            log.info(" >> METHOD: {}", METHOD);
            pathKey = "api_".concat(METHOD.toLowerCase()).concat(URI.replaceAll("/", "_").toLowerCase());
            this.registry.counter(pathKey).increment();
        }
    }
}
