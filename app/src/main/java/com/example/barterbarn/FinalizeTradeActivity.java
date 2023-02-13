package com.example.barterbarn;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FinalizeTradeActivity extends AppCompatActivity {
    DatabaseReference db;
    private String userID1;
    private String userID2;
    private String listingName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finalize_trade);

        extractUserIdsFromIntent();
        initializeToolbar();
        initializeDatabase();
        initializeTradeButton();
    }

    protected void extractUserIdsFromIntent() {
        userID1 = getIntent().getStringExtra("userID1");
        userID2 = getIntent().getStringExtra("userID2");
    }

    protected void initializeDatabase() {
        db = FirebaseDatabase.getInstance().getReference("trades");
    }

    private void initializeToolbar() {
        Toolbar tradeBar = findViewById(R.id.tradeBar);
        setSupportActionBar(tradeBar);
        tradeBar.setTitle("Trade");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.general_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.profileButton:
                launchProfileActivity();
                return true;

            case R.id.logoutButton:
                logoutUser();
                return true;

            case R.id.backToHome:
                launchMainActivity();
                return true;
            case R.id.homeButton:
                launchMainActivity();
                return true;
        }

        return false;
    }

    protected void initializeTradeButton() {
        Button tradeButton = findViewById(R.id.tradeCompleteButton);
        tradeButton.setOnClickListener(v -> {
            TextView textView = findViewById(R.id.tradeInput);
            listingName = textView.getText().toString();

            if (listingName.length() > 0) {
                Trade tr = new Trade(userID1, userID2, listingName);
                Toast.makeText(this, "Trade is complete!", Toast.LENGTH_LONG).show();
                pushTrade(tr);
            } else {
                Toast.makeText(this, "Listing name cannot be empty.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void pushTrade(Trade trade) {
        db.push().setValue(trade);
        // Return home once the trade has been finalized
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    protected void logoutUser() {
        FirebaseAuth.getInstance().signOut();
        launchLoginActivity();
    }

    protected void launchLoginActivity() {
        Intent intent = new Intent(this, CreateAccountActivity.class);
        startActivity(intent);
    }

    protected void launchMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void launchProfileActivity() {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }
}
