package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferDetails;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDetailsDao implements TransferDetailsDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcTransferDetailsDao(DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }

    @Override
    public List<TransferDetails> getTransferHistory(Long accountId) {
        String sql = "SELECT transfer_id, transfer_status_desc, transfer_type_desc, username, amount FROM transfer " +
                "JOIN account ON account_to = account_id " +
                "JOIN tenmo_user ON account.user_id = tenmo_user.user_id " +
                "JOIN transfer_status ON transfer_status.transfer_status_id = transfer.transfer_status_id " +
                "JOIN transfer_type ON transfer_type.transfer_type_id = transfer.transfer_type_id " +
                "WHERE account_from = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, accountId);
        List<TransferDetails> transferHistory = new ArrayList<>();

        while (results.next()) {
            transferHistory.add(mapRowToTransferDetails(results));
        }
        return transferHistory;
    }

    @Override
    public TransferDetails getTransferDetails(Long transferId) {
        String sql = "SELECT transfer_id, transfer_status_desc, transfer_type_desc, username, amount FROM transfer " +
                "JOIN account ON account_to = account_id " +
                "JOIN tenmo_user ON account.user_id = tenmo_user.user_id " +
                "JOIN transfer_status ON transfer_status.transfer_status_id = transfer.transfer_status_id " +
                "JOIN transfer_type ON transfer_type.transfer_type_id = transfer.transfer_type_id " +
                "WHERE transfer_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferId);
        TransferDetails transferDetails = new TransferDetails();

        if (results.next()) {
            transferDetails = mapRowToTransferDetails(results);
        }

        return transferDetails;
    }

    private TransferDetails mapRowToTransferDetails(SqlRowSet results) {
        TransferDetails transferDetails = new TransferDetails();

        transferDetails.setTransferId(results.getLong("transfer_id"));
        transferDetails.setAccountToUsername(results.getString("username"));
        transferDetails.setTransferTypeDescription(results.getString("transfer_type_desc"));
        transferDetails.setTransferStatus(results.getString("transfer_status_desc"));
        transferDetails.setTransferAmount(results.getBigDecimal("amount"));

        return transferDetails;
    }

}
