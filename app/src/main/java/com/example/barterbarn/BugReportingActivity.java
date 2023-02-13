package com.example.barterbarn;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BugReportingActivity extends AppCompatActivity {
    DatabaseReference db;
    private String userID;
    private String reportText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bug_reporting);

        initializeToolbar();
        initializeDatabase();
        initializeSubmitButton();
    }

    protected void initializeDatabase() {
        db = FirebaseDatabase.getInstance().getReference("issues");
    }

    private void initializeToolbar() {
        Toolbar tradeBar = findViewById(R.id.bugBar);
        setSupportActionBar(tradeBar);
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

    protected void initializeSubmitButton() {
        Button bugButton = findViewById(R.id.bugButton);
        bugButton.setOnClickListener(v -> {
            TextView textView = findViewById(R.id.descriptionBug);
            reportText = textView.getText().toString();

            if (reportText.length() > 0) {
                BugReport tr = new BugReport(userID, reportText);
                pushBug(tr);
            } else {
                Toast.makeText(this, "Cannot submit an empty bug report.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void pushBug(BugReport bug) {
        db.push().setValue(bug);
        clearReportField();
        Toast.makeText(this, "Bug report submitted!", Toast.LENGTH_LONG).show();
    }

    protected void clearReportField() {
        EditText input = findViewById(R.id.descriptionBug);
        input.setText("");
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
