package com.techelevator;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class TransferDtoTests {

    private TransferDTO transferDTO;

    @Before
    public void setup() {
        transferDTO = new TransferDTO();
        transferDTO.setTransferId(3002L);
        transferDTO.setUserFromId(1001L);
        transferDTO.setUserToId(1002L);
        transferDTO.setTransferAmount(new BigDecimal("25.00"));
    }

    @Test
    public void test_get_transferDTO_amount() {
        BigDecimal expected = new BigDecimal("25.00");
        BigDecimal actual = transferDTO.getTransferAmount();

        Assert.assertEquals(expected,actual);
    }

    @Test
    public void test_get_transfer_id() {
        long expected = 3002L;
        long actual = transferDTO.getTransferId();

        Assert.assertEquals(expected,actual);
    }

    @Test
    public void test_get_user_from_id() {
        long expected = 1001L;
        long actual = transferDTO.getUserFromId();

        Assert.assertEquals(expected,actual);
    }


    @Test
    public void test_get_user_to_id() {
        long expected = 1002L;
        long actual = transferDTO.getUserToId();

        Assert.assertEquals(expected,actual);
    }


}
