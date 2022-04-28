package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferDTO;
import com.techelevator.tenmo.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao{

    private JdbcTemplate jdbcTemplate;

    public JdbcTransferDao(DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }

    @Override
    public List<User> viewListOfUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT user_id, username FROM tenmo_user;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while(results.next()) {
            User user = mapRowToUser(results);
            users.add(user);
        }
        return users;
    }

    // insert transfer to table
    public Transfer addTransfer(Transfer transfer) {

        String sql = "INSERT INTO transfer (transfer_type_id, transfer_status_id, account_from, account_to, amount) " +
                "VALUES (?, ?, ?, ?, ?) RETURNING transfer_id;";
        long newTransferId = jdbcTemplate.queryForObject(sql, Long.class,
                transfer.getTransferTypeId(), transfer.getTransferStatusId(), transfer.getAccountFromId(), transfer.getAccountToId(), transfer.getTransferAmount());

        return getTransfer(newTransferId);
    }

    public Transfer getTransfer(Long transferId) {
        String sql = "SELECT * FROM transfer WHERE transfer_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferId);
        Transfer transfer = new Transfer();

        if (results.next()) {
            transfer = mapRowToTransfer(results);
        }

        return transfer;
    }

    public List<Transfer> getAllTransfersByAccount(Long accountId) {
        String sql = "SELECT * FROM transfer WHERE account_from = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, accountId);
        List<Transfer> transfers = new ArrayList<>();

        while (results.next()) {
            transfers.add(mapRowToTransfer(results));
        }
        return transfers;
    }


    @Override
    public User selectUser(Long id) {
        return null;
    }

    private User mapRowToUser(SqlRowSet rs) {
        User user = new User();
        user.setId(rs.getLong("user_id"));
        user.setUsername(rs.getString("username"));
        return user;
    }

    private Transfer mapRowToTransfer(SqlRowSet results) {
        Transfer transfer = new Transfer();

        transfer.setTransferId(results.getLong("transfer_id"));
        transfer.setTransferTypeId(2); // 2 = send
        transfer.setTransferStatusId(2); // 2 = approved
        transfer.setAccountFromId(results.getLong("account_from"));
        transfer.setAccountToId(results.getLong("account_to"));
        transfer.setTransferAmount(results.getBigDecimal("amount"));

        return transfer;
    }




}
