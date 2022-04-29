package com.techelevator.tenmo;

import com.techelevator.tenmo.model.Transfer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class TransferTests {

    private Transfer transfer;

    @Before
    public void setup() {
        transfer = new Transfer();
        transfer.setTransferId(3002L);
        transfer.setAccountFromId(1001L);
        transfer.setAccountToId(1002L);
        transfer.setTransferStatusId(2);
        transfer.setTransferTypeId(2);
        transfer.setTransferAmount(new BigDecimal("25.00"));
    }

    public void test_get_transfer_amount() {
        BigDecimal expected = new BigDecimal("25.00");
        BigDecimal actual = transfer.getTransferAmount();

        Assert.assertEquals(expected,actual);
    }

    @Test
    public void test_get_transfer_id() {
        long expected = 3002L;
        long actual = transfer.getTransferId();

        Assert.assertEquals(expected,actual);
    }

    @Test
    public void test_get_account_from_id() {
        long expected = 1001L;
        long actual = transfer.getAccountFromId();

        Assert.assertEquals(expected,actual);
    }

    @Test
    public void test_get_account_to_id() {
        long expected = 1002;
        long actual = transfer.getAccountToId();

        Assert.assertEquals(expected,actual);
    }

    @Test
    public void test_get_status_id() {
        int expected = 2;
        int actual = transfer.getTransferStatusId();

        Assert.assertEquals(expected,actual);
    }

    @Test
    public void test_get_type_id() {
        int expected = 2;
        int actual = transfer.getTransferTypeId();

        Assert.assertEquals(expected,actual);
    }




}
