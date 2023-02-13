package com.example.barterbarn;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class ItemAdapterTest {

    private static ArrayList<Item> itemList;
    private static ItemAdapter itemAdapter;

    @Before
    public void setUp() {
        itemList = new ArrayList<>();

        itemList.add(new Item());
        itemList.add(new Item());
        itemList.add(new Item());
        itemList.add(new Item());
        itemList.add(new Item());
        itemList.add(new Item());
        itemList.add(new Item());
        itemList.add(new Item());
        itemList.add(new Item());

        itemAdapter = new ItemAdapter(itemList);
    }

    @Test
    public void getItemCount() {

        assertEquals(itemAdapter.getItemCount(), itemList.size());
    }

    @After
    public void tearDown() {
        System.gc();
    }
}
