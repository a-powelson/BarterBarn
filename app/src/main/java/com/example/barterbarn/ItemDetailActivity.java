package com.example.barterbarn;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;

public class ItemDetailActivity extends AppCompatActivity implements View.OnClickListener {
    static String sellerNameContent;
    ImageButton returnButton;
    TextView title;
    TextView location;
    TextView date;
    TextView detail;
    ImageView image;
    TextView contactName;
    String sellerUserID;
    ImageButton sendSeller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        title = findViewById(R.id.title);
        location = findViewById(R.id.location);
        date = findViewById(R.id.date);
        detail = findViewById(R.id.detail);
        image = findViewById(R.id.image);
        contactName = findViewById(R.id.sellerName);

        String titleContent = getIntent().getStringExtra("Title");
        title.setText(titleContent);

        String locationContent = getIntent().getStringExtra("Location");
        location.setText(locationContent);

        String dateContent = getIntent().getStringExtra("Date");
        date.setText(dateContent);

        sellerNameContent = getIntent().getStringExtra("Seller");
        contactName.setText(sellerNameContent);

        sellerUserID = getIntent().getStringExtra("SellerID");

        String detailContent = getIntent().getStringExtra("Detail");
        detail.setText(detailContent);

        String imageContent = getIntent().getStringExtra("Image");
        try {
            URL realUrl = new URL(imageContent);
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(inputStream);
            image.setImageBitmap(myBitmap);
        } catch (IOException e) {
            Log.i("loadPicture", "Profile doesn't have picture");
        }

        returnButton = findViewById(R.id.backArrow);
        returnButton.setOnClickListener(v -> onBackPressed());

        sendSeller = findViewById(R.id.senMessageBtn);
        sendSeller.setOnClickListener(v -> launchChatMessaging());
        contactSeller();
    }

    protected void launchChatMessaging() {
        Intent intent = new Intent(this, MessagingActivity.class);
        intent.putExtra("userID", sellerUserID);
        startActivity(intent);

        contactSeller();
    }

    private void contactSeller() {
        contactName = findViewById(R.id.sellerName);
        contactName.setOnClickListener(this);
    }

    private void launchProfileActivity() {
        Intent intent = new Intent(this, SellerProfileActivity.class);

        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("users");
        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                User user = snapshot.getValue(User.class);
                if (user.getName().equals(sellerNameContent)) {
                    intent.putExtra("Name", user.getName());
                    intent.putExtra("Email", user.getEmail());
                    intent.putExtra("Date", new SimpleDateFormat("yyyy-MM-dd").format(user.getDateRegistered()));
                    startActivity(intent);
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
    public void onClick(View v) {
        launchProfileActivity();
    }
}
