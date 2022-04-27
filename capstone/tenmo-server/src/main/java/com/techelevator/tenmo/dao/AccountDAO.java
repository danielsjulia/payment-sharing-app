package com.techelevator.tenmo.dao;

import java.math.BigDecimal;

public interface AccountDAO {

    BigDecimal getBalance(Long accountId);
    Long getAccountId();
    Long getUserId();


}
