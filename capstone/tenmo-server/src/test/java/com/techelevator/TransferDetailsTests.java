package com.techelevator;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferDetails;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class TransferDetailsTests {

    private TransferDetails transferDetails;

    @Before
    public void setUp() {
        transferDetails = new TransferDetails();

        transferDetails.setTransferId(3002L);
        transferDetails.setAccountToUsername("test");
        transferDetails.setTransferTypeDescription("test description");
        transferDetails.setTransferStatus("test status");
        transferDetails.setTransferAmount(new BigDecimal("19.99"));
    }

    @Test
    public void test_get_transfer_amount() {
        BigDecimal expected = new BigDecimal("19.99");
        BigDecimal actual = transferDetails.getTransferAmount();

        Assert.assertEquals(expected,actual);
    }

    @Test
    public void test_get_transfer_id() {
        long expected = 3002L;
        long actual = transferDetails.getTransferId();

        Assert.assertEquals(expected,actual);
    }

    @Test
    public void get_to_username() {
        String expected = "test";
        String actual = transferDetails.getAccountToUsername();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void test_get_type_description() {
        String expected = "test description";
        String actual = transferDetails.getTransferTypeDescription();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void test_get_status() {
        String expected = "test status";
        String actual = transferDetails.getTransferStatus();

        Assert.assertEquals(expected, actual);
    }


}
