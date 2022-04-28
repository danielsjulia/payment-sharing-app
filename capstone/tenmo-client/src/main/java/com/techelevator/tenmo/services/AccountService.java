package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.util.BasicLogger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

public class AccountService {

    private static final String API_BASE_URL = "http://localhost:8080/";
    private final RestTemplate restTemplate = new RestTemplate();

    private String authToken = null;

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }


    public BigDecimal getBalance() {
        Account account = null;
        BigDecimal balance = null;
        try {
            account = restTemplate.exchange(
                    API_BASE_URL + "balance",
                    HttpMethod.GET,
                    makeAuthEntity(),
                    Account.class
            ).getBody();
            balance = account.getBalance();

        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return balance;
    }


//    public Account getAccount() {
//        Account account1 = null;
//
//        try {
//            account1 = restTemplate.exchange(
//                    API_BASE_URL + "balance",
//                    HttpMethod.GET,
//                    makeAuthEntity(),
//                    Account.class
//            ).getBody();
//
//        } catch (RestClientResponseException | ResourceAccessException e) {
//            BasicLogger.log(e.getMessage());
//        }
//        return account1;
//    }


    private HttpEntity<Account> makeAccountEntity(Account account) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        HttpEntity<Account> entity = new HttpEntity<>(account, headers);
        return entity;
    }

    private HttpEntity<Void> makeAuthEntity() {
        // only headers for GET

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        return entity;
    }

}
