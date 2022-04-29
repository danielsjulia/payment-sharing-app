package com.techelevator.tenmo.controller;


import com.techelevator.tenmo.dao.AccountDAO;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferDTO;
import com.techelevator.tenmo.model.User;

import com.techelevator.tenmo.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@PreAuthorize("isAuthenticated()")
@RestController
public class AppController {

    @Autowired
    AccountDAO accountDAO;

    @Autowired
    TransferDao transferDao;

    // autowire user dao to connect principal to user id
    @Autowired
    UserDao userDao;


    // get balance using principal
    // changes for client side: change path;
    @RequestMapping(path="/balance", method = RequestMethod.GET)
    public Account getBalance(Principal principal) {
        principal.getName();
        int userId = userDao.findIdByUsername(principal.getName());
        return accountDAO.getBalance((long)userId);
    }


    @RequestMapping(path="/tenmo_user", method = RequestMethod.GET)
    public List<User> getUsers() {
        return transferDao.viewListOfUsers();
    }

    @RequestMapping(path="/transfer", method = RequestMethod.POST)
    public Transfer makeTransfer(@RequestBody TransferDTO transferDTO) {
        Transfer transfer = mapDTOToTransfer(transferDTO);

        // update accounts
        accountDAO.transferBalance(transfer);
        return transferDao.addTransfer(transfer);
    }

    @RequestMapping(path = "/transfer/{transferId}", method = RequestMethod.GET)
    public Transfer getTransferDetails(@PathVariable Long transferId) {
        return transferDao.getTransfer(transferId);
    }

    @RequestMapping(path = "/transfer", method = RequestMethod.GET)
    public List<Transfer> getTransferHistory(Principal principal) {
        int userId = userDao.findIdByUsername(principal.getName());
        long accountId = userDao.findAccountByUserId(userId);

        return transferDao.getAllTransfersByAccount(accountId);
    }

    @RequestMapping(path = "")
    public String userIdentity(Principal principal) {
        return principal.getName();
    }


    private Transfer mapDTOToTransfer(TransferDTO transferDTO) {
        Transfer transfer = new Transfer();

        transfer.setTransferTypeId(2); // 2 = send
        transfer.setTransferStatusId(2); // 2 = approved
        transfer.setAccountFromId(userDao.findAccountByUserId(transferDTO.getUserFromId()));
        transfer.setAccountToId(userDao.findAccountByUserId(transferDTO.getUserToId()));
        transfer.setTransferAmount(transferDTO.getTransferAmount());

        return transfer;
    }

}
