package com.example.barterbarn;

// This class will be used for creating new Listings in the
// Firebase Realtime Database. Its fields need to match the
// database schema
public class Item {
    private long datePosted;
    private String sellerId;
    private String title;
    private String description;
    private String contactName;
    private String location;
    private String imageUrl;



    private String uploadId;

    public Item() {
        // Default constructor required for Firebase Realtime Database
    }

    public Item(String sellerId, String title, String description, String contactName,String location, String imageUrl,String uploadId) {
        this.sellerId = sellerId;
        this.title = title;
        this.description = description;
        this.contactName = contactName;
        this.datePosted = System.currentTimeMillis();
        this.location = location;
        this.imageUrl = imageUrl;
        this.uploadId = uploadId;

    }


    public String url(){
        return imageUrl;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getTitle() {
        return title;
    }
    public String getId(){
        return sellerId;
    }

    public String getLocation() {
        return location;
    }

    public void setTitle(String text) {
        this.title = text;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContactName() {
        return contactName;
    }

    public long getDatePosted() {
        return datePosted;
    }
    public String getmImageUrl() {
        return imageUrl;
    }
    public void setmImageUrl(String mImageUrl) {
        this.imageUrl = mImageUrl;
    }
    public String getUploadId() {
        return uploadId;
    }

    public void setUploadId(String uploadId) {
        this.uploadId = uploadId;
    }

}
