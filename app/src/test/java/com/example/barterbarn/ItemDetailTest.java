package com.example.barterbarn;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class ItemDetailTest {

    private static Item item1;

    @Before
    public void setUp() {
        item1 = new Item("TSNFU4DAWFD2E", "Apple", "apple", "Hunter", "Halifax", "","");

    }

    @Test
    public void titleTest() {
        assertEquals(item1.getTitle(), "Apple");
    }

    @Test
    public void datePostedTest() {
        long dateLong = item1.getDatePosted();
        String formatDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date(dateLong));
        assertEquals(formatDate, new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis())));
    }

    @Test
    public void descriptionTest() {
        assertEquals(item1.getDescription(), "apple");
    }


    @After
    public void tearDown() {
        System.gc();
    }
}
