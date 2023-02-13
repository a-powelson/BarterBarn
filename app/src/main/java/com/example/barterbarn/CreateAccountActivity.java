package com.example.barterbarn;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class CreateAccountActivity extends AuthenticationActivity {

    DatabaseReference databaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        setActivityTitle();
        connectToFirebase();
        initializeRegisterButton();
        initializeLoginButton();
    }

    private void setActivityTitle() {
        setTitle("Create Account");
    }

    private void connectToFirebase() {
        mAuth = FirebaseAuth.getInstance();
        databaseRef = FirebaseDatabase.getInstance().getReference();
    }

    private void initializeRegisterButton() {
        initializeButton(R.id.registerButton, this::registerUser);
    }

    private void initializeLoginButton() {
        initializeButton(R.id.loginLinkButton, this::launchLoginActivity);
    }

    protected void registerUser() {
        // Read the values the user entered
        String name = readNameField();
        String email = readEmailField();
        String password = readPasswordField();

        if (validateFields(name, email, password)) {
            // Attempt to register a new user
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(accountCreation -> {
                        if (accountCreation.isSuccessful()) {
                            String userId = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
                            DatabaseReference userRef = databaseRef.child("users/" + userId);

                            User newUser = new User(name, "", email);
                            userRef.setValue(newUser);

                            launchMainActivity();
                        } else {
                            updateStatusLabel(R.string.invalidEmailPassword);
                        }
                    });
        } else {
            updateStatusLabel(R.string.emptyEmailPassword);
        }
    }

    private String readNameField() {
        return readTextFieldWithId(R.id.createAccountNameField);
    }

    private String readEmailField() {
        return readTextFieldWithId(R.id.createAccountEmailField);
    }

    private String readPasswordField() {
        return readTextFieldWithId(R.id.createAccountPasswordField);
    }
}