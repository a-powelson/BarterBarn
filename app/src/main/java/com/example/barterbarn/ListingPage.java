package com.example.barterbarn;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class ListingPage extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    EditText contactName;
    EditText goodsName;
    EditText description;
    EditText location;
    ImageView imageView;
    ProgressBar progressBar;
    Uri imageUri;

    String sellerId;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference databaseRef;
    DatabaseReference listingsRef;

    DatabaseReference imageRef;
    StorageReference storageReference;
    private StorageTask mUploadTask;
    int totalItem = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listingpage);

        goodsName = findViewById(R.id.goodsName);
        contactName = findViewById(R.id.contactName);
        description = findViewById(R.id.description);
        location = findViewById(R.id.Item_Location);
        imageView = findViewById(R.id.imageView3);
        progressBar = findViewById(R.id.progressBar);


        progressBar.setVisibility(View.INVISIBLE);

        database = FirebaseDatabase.getInstance();
        databaseRef = database.getReference();

        mAuth = FirebaseAuth.getInstance();
        sellerId = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();

        storageReference = FirebaseStorage.getInstance().getReference("listings");
        imageRef = FirebaseDatabase.getInstance().getReference("listings");


        Button uploadImage = findViewById(R.id.Image_Upload);
        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        Button submit = findViewById(R.id.button2);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //insertItems();
                if (imageUri !=null){
                    if (mUploadTask!=null && mUploadTask.isInProgress()){
                        Toast.makeText(ListingPage.this, "Upload in Progress", Toast.LENGTH_SHORT).show();
                    }else {
                        //insertItems();
                        uploadFile();
                    }

                }else {
                    Toast.makeText(ListingPage.this, "Please Select Image",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private void openFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            imageUri = data.getData();
            Picasso.with(this).load(imageUri).into(imageView);
        }
    }


    private String getFileExtension(Uri mUri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return  mime.getExtensionFromMimeType(cr.getType(mUri));
    }

    private void uploadFile() {
        String des = description.getText().toString();
        String contact = contactName.getText().toString();
        String locationCity = location.getText().toString();

        if (imageUri != null){
            StorageReference fileRef = storageReference.child(System.currentTimeMillis()+"."+getFileExtension(imageUri));

            mUploadTask= fileRef.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    progressBar.setProgress(0);
                                }
                            }, 500);

                            Toast.makeText(ListingPage.this, "Upload Successful", Toast.LENGTH_LONG).show();
                            fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String uploadID = imageRef.push().getKey();

                                    Item upload = new Item(sellerId,goodsName.getText().toString().trim(),des,contact,locationCity,
                                            uri.toString(),uploadID);

                                    imageRef.child(uploadID).setValue(upload);
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ListingPage.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                            double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                            progressBar.setProgress((int) progress);
                        }
                    });
        } else {
            Toast.makeText(this, "No file Selected", Toast.LENGTH_SHORT).show();
        }

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void setTotalItem(int number){
        totalItem = number;
    }

}
