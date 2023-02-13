package com.example.barterbarn;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class MessageListAdapterTest {

    private static ArrayList<ChatMessage> messageList;
    private static MessageListAdapter messageListAdapter;
    private MessagingActivity MessagingActivity;


    @Before
    public void setup() {
         messageList = new ArrayList<ChatMessage>();

        ChatMessage message = new ChatMessage("John", "Chloe", "123456", "Hello");
        ChatMessage message2 = new ChatMessage("Chloe", "John", "123457", "Hi");
        ChatMessage message3 = new ChatMessage("John", "Chloe", "123458", "Hey");

        messageList.add(message);
        messageList.add(message2);
        messageList.add(message3);

        messageListAdapter = new MessageListAdapter(MessagingActivity, messageList);
    }

    @Test
    public void getItemCount() {
        assertEquals(messageListAdapter.getItemCount(), messageList.size());
    }

    @Test
    public void getViewType() {
        assertEquals(messageListAdapter.getItemViewType(0), 1);
    }

}
