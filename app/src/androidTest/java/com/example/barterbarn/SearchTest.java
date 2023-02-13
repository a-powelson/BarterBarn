package com.example.barterbarn;

import android.view.KeyEvent;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class SearchTest {

    @Rule
    public ActivityScenarioRule<MainActivity> myRule = new ActivityScenarioRule<>(MainActivity.class);

    @BeforeClass
    public static void setup() {
        Intents.init();
    }

    @Test
    public void checkSearchFunction() {
        onView(withId(R.id.search)).perform(click());
        onView(withId(R.id.search)).perform(click());
    }

    @Test
    public void checkWholeTextSearch() {
        onView(withId(R.id.search)).perform(click());
        onView(withId(R.id.search)).perform(typeText("Apple"));
        onView(withId(R.id.search)).perform(click());
    }

    @Test
    public void checkIncompleteTextSearch() {
        onView(withId(R.id.search)).perform(click());
        onView(withId(R.id.search)).perform(typeText("pe"));
        onView(withId(R.id.search)).perform(click());
    }

    @Test
    public void checkUppercaseSearch() {
        onView(withId(R.id.search)).perform(click());
        onView(withId(R.id.search)).perform(typeText("ORANGE"));
        onView(withId(R.id.search)).perform(click());
    }

    @Test
    public void checkTrimSearch() {
        onView(withId(R.id.search)).perform(click());
        onView(withId(R.id.search)).perform(typeText("         Apple"));
        onView(withId(R.id.search)).perform(click());
    }

    @Test
    public void checkNoResultSearch() {
        onView(withId(R.id.search)).perform(click());
        onView(withId(R.id.search)).perform(typeText("Abble"));
        onView(withId(R.id.search)).perform(ViewActions.pressKey(KeyEvent.KEYCODE_ENTER));
    }
}