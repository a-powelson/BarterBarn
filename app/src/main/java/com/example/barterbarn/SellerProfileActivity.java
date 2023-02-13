package com.example.barterbarn;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

public class SellerProfileActivity extends AppCompatActivity {

    private TextView nameField;
    private TextView emailField;
    private TextView dateField;
    private ImageButton returnButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_profile);

        nameField = findViewById(R.id.profileName);
        emailField = findViewById(R.id.profileEmail);
        dateField = findViewById(R.id.profileDateRegistered);

        String name = getIntent().getStringExtra("Name");
        String date = getIntent().getStringExtra("Date");
        String email = getIntent().getStringExtra("Email");

        nameField.setText(name);
        dateField.setText(date);
        emailField.setText(email);

        returnButton = findViewById(R.id.backArrow);
        returnButton.setOnClickListener(v -> onBackPressed());
    }
}