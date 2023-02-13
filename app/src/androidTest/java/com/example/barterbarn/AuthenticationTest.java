package com.example.barterbarn;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.fail;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class AuthenticationTest {

    @Rule
    public ActivityScenarioRule<CreateAccountActivity> activityRule =
            new ActivityScenarioRule<>(CreateAccountActivity.class);

    @Before
    public void setup() {
        Intents.init();
    }

    @Test
    public void createValidAccount() throws InterruptedException {
        onView(withId(R.id.createAccountNameField)).perform(typeText("userName"));
        onView(withId(R.id.createAccountEmailField)).perform(typeText(getRandomEmail()));
        onView(withId(R.id.createAccountPasswordField)).perform(typeText("password"));
        onView(withId(R.id.registerButton)).perform(click());

        // Wait for up to 5 seconds for the API call to finish, checking every second if it has
        int timeout = 5;
        while (timeout-- > 0) {
            try {
                // Check if the MainActivity is showing (i.e., we successfully registered)
                intended(hasComponent(MainActivity.class.getName()));
                return;
            } catch (AssertionError ae) {
                Thread.sleep(1000);
            }
        }

        fail("Account registration failed (or Firebase took too long)");
    }

    @Test
    public void createInvalidAccount() throws InterruptedException {
        onView(withId(R.id.createAccountEmailField)).perform(typeText("notAnEmail"));
        onView(withId(R.id.createAccountPasswordField)).perform(typeText("short"));
        onView(withId(R.id.registerButton)).perform(click());

        // Wait for up to 5 seconds for the statusLabel to update
        int timeout = 5;
        while (timeout-- > 0) {
            try {
                // Check that the correct error chat_message_bubble is showing
                onView(withId(R.id.statusLabel)).check(matches(withText(R.string.invalidEmailPassword)));
                return;
            } catch (AssertionError ae) {
                Thread.sleep(1000);
            }
        }

        fail("Failed to find statusLabel in time");
    }

    @Test
    public void createAccountWithEmptyCredentials() throws InterruptedException {
        onView(withId(R.id.registerButton)).perform(click());

        // Wait for up to 5 seconds for the statusLabel to update
        int timeout = 5;
        while (timeout-- > 0) {
            try {
                // Check that the correct error chat_message_bubble is showing
                onView(withId(R.id.statusLabel)).check(matches(withText(R.string.emptyEmailPassword)));
                return;
            } catch (AssertionError ae) {
                Thread.sleep(1000);
            }
        }

        fail("Failed to find statusLabel in time");
    }

    @Test
    public void validLogin() throws InterruptedException {
        onView(withId(R.id.loginLinkButton)).perform(click());
        onView(withId(R.id.loginEmailField)).perform(typeText("doNotDelete@email.com"));
        onView(withId(R.id.loginPasswordField)).perform(typeText("password"));
        onView(withId(R.id.loginButton)).perform(click());

        // Wait for up to 5 seconds for the API call to finish, checking every second if it has
        int timeout = 5;
        while (timeout-- > 0) {
            try {
                // Check if the MainActivity is showing (i.e., we successfully registered)
                intended(hasComponent(MainActivity.class.getName()));
                return;
            } catch (AssertionError ae) {
                Thread.sleep(1000);
            }
        }

        fail("Login failed (or Firebase took too long)");
    }

    @Test
    public void invalidLogin() throws InterruptedException{
        onView(withId(R.id.loginLinkButton)).perform(click());
        onView(withId(R.id.loginEmailField)).perform(typeText("test bad email"));
        onView(withId(R.id.loginPasswordField)).perform(typeText("password"));
        onView(withId(R.id.loginButton)).perform(click());

        // Wait for up to 5 seconds for the statusLabel to update
        int timeout = 5;
        while (timeout-- > 0) {
            try {
                // Check that the correct error chat_message_bubble is showing
                onView(withId(R.id.statusLabel)).check(matches(withText(R.string.invalidEmailPassword)));
                return;
            } catch (AssertionError ae) {
                Thread.sleep(1000);
            }
        }
    }

    @Test
    public void logOut() throws InterruptedException {
        onView(withId(R.id.loginLinkButton)).perform(click());
        onView(withId(R.id.loginEmailField)).perform(typeText("doNotDelete@email.com"));
        onView(withId(R.id.loginPasswordField)).perform(typeText("password"));
        onView(withId(R.id.loginButton)).perform(click());

        // Wait for up to 5 seconds for the API call to finish, checking every second if it has
        int timeout = 5;
        while (timeout-- > 0) {
            try {
                // Open the options menu OR open the overflow menu, depending on whether
                // the device has a hardware or software overflow menu button.
                openActionBarOverflowOrOptionsMenu(
                        ApplicationProvider.getApplicationContext());

                // Click the item.
                onView(withText("Logout"))
                        .perform(click());

                // Check if returned to CreateAccount page
                intended(hasComponent(CreateAccountActivity.class.getName()));
                return;

            } catch (NoMatchingViewException e) {
                // View is not in hierarchy
                Thread.sleep(1000);
            }
        }

        fail("Log out failed");
    }

    @Test
    public void emptyLogin() throws InterruptedException{
        onView(withId(R.id.loginLinkButton)).perform(click());
        onView(withId(R.id.loginButton)).perform(click());

        // Wait for up to 5 seconds for the statusLabel to update
        int timeout = 5;
        while (timeout-- > 0) {
            try {
                // Check that the correct error chat_message_bubble is showing
                onView(withId(R.id.statusLabel)).check(matches(withText(R.string.emptyEmailPassword)));
                return;
            } catch (AssertionError ae) {
                Thread.sleep(1000);
            }
        }
    }

    @After
    public void teardown() {
        Intents.release();
    }

    private String getRandomEmail() {
        final String OPTIONS = "abcdefghijklmnopqrstuvwxyz1234567890";
        final String SUFFIX = "@gmail.com";

        Random random = new Random();
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < 8; i++) {
            char randomChar = OPTIONS.charAt(random.nextInt(OPTIONS.length()));
            result.append(randomChar);
        }

        return result.toString() + SUFFIX;
    }
}