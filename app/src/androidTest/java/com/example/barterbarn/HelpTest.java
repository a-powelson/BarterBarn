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
public class HelpTest {

    @Rule
    public ActivityScenarioRule<HelpDocumentationActivity> myRule = new ActivityScenarioRule<>(HelpDocumentationActivity.class);

    @Before
    public void setup() {
        Intents.init();
    }

    @Test
    public void accountHelpButtonTest() throws InterruptedException {
        onView(withId(R.id.help_account_button)).perform(click());
        Thread.sleep(3000);
    }

    @Test
    public void searchHelpButtonTest() throws InterruptedException {
        onView(withId(R.id.help_search_button)).perform(click());
        Thread.sleep(3000);
    }

    @Test
    public void editHelpButtonTest() throws InterruptedException {
        onView(withId(R.id.help_account_button)).perform(click());
        Thread.sleep(3000);
    }

    @Test
    public void messageHelpButtonTest() throws InterruptedException {
        onView(withId(R.id.help_message_button)).perform(click());
        Thread.sleep(3000);
    }

    @After
    public void teardown() {
        Intents.release();
    }


}