package com.example.barterbarn;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MessagingHomeActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private String userID;
    private DatabaseReference dbRef;
    private DatabaseReference userContacts;
    private List<Conversation> convoList;
    private MessagePreviewAdapter mPrevadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging_home);

        convoList = new ArrayList<>();
        mPrevadapter = new MessagePreviewAdapter(this, convoList);
        initializeMessagesListScroll(mPrevadapter);

        initializeToolbar();
        initializeDatabase();
        getConversations();
        alertUserMessages();

    }

    private void initializeMessagesListScroll(MessagePreviewAdapter itemAdapter) {
        RecyclerView recyclerView = findViewById(R.id.list_of_chats);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(itemAdapter);
    }

    private void initializeDatabase() {
        mAuth = FirebaseAuth.getInstance();
        userID = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        dbRef = FirebaseDatabase.getInstance().getReference("messaging");
        userContacts = dbRef.child("contacts").child(userID);
    }

    private void getConversations() {
        userContacts.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if(snapshot.exists()){
                    Conversation convo = snapshot.getValue(Conversation.class);
                    updateConvoList(convo);
                }
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

    private void updateConvoList(Conversation c) {
        convoList.add(c);
        mPrevadapter = new MessagePreviewAdapter(this, convoList);
        initializeMessagesListScroll(mPrevadapter);
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

    private void initializeToolbar() {
        Toolbar chatBar = findViewById(R.id.chatBarHome);
        setSupportActionBar(chatBar);
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
    protected String getName(String userID){
        for(int i = 0; i<convoList.size(); i++){
            if(convoList.get(i).getUserID().equals(userID)){
                return convoList.get(i).getUserName();
            }
        }
        return "Anonymous Chicken";
    }


    public void alertUserMessages() {
        DatabaseReference messagesRef = dbRef.child("userID-" + userID);
        messagesRef.addChildEventListener(new ChildEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String recentConvoID = snapshot.getKey();
                messagesRef.child(recentConvoID).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        ChatMessage cm = snapshot.getValue(ChatMessage.class);
                        if (!cm.getSender()) {
                            Intent intent = new Intent(MessagingHomeActivity.this, MessagingActivity.class);
                            intent.putExtra("userID", cm.getFromUser());
                            PendingIntent pendingIntent = PendingIntent.getActivity(MessagingHomeActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                            String id = "channel_1";
                            String description = "123";
                            int importance = NotificationManager.IMPORTANCE_LOW;
                            NotificationChannel channel = new NotificationChannel(id, "123", importance);
                            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                            manager.createNotificationChannel(channel);
                            Notification notification = new Notification.Builder(MessagingHomeActivity.this, id)
                                    .setCategory(Notification.CATEGORY_MESSAGE)
                                    .setSmallIcon(R.mipmap.ic_launcher)
                                    .setContentTitle("New Message on Barter Barn!")
                                    .setContentText(getName(cm.getFromUser()) + ": " + cm.getMessageText())
                                    .setContentIntent(pendingIntent)
                                    .setAutoCancel(true)
                                    .build();
                            manager.notify(1, notification);
                        }
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
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String recentConvoID = snapshot.getKey();
                messagesRef.child(recentConvoID).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        ChatMessage cm = snapshot.getValue(ChatMessage.class);
                        if (!cm.getSender()) {
                            Intent intent = new Intent(MessagingHomeActivity.this, MessagingActivity.class);
                            intent.putExtra("userID",cm.getFromUser());
                            PendingIntent pendingIntent = PendingIntent.getActivity(MessagingHomeActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                            String id = "channel_1";
                            String description = "123";
                            int importance = NotificationManager.IMPORTANCE_LOW;
                            NotificationChannel channel = new NotificationChannel(id, "123", importance);
                            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                            manager.createNotificationChannel(channel);
                            Notification notification = new Notification.Builder(MessagingHomeActivity.this, id)
                                    .setCategory(Notification.CATEGORY_MESSAGE)
                                    .setSmallIcon(R.mipmap.ic_launcher)
                                    .setContentTitle("New Message on Barter Barn!")
                                    .setContentText(getName(cm.getFromUser()) + ": " + cm.getMessageText())
                                    .setContentIntent(pendingIntent)
                                    .setAutoCancel(true)
                                    .build();
                            manager.notify(1, notification);
                        }
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