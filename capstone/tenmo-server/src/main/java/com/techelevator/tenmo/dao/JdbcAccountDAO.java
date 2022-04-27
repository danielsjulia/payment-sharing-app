package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigDecimal;

@Component
public class JdbcAccountDAO implements AccountDAO{

    private JdbcTemplate jdbcTemplate;

    public JdbcAccountDAO(DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }

    @Override
    public BigDecimal getBalance(Long id) {
        String sql = "SELECT balance FROM account WHERE user_id = ?;";
        SqlRowSet results = this.jdbcTemplate.queryForRowSet(sql, id);
        Account account = null;

        if (results.next()) {
            account = accountObjectMapper(results);
        }

        return account.getBalance();
    }

    @Override
    public Long getAccountId() {
        return null;
    }

    @Override
    public Long getUserId() {
        return null;
    }

    public Account accountObjectMapper(SqlRowSet results) {
        Account account = new Account();

        account.setAccountId(results.getLong("account_id"));
        account.setUserId(results.getLong("user_id"));
        account.setBalance(results.getBigDecimal("balance"));

        return account;
    }
}
