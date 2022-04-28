package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;

public interface AccountDAO {

    Account getBalance(long accountId);
    Long getAccountId(long userId);
    Long getUserId(long accountId);
    void transferBalance(Transfer transfer);


}
