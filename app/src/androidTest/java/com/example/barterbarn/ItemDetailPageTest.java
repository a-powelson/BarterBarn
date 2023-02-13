package com.example.barterbarn;

import androidx.test.espresso.action.ViewActions;
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
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class ItemDetailPageTest {

    @Rule
    public ActivityScenarioRule<ItemDetailActivity> myRule = new ActivityScenarioRule<>(ItemDetailActivity.class);

    @Before
    public void setup() {
        Intents.init();
    }

    @Test
    public void checkReturnBtn() {
        onView(withId(R.id.backArrow)).perform(click());
    }

    @Test
    public void checkSenMessageBtn(){
        onView(withId(R.id.senMessageBtn)).perform(click());
    }

    @Test
    public void scrollDown() {
        onView(withId(R.id.itemDetailScroll)).perform(ViewActions.swipeUp());
    }

    @After
    public void teardown() {
        Intents.release();
    }

}
