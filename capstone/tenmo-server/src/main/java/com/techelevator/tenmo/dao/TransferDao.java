package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;

import java.util.List;

public interface TransferDao {

    List<User> viewListOfUsers();

    User selectUser(Long id);

    Transfer addTransfer(Transfer transfer);
    Transfer getTransfer(Long transferId);
    List<Transfer> getAllTransfersByAccount(Long accountId);

}
