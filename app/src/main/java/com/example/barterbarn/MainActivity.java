package com.example.barterbarn;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final FirebaseDatabase db = FirebaseDatabase.getInstance();
    private final DatabaseReference root = db.getReference().child("listings");
    private final ArrayList<Item> realData = new ArrayList<>();
    private FirebaseAuth mAuth;
    private ItemAdapter itemAdapter;
    private PermissionHandler permissionHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_main);

        connectToFirebase();
        initializeToolbar();
        alertUser();

        FloatingActionButton addNewItem = findViewById(R.id.floating_button);
        addNewItem.setOnClickListener(this);

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                realData.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Item items = dataSnapshot.getValue(Item.class);
                    realData.add(items);
                }

                initializeItemAdapter(realData);
                initializeScrollableList();
                itemAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    @Override
    public void onClick(View view) {
        switchListingWindow();
    }

    protected void switchListingWindow() {
        Intent intent = new Intent(MainActivity.this, ListingPage.class);
        startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onStart() {
        super.onStart();

        if (userIsSignedIn()) {
            checkLocationPermission();
            alertUser();
        } else {
            launchCreateAccountActivity();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.reportBugButton:
                reportBug();
                return true;
            case R.id.logoutButton:
                // User chose to logout
                logoutUser();
                return true;

            case R.id.messagingButton:
                // switch to messaging activity
                launchMessageActivity();

                // Switch to messaging activity
                return true;

            case R.id.profileButton:
                launchProfileActivity();
                return true;

            case R.id.helpButton:
                launchHelpActivity();
                return true;
        }

        // Somehow chose an unavailable option
        return false;
    }

    private void launchProfileActivity() {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    private void connectToFirebase() {
        mAuth = FirebaseAuth.getInstance();
    }

    protected void launchMessageActivity() {
        Intent intent = new Intent(this, MessagingHomeActivity.class);
        startActivity(intent);
    }

    protected void launchHelpActivity() {
        Intent intent = new Intent(this, HelpDocumentationActivity.class);
        startActivity(intent);
    }

    protected void reportBug() {
        Intent intent = new Intent(this, BugReportingActivity.class);
        startActivity(intent);
    }

    protected void logoutUser() {
        FirebaseAuth.getInstance().signOut();
        launchCreateAccountActivity();
    }

    private void initializeToolbar() {
        Toolbar topAppBar = findViewById(R.id.topAppBar);
        setSupportActionBar(topAppBar);
    }

    private void initializeItemAdapter(ArrayList<Item> items) {
        itemAdapter = new ItemAdapter(items);
    }

    private void initializeScrollableList() {
        RecyclerView recyclerView = findViewById(R.id.Search_Item);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(itemAdapter);

        itemAdapter.setOnItemClickListener(position -> {
            long date = itemAdapter.getFilteredResults().get(position).getDatePosted();
            Intent intent = new Intent(MainActivity.this, ItemDetailActivity.class);
            intent.putExtra("Title", itemAdapter.getFilteredResults().get(position).getTitle());
            intent.putExtra("Location", itemAdapter.getFilteredResults().get(position).getLocation());
            intent.putExtra("Date", new SimpleDateFormat("yyyy-MM-dd").format(new Date(date)));
            intent.putExtra("Seller", itemAdapter.getFilteredResults().get(position).getContactName());
            intent.putExtra("Detail", itemAdapter.getFilteredResults().get(position).getDescription());
            intent.putExtra("Image", itemAdapter.getFilteredResults().get(position).getmImageUrl());
            intent.putExtra("SellerID", itemAdapter.getFilteredResults().get(position).getSellerId());
            startActivity(intent);
        });
    }

    private boolean userIsSignedIn() {
        return mAuth.getCurrentUser() != null;
    }

    private void checkLocationPermission() {
        permissionHandler = new PermissionHandler(this);
        permissionHandler.requestLocationPermissionIfNotGranted();
    }

    protected void launchCreateAccountActivity() {
        Intent intent = new Intent(this, CreateAccountActivity.class);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionHandler.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    // Search bar component in the toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        TextView noSearchResultsFound = findViewById(R.id.notFoundText);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (itemAdapter.getItemCount() == 0) {
                    noSearchResultsFound.setVisibility(View.VISIBLE);
                }

                return false;
            }

            // While the user is typing, hide the "No results found" chat_message_bubble
            @Override
            public boolean onQueryTextChange(String newSearchQuery) {
                itemAdapter.getFilter().filter(newSearchQuery);
                noSearchResultsFound.setVisibility(View.INVISIBLE);

                return false;
            }
        });

        return true;
    }

    public void alertUser() {
        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("listings");
        mRef.addChildEventListener(new ChildEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Item item = snapshot.getValue(Item.class);
                Intent intent = new Intent(MainActivity.this, ItemDetailActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                String id = "channel_1";
                String description = "123";
                int importance = NotificationManager.IMPORTANCE_LOW;
                NotificationChannel channel = new NotificationChannel(id, description, importance);
                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                manager.createNotificationChannel(channel);
                Notification notification = new Notification.Builder(MainActivity.this, id)
                        .setCategory(Notification.CATEGORY_MESSAGE)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("There is a new item posted nearby!")
                        .setContentText("Title: " + item.getTitle() + ".      Description: " + item.getDescription())
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .build();
                manager.notify(1, notification);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}
