package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.util.BasicLogger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
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


    public BigDecimal getBalance(Long id) {
        Account account1 = null;
        BigDecimal balance = null;

        try {
            account1 = restTemplate.exchange(
                    API_BASE_URL + "tenmo/account/" + id,
                    HttpMethod.GET,
                    makeAuthEntity(),
                    Account.class
            ).getBody();
            balance = account1.getBalance();

        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return balance;
    }

//        try {
//            ResponseEntity<BigDecimal> balance = restTemplate.exchange(
//                    API_BASE_URL + "tenmo/account/" + accountId,
//                    HttpMethod.GET,
//                    makeAccountEntity(),
//                    Account.class
//            ).getBody();
//
//        } catch (RestClientResponseException | ResourceAccessException e) {
//            BasicLogger.log(e.getMessage());
//        }
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
