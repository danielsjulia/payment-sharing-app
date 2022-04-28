package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferDTO;
import com.techelevator.tenmo.model.User;
import com.techelevator.util.BasicLogger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class TransferService {

    private static final String API_BASE_URL = "http://localhost:8080/";
    private final RestTemplate restTemplate = new RestTemplate();

    private String authToken = null;

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public List<User> getUsers() {
        User[] usersArray;
        List<User> listOfUsers = new ArrayList<>();

        try {
            usersArray = restTemplate.exchange(API_BASE_URL + "tenmo_user", HttpMethod.GET,
                    makeAuthEntity(), User[].class).getBody();

            for(User user : usersArray) {

                listOfUsers.add(user);
            }

        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return listOfUsers;
    }


    public Transfer completedTransfer(TransferDTO transferDTO) {
        // POST to transfer table
        // PUT to update both user's balance
        // save to transfer object
        Transfer newTransfer = null;

        try {
            newTransfer = restTemplate.exchange(
                    API_BASE_URL + "transfer",
                    HttpMethod.POST,
                    makeTransferEntity(transferDTO),
                    Transfer.class
            ).getBody();
            System.out.println("Transfer made!");
            System.out.println(newTransfer);

        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return newTransfer;
    }

    public HttpEntity<TransferDTO> makeTransferEntity(TransferDTO transferDTO) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        HttpEntity<TransferDTO> entity = new HttpEntity<>(transferDTO, headers);
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
