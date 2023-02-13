package com.example.barterbarn;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AuthenticationActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setActivityTitle();
        connectToFirebase();
        initializeLoginButton();
        initializeRegisterButton();
    }

    private void setActivityTitle() {
        setTitle("Login");
    }

    private void connectToFirebase() {
        mAuth = FirebaseAuth.getInstance();
    }

    private void initializeLoginButton() {
        initializeButton(R.id.loginButton, this::loginUser);
    }

    private void initializeRegisterButton() {
        initializeButton(R.id.createAccountLinkButton, this::launchCreateAccountActivity);
    }

    protected void loginUser() {
        // Read the values the user entered
        String email = readEmailField();
        String password = readPasswordField();

        if (validateFields(email, password)) {
            // Attempt to login
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(loginAttempt -> {
                        if (loginAttempt.isSuccessful()) {
                            launchMainActivity();
                        } else {
                            updateStatusLabel(R.string.incorrectEmailPassword);
                        }
                    });
        } else {
            updateStatusLabel(R.string.emptyEmailPassword);
        }
    }

    private String readEmailField() {
        return readTextFieldWithId(R.id.loginEmailField);
    }

    private String readPasswordField() {
        return readTextFieldWithId(R.id.loginPasswordField);
    }
}