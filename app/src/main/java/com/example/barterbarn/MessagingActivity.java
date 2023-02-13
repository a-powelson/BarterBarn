package com.example.barterbarn;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MessagingActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    DatabaseReference db;
    DatabaseReference currUserRef;
    DatabaseReference receiverUserRef;
    DatabaseReference convoNode;
    DatabaseReference convoReceiverNode;
    private String toUserID;
    private String fromUserID;
    private String fromUserName;
    private String toUserName;
    private String messageID;
    private String messageText;
    private MessageListAdapter messageAdapter;
    private List<ChatMessage> messagesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);
        toUserID = getIntent().getStringExtra("userID");

        messagesList = new ArrayList<>();
        messageAdapter = new MessageListAdapter(this, messagesList);
        initializeMessagesListScroll(messageAdapter);

        initializeDatabase();
        fetchReceiverName();
        fetchCurrUser();
        initializeSendButton();
        initializeToolbar(toUserName);
    }

    protected void initializeSendButton() {
        ImageButton sendButton = findViewById(R.id.sendButton);
        sendButton.setOnClickListener(v -> {
            ChatMessage cm = composeMessage();
            sendMessage(cm);
            initializeConversation();
            clearMessageField();
        });

        fetchMessages();


    }

    protected void fetchMessages(){
        convoNode.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ChatMessage message = snapshot.getValue(ChatMessage.class);
                updateMessageAdapter(message);
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

    protected void sendMessage(ChatMessage cm){
        if(cm.getMessageText().isEmpty()){
            return;
        }
        convoNode.child("message"+messageID).setValue(cm);
        ChatMessage cm2 = new ChatMessage(toUserID, fromUserID, messageID,  messageText);
        cm2.setSender(false);
        convoReceiverNode.child("message"+messageID).setValue(cm2);
    }

    protected ChatMessage composeMessage() {
        messageText = readMessageField();
        messageID = String.valueOf(System.currentTimeMillis());
        ChatMessage cm = new ChatMessage(toUserID, fromUserID, messageID,  messageText);
        return cm;
    }

    private void initializeMessagesListScroll(MessageListAdapter itemAdapter) {
        RecyclerView recyclerView = findViewById(R.id.list_of_messages);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.scrollToPosition(itemAdapter.getItemCount() - 1);
        recyclerView.setAdapter(itemAdapter);
    }

    protected void initializeDatabase() {
        mAuth = FirebaseAuth.getInstance();
        fromUserID = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        db = FirebaseDatabase.getInstance().getReference("messaging");
        currUserRef = db.child("userID-"+fromUserID);
        receiverUserRef = db.child("userID-"+toUserID);
        convoNode = currUserRef.child("convo-"+toUserID);
        convoReceiverNode = receiverUserRef.child("convo-"+fromUserID);
    }

    protected void fetchCurrUser(){
        DatabaseReference currUserNode = FirebaseDatabase.getInstance().
                getReference("users").child(fromUserID);
        currUserNode.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    User curr = snapshot.getValue(User.class);
                    fromUserName = curr.getName();
                }else{
                    fromUserName = "Anonymous Goat";
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    protected void initializeConversation(){
        UserContact receiver = new UserContact(toUserID, toUserName);
        UserContact sender = new UserContact(fromUserID, fromUserName);
        DatabaseReference userContacts = db.child("contacts");
        DatabaseReference myContact = userContacts.child(fromUserID);
        myContact.child(toUserID).setValue(receiver);
        DatabaseReference otherContact = userContacts.child(toUserID);
        otherContact.child(fromUserID).setValue(sender);
    }

    protected void updateMessageAdapter(ChatMessage cm) {
        messagesList.add(cm);
        messageAdapter = new MessageListAdapter(this, messagesList);
        initializeMessagesListScroll(messageAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.tradeButton:
                launchTrade();
                return true;
            case R.id.logoutButton:
                logoutUser();
                return true;

            case R.id.messagingButton:
                launchMessagesHome();
                return true;

            case R.id.backToChat:
                launchMessagesHome();
                return true;

            case R.id.homeButton:
                launchMainActivity();
                return true;
            case R.id.profileButton:
                launchProfileActivity();
                return true;
        }

        return false;
    }

    protected void launchTrade(){
        Intent intent = new Intent(this, FinalizeTradeActivity.class);
        intent.putExtra("userID1",fromUserID);
        intent.putExtra("userID2",toUserID);
        startActivity(intent);
    }
    protected void clearMessageField() {
        EditText input = findViewById(R.id.messageField);
        input.setText("");
    }

    protected String readMessageField() {
        TextView textField = findViewById(R.id.messageField);
        return textField.getText().toString();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.chat_menu, menu);
        return true;
    }

    private void initializeToolbar(String name) {
        Toolbar chatBar = findViewById(R.id.chatBar);
        setSupportActionBar(chatBar);
        chatBar.setTitle(name);
    }

    private void fetchReceiverName(){
        DatabaseReference userNameNode = FirebaseDatabase.getInstance()
                .getReference("users");
        userNameNode.child(toUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    User to = snapshot.getValue(User.class);
                    toUserName = to.getName();
                    initializeToolbar(toUserName);
                }else{
                    initializeToolbar("Anonymous Chicken");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    protected void logoutUser() {
        FirebaseAuth.getInstance().signOut();
        launchCreateAccountActivity();
    }

    protected void launchCreateAccountActivity() {
        Intent intent = new Intent(this, CreateAccountActivity.class);
        startActivity(intent);
    }

    protected void launchMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    protected void launchMessagesHome() {
        Intent intent = new Intent(this, MessagingHomeActivity.class);
        startActivity(intent);
    }

    private void launchProfileActivity() {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }
}