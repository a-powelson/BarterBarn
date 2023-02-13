package com.example.barterbarn;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TradeTest {
    private static Trade trade;
    private String user1ID = "1234";
    private String user2ID = "5678";
    private String listingID = "foo123";

    @Before
    public void setUp() throws Exception {
        trade = new Trade(user1ID,user2ID,listingID);
    }

    @After
    public void tearDown() throws Exception {
        System.gc();
    }

    @Test
    public void getUser1ID() {
        assertEquals(trade.getUser1ID(),user1ID);
    }

    @Test
    public void setUser1ID() {
        trade.setUser1ID("0000");
        assertEquals(trade.getUser1ID(),"0000");
    }

    @Test
    public void getUser2ID() {
        assertEquals(trade.getUser2ID(),user2ID);
    }

    @Test
    public void setUser2ID() {
        trade.setUser2ID("0000");
        assertEquals(trade.getUser2ID(),"0000");
    }

    @Test
    public void getListingID() {
        assertEquals(trade.getListingName(),listingID);
    }

    @Test
    public void setListingID() {
        trade.setListingName("foobar");
        assertEquals(trade.getListingName(),"foobar");
    }

    @Test
    public void isTradeCompleted() {
        assertTrue(trade.isTradeCompleted());
    }

    @Test
    public void setTradeCompleted() {
        trade.setTradeCompleted(false);
        assertFalse(trade.isTradeCompleted());
    }
}