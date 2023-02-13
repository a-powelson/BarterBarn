package com.example.barterbarn;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.fail;

@RunWith(AndroidJUnit4.class)
public class FinalizeTradeTest {
    @Rule
    public ActivityScenarioRule<FinalizeTradeActivity> myRule = new ActivityScenarioRule<>(FinalizeTradeActivity.class);

    @Before
    public void setup() {
        Intents.init();
    }

    /** UI Tests*/
    @Test
    public void completeTrade() throws InterruptedException {
        onView(withId(R.id.tradeInput)).perform(typeText("Foo Bar Tester"));
        onView(withId(R.id.tradeCompleteButton)).perform(click());
        Thread.sleep(3000);
    }

    @Test
    public void emptyTrade() throws InterruptedException {
        onView(withId(R.id.tradeCompleteButton)).perform(click());
        Thread.sleep(3000);
    }

    @After
    public void teardown() {
        Intents.release();
    }
}