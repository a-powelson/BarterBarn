package com.example.barterbarn;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class LocationPermissionTest {

    @Rule
    public ActivityScenarioRule<LoginActivity> myRule = new ActivityScenarioRule<>(LoginActivity.class);

    @BeforeClass
    public static void setup() {
        Intents.init();
    }

    @Test
    public void checkPermission() throws InterruptedException {
        onView(withId(R.id.loginEmailField)).perform((typeText("testLocation@dal.ca")));
        onView(withId(R.id.loginPasswordField)).perform((typeText("123456")));
        onView(withId(R.id.loginButton)).perform(click());
        Thread.sleep(3000);
        onView(withId(R.id.locationStatusLabel)).check(matches(withText(R.string.locationPermissionGranted)));
    }
}

