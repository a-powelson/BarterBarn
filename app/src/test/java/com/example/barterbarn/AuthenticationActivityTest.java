package com.example.barterbarn;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AuthenticationActivityTest {
    private static AuthenticationActivity authenticationActivity;

    @Before
    public void setUp() {
        authenticationActivity = new AuthenticationActivity();
    }

    @Test
    public void validateFieldsTest_isValid() {
        assertTrue(authenticationActivity.validateFields("test@gmail.com", "pass"));
    }

    @Test
    public void validateFieldsTest_isInvalid() {
        assertFalse(authenticationActivity.validateFields("", ""));
    }

    @Test
    public void validateFieldsTest_invalidEmail() {
        assertFalse(authenticationActivity.validateFields("", "Pass"));
    }

    @Test
    public void validateFieldsTest_invalidPassword() {
        assertFalse(authenticationActivity.validateFields("test@gmail.com", ""));
    }

    @After
    public void tearDown() {
        System.gc();
    }
}
