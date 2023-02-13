package com.example.barterbarn;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

public class ChatMessageTest {
    private static ChatMessage chatMessage;
    private String messageText = "Hello, World!";
    private String toUser = "Steve Jobs";
    private String fromUser = "John Smith";
    private String messageID = "XY-1001";

    @Before
    public void setUp() throws Exception {
        chatMessage = new ChatMessage(toUser, fromUser, messageID, messageText);
    }

    @After
    public void tearDown() throws Exception {
        System.gc();
    }

    @Test
    public void getMessageText() {
        assertEquals(chatMessage.getMessageText(), messageText);
    }

    @Test
    public void setMessageText() {
        chatMessage.setMessageText("Hello, Steve!");
        assertEquals(chatMessage.getMessageText(), "Hello, Steve!");
    }

    @Test
    public void getToUser() {
        assertEquals(chatMessage.getToUser(), toUser);
    }

    @Test
    public void setToUser() {
        chatMessage.setToUser("Ava");
        assertEquals(chatMessage.getToUser(), "Ava");
    }

    @Test
    public void getFromUser() {
        assertEquals(chatMessage.getFromUser(), fromUser);
    }

    @Test
    public void setFromUser() {
        chatMessage.setToUser("Keelin");
        assertEquals(chatMessage.getToUser(), "Keelin");
    }
    
    @Test
    public void getMessageID() {
        assertEquals(chatMessage.getMessageID(), messageID);
    }

    @Test
    public void setMessageID() {
        chatMessage.setMessageID("KSB-123");
        assertEquals(chatMessage.getMessageID(), "KSB-123");
    }

    @Test
    public void setSender(){
        chatMessage.setSender(false);
        assertFalse(chatMessage.getSender());
    }

    @Test
    public void getSender(){
        assertTrue(chatMessage.getSender());
    }
}