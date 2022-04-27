package com.techelevator.tenmo.controller;


import com.techelevator.tenmo.dao.AccountDAO;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferDTO;
import com.techelevator.tenmo.model.User;

import com.techelevator.tenmo.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;


@RestController
public class AppController {

    @Autowired
    AccountDAO accountDAO;

    @Autowired
    TransferDao transferDao;

//    @RequestMapping(path="/account/{accountId}", method = RequestMethod.GET)
//    public BigDecimal getBalance(@PathVariable Long accountId) {
//        return accountDAO.getBalance(accountId);

    @RequestMapping(path="/account/{id}", method = RequestMethod.GET)
    public Account getBalance(@PathVariable Long id) {
        return accountDAO.getBalance(id);
    }

    @RequestMapping(path="/tenmo_user", method = RequestMethod.GET)
    public List<User> getUsers() {
        return transferDao.viewListOfUsers();
    }

    @RequestMapping(path="/transfer", method = RequestMethod.POST)
    public Transfer makeTransfer(@RequestBody TransferDTO transferDTO) {
        Transfer transfer = mapDTOToTransfer(transferDTO);

        return transferDao.addTransfer(transfer);
    }

    @RequestMapping(path = "")
    public String userIdentity(Principal principal) {
        return principal.getName();
    }


    private Transfer mapDTOToTransfer(TransferDTO transferDTO) {
        Transfer transfer = new Transfer();

        transfer.setTransferTypeId(2); // 2 = send
        transfer.setTransferStatusId(2); // 2 = approved
        transfer.setAccountFromId(transferDTO.getAccountFromId());
        transfer.setAccountToId(transferDTO.getAccountToId());
        transfer.setTransferAmount(transferDTO.getTransferAmount());

        return transfer;
    }

}
