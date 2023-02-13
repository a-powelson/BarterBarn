package com.example.barterbarn;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {

    private String email;
    private String userId;
    private String fullName;
    private Date dateRegistered;
    private DatabaseReference database;
    private TextView nameField;
    private TextView emailField;
    private TextView dateField;
    private TextView tradesCompleted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        connectToFirebase();
        initializeToolbar();
        initializeFields();
        initializeSaveButton();
        fetchUserInformation();
    }

    private void connectToFirebase() {
        database = FirebaseDatabase.getInstance().getReference();
        userId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
    }

    private void initializeToolbar() {
        Toolbar toolbar = findViewById(R.id.profileToolbar);
        toolbar.setTitle(R.string.profile);
        setSupportActionBar(toolbar);

        // Add back arrow to the toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void initializeFields() {
        nameField = findViewById(R.id.profileName);
        emailField = findViewById(R.id.profileEmail);
        dateField = findViewById(R.id.profileDateRegistered);
        tradesCompleted = findViewById(R.id.tradesCompleted);
    }

    private void initializeSaveButton() {
        Button editButton = findViewById(R.id.profileSaveButton);
        editButton.setOnClickListener(v -> saveChanges());
    }

    private void saveChanges() {
        String nameValue = nameField.getText().toString();
        DatabaseReference userNameRef = database.child("users").child(userId).child("name");
        userNameRef.setValue(nameValue);

        Toast.makeText(this, "Name saved!", Toast.LENGTH_SHORT).show();
    }

    private void fetchUserInformation() {
        DatabaseReference userRef = database.child("users").child(userId);
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);

                if (user != null) {
                    email = user.getEmail();
                    fullName = user.getName();
                    dateRegistered = new Date(user.getDateRegistered());

                    initializeInformationFields();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        DatabaseReference tradesRef = database.child("trades");
        tradesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int numberOfTradesCompleted = 0;

                for (DataSnapshot child : snapshot.getChildren()) {
                    Trade trade = child.getValue(Trade.class);

                    if (trade == null) {
                        continue;
                    }

                    if (trade.getUser1ID().equals(userId) || trade.getUser2ID().equals(userId)) {
                        numberOfTradesCompleted++;
                    }
                }

                updateTradesCompleted(numberOfTradesCompleted);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void updateTradesCompleted(int numberOfTradesCompleted) {
        if (numberOfTradesCompleted == 0) {
            return;
        }

        String text = numberOfTradesCompleted + " trades completed.";
        tradesCompleted.setText(text);
    }

    private void initializeInformationFields() {
        nameField.setText(fullName);
        emailField.setText(email);

        Format formatter = new SimpleDateFormat("MMMM, dd yyyy");
        String dateString = formatter.format(dateRegistered);
        dateField.setText(dateString);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (backArrowClicked(item)) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean backArrowClicked(MenuItem item) {
        return item.getItemId() == android.R.id.home;
    }
}
