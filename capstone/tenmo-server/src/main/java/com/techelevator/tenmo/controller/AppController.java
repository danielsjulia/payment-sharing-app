package com.techelevator.tenmo.controller;


import com.techelevator.tenmo.dao.AccountDAO;
import com.techelevator.tenmo.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.security.Principal;


@RestController
public class AppController {

    @Autowired
    AccountDAO accountDAO;

    @RequestMapping(path="/account/{id}", method = RequestMethod.GET)
    public Account getBalance(@PathVariable Long id) {
        return accountDAO.getBalance(id);
    }

    @RequestMapping(path = "")
    public String userIdentity(Principal principal) {
        return principal.getName();
    }

}
