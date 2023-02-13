package com.example.barterbarn;

// This class will be used for creating new Users in the
// Firebase Realtime Database. Its fields need to match the
// database schema
public class User {

    private String name;
    private long dateRegistered;
    private String city;
    private String email;

    public User() {
        // Default constructor required for Firebase Realtime Database
    }

    public User(String name, String city, String email) {
        this.name = name;
        this.dateRegistered = System.currentTimeMillis();
        this.city = city;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public long getDateRegistered() {
        return dateRegistered;
    }

    public String getCity() {
        return city;
    }

    public String getEmail() {
        return email;
    }
}
