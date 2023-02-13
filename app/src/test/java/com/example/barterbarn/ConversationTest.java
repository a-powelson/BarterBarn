package com.example.barterbarn;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ConversationTest {
    private static Conversation conversation;
    private String userID = "CSCI3130SWE2021";
    private String userName = "Android Tester";

    @Before
    public void setUp() throws Exception {
        conversation = new Conversation(userID, userName);
    }

    @After
    public void tearDown() throws Exception {
        System.gc();
    }

    @Test
    public void getUserID() {
        assertEquals(conversation.getUserID(), userID);
    }

    @Test
    public void getUserName() {
        assertEquals(conversation.getUserName(), userName);
    }

    @Test
    public void setUserID() {
        conversation.setUserID("1234TESTING");
        assertEquals(conversation.getUserID(),"1234TESTING");
    }

    @Test
    public void setUserName() {
        conversation.setUserName("Steve Jobs");
        assertEquals(conversation.getUserName(),"Steve Jobs");
    }
}