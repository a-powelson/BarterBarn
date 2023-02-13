package com.example.barterbarn;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class AuthenticationActivity extends AppCompatActivity {

    protected FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void initializeButton(int viewId, Runnable onClick) {
        View button = findViewById(viewId);
        button.setOnClickListener(v -> onClick.run());
    }

    protected String readTextFieldWithId(int viewId) {
        TextView textField = findViewById(viewId);
        return textField.getText().toString();
    }

    protected boolean validateFields(String email, String password) {
        return email.length() != 0 && password.length() != 0;
    }

    protected boolean validateFields(String name, String email, String password) {
        return name.length() != 0 && validateFields(email, password);
    }

    protected void updateStatusLabel(int viewId) {
        updateStatusLabel(getString(viewId));
    }

    protected void updateStatusLabel(String message) {
        TextView statusLabel = findViewById(R.id.statusLabel);
        statusLabel.setText(message);
    }

    protected void launchMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    protected void launchLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    protected void launchCreateAccountActivity() {
        Intent intent = new Intent(this, CreateAccountActivity.class);
        startActivity(intent);
    }
}