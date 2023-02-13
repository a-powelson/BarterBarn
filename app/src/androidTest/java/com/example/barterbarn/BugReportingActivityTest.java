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
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.fail;

@RunWith(AndroidJUnit4.class)
public class BugReportingActivityTest {
    @Rule
    public ActivityScenarioRule<BugReportingActivity> myRule = new ActivityScenarioRule<>(BugReportingActivity.class);

    @Before
    public void setup() {
        Intents.init();
    }

    /** UI Tests*/
    @Test
    public void completeReport() throws InterruptedException {
        onView(withId(R.id.descriptionBug)).perform(typeText("Foo Bar bug"));
        onView(withId(R.id.bugButton)).perform(click());
        Thread.sleep(3000);
    }

    @Test
    public void emptyReport() throws InterruptedException {
        onView(withId(R.id.bugButton)).perform(click());
        Thread.sleep(3000);
    }

    @After
    public void teardown() {
        Intents.release();
    }
}