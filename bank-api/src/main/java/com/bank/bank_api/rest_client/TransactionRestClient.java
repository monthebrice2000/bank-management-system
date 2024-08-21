package com.bank.bank_api.rest_client;

import java.lang.reflect.ParameterizedType;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.web.client.RestTemplate;

import com.bank.bank_api.domain.Transaction;
import com.bank.bank_api.exception.TransactionErrorHandler;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TransactionRestClient {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private BankRestClientProperties properties;

    // public TransactionRestClient() {
    // this.restTemplate.setErrorHandler(new TransactionErrorHandler());
    // }

    public Iterable<Transaction> findAll() throws URISyntaxException {
        RequestEntity<Iterable<Transaction>> requestEntity = new RequestEntity<Iterable<Transaction>>(HttpMethod.GET,
                new URI(properties.getUrl() +
                        properties.getBasePath()));
        ResponseEntity<Iterable<Transaction>> response = restTemplate.exchange(requestEntity,
                new ParameterizedTypeReference<Iterable<Transaction>>() {
                });
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        }
        return null;
    }

    public Transaction findById(String id) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", id);
        return restTemplate.getForObject(properties.getUrl() + properties.getBasePath() + "/{id}", Transaction.class,
                params);
    }

    public Transaction upsert(Transaction Transaction) throws URISyntaxException {

        // Create Authorization Header
        // HttpHeaders headers = new HttpHeaders();
        // String auth = properties.getUsername() + ":" + properties.getPassword();
        // byte[] encodedAuth = Base64Utils.encode(auth.getBytes(StandardCharsets.UTF_8));
        // String authHeader = "Basic " + new String(encodedAuth);
        // headers.set("Authorization", authHeader);
        // headers.set("Content-Type", "application/json");
        // headers.set("Accept", "application/json");

        RequestEntity<?> requestEntity = new RequestEntity<>(Transaction, HttpMethod.POST, new URI(properties.getUrl() +
                properties.getBasePath()));
        ResponseEntity<?> response = restTemplate.exchange(requestEntity,
                new ParameterizedTypeReference<Transaction>() {
                });
        if (response.getStatusCode() == HttpStatus.CREATED) {
            return restTemplate.getForObject(response.getHeaders().getLocation(), Transaction.class);
        }
        return null;
    }

    public Transaction setCompleted(String id) throws URISyntaxException {
        // Map<String, String> params = new HashMap<String, String>();
        // params.put("id", id);
        // restTemplate.postForObject(properties.getUrl() + properties.getBasePath() +
        // "/{id}?_method=patch", null,
        // ResponseEntity.class,
        // params);
        // return findById(id);

        // Construct the URL with the ID parameter
        String url = properties.getUrl() + properties.getBasePath() + "/" + id;

        // Create the headers, if needed
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON); // Set content type if necessary
        headers.add("X-HTTP-Method-Override", "PATCH");

        // Create the request entity, in this case, no body is required for setting
        HttpEntity<Void> requestEntity = new HttpEntity<>(null, headers);

        // Make the PATCH request
        ResponseEntity<Transaction> response = restTemplate.exchange(
                new URI(url),
                HttpMethod.POST,
                requestEntity,
                Transaction.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return restTemplate.getForObject(response.getHeaders().getLocation(),
                    Transaction.class);
        }
        return null;

    }

    public void delete(String id) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", id);
        restTemplate.delete(properties.getUrl() + properties.getBasePath()
                + "/{id}", params);
    }

}
