package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

import java.math.BigDecimal;

public interface AccountDAO {

    Account getBalance(long accountId);
    Long getAccountId();
    Long getUserId();


}
