package com.example.barterbarn;

import android.content.ComponentName;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.fail;

@RunWith(AndroidJUnit4.class)
public class MessagingTest {

    @Rule
    public ActivityScenarioRule<MessagingActivity> myRule = new ActivityScenarioRule<>(MessagingActivity.class);

    @Before
    public void setup() {
        Intents.init();

        ChatMessage message = new ChatMessage("John", "Chloe", "123456", "Hello");
        ChatMessage message2 = new ChatMessage("John", "Chloe", "123456", "Hi");
        ChatMessage message3 = new ChatMessage("John", "Chloe", "123456", "Hey");

        List<ChatMessage> messagesList = new ArrayList<>();
        messagesList.add(message);
        messagesList.add(message2);
        messagesList.add(message3);

        //MessageListAdapter msgListAdapter = new MessageListAdapter(MessagingActivity, messagesList);

    }


    @Test
    public void sendMessageTest() throws InterruptedException {
        onView(withId(R.id.messageField)).perform(typeText("Hello"));
        onView(withId(R.id.sendButton)).perform(click());
        Thread.sleep(3000);
    }

    @Test
    public void sendMessageTestNoText() throws InterruptedException {
        onView(withId(R.id.sendButton)).perform(click());
        Thread.sleep(3000);
    }

    @After
    public void teardown() {
        Intents.release();
    }


}

