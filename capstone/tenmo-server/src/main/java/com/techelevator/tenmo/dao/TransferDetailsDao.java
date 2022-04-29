package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.TransferDetails;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.List;

public interface TransferDetailsDao {

    TransferDetails getTransferDetails(Long transferId);
    List<TransferDetails> getTransferHistory(Long accountId);

}
