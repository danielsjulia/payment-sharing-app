package com.techelevator.tenmo.controller;


import com.techelevator.tenmo.dao.AccountDAO;
<<<<<<< HEAD
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.model.User;
=======
import com.techelevator.tenmo.model.Account;
>>>>>>> 8821dd72ccc0be94dbb122a7b912ec681fd92e1a
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;


@RestController
public class AppController {

    @Autowired
    AccountDAO accountDAO;


    @Autowired
    TransferDao transferDao;

    @RequestMapping(path="/account/{accountId}", method = RequestMethod.GET)
    public BigDecimal getBalance(@PathVariable Long accountId) {
        return accountDAO.getBalance(accountId);

    @RequestMapping(path="/account/{id}", method = RequestMethod.GET)
    public Account getBalance(@PathVariable Long id) {
        return accountDAO.getBalance(id);

    }

    @RequestMapping(path="/tenmo_user", method = RequestMethod.GET)
    public List<User> getUsers() {
        return transferDao.viewListOfUsers();
    }

    @RequestMapping(path = "")
    public String userIdentity(Principal principal) {
        return principal.getName();
    }

}
