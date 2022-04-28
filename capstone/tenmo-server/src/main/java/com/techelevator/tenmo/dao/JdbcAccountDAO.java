package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigDecimal;

@Component
public class JdbcAccountDAO implements AccountDAO{

    private JdbcTemplate jdbcTemplate;
    private UserDao userDao;

    public JdbcAccountDAO(DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }

    @Override
    public Account getBalance(long id) {

        String sql = "SELECT account_id, user_id, balance FROM account WHERE user_id = ?;";
        SqlRowSet results = this.jdbcTemplate.queryForRowSet(sql, id);
        Account account = null;

        while (results.next()) {
            account = accountObjectMapper(results);
        }

        return account;
    }

    public void transferBalance(Transfer transfer) {
        // set up account info
        long fromAccount = transfer.getAccountFromId();
        long fromUser = getUserId(fromAccount);
        BigDecimal fromNewBalance = getBalance(fromUser).getBalance().subtract(transfer.getTransferAmount());

        long toAccount = transfer.getAccountToId();
        long toUser = getUserId(toAccount);
        BigDecimal toNewBalance = getBalance(toUser).getBalance().add(transfer.getTransferAmount());

        // update accounts
        String sql = "UPDATE account SET balance = ? WHERE account_id = ?;";
        jdbcTemplate.update(sql, fromNewBalance, fromAccount);
        jdbcTemplate.update(sql, toNewBalance, toAccount);

    }

    @Override
    public Long getAccountId(long userId) {
        return null;
    }

    @Override
    public Long getUserId(long accountId) {
        String sql = "SELECT user_id FROM account WHERE account_id = ?;";
        long userId = jdbcTemplate.queryForObject(sql, Long.class, accountId);

        return userId;
    }

    public Account accountObjectMapper(SqlRowSet results) {
        Account account = new Account();

        account.setAccountId(results.getLong("account_id"));
        account.setUserId(results.getLong("user_id"));
        account.setBalance(results.getBigDecimal("balance"));

        return account;
    }
}
