package com.example.barterbarn;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class ItemTest {

    private static ArrayList<Item> itemList;

    @Before
    public void setUp() {
        itemList = new ArrayList<>();

        itemList.add(new Item("1", "Apple", "An apple", "Frank", "Halifax", "",""));
        itemList.add(new Item("2", "Pear", "A pair of pears", "Jack", "Dartmouth", "",""));
    }

    @Test
    public void getItemTitle() {
        assertEquals(itemList.get(0).getTitle(), "Apple");
    }

    @Test
    public void getItemLocation() {
        assertEquals(itemList.get(0).getLocation(), "Halifax");
    }

    @Test
    public void setItemTitle() {
        itemList.get(0).setTitle("iPhone 12 Mini");
        assertEquals(itemList.get(0).getTitle(), "iPhone 12 Mini");
    }

    @After
    public void tearDown() {
        System.gc();
    }
}
