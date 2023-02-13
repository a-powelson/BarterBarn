package com.example.barterbarn;

public class Trade {
    private String user1ID = "";
    private String user2ID = "";
    private String listingName = "";
    private boolean tradeCompleted;

    public Trade() {
    }

    public Trade(String user1ID, String user2ID, String listingName) {
        this.user1ID = user1ID;
        this.user2ID = user2ID;
        this.listingName = listingName;
        this.tradeCompleted = true;
    }

    public String getUser1ID() {
        return user1ID;
    }

    public void setUser1ID(String user1ID) {
        this.user1ID = user1ID;
    }

    public String getUser2ID() {
        return user2ID;
    }

    public void setUser2ID(String user2ID) {
        this.user2ID = user2ID;
    }

    public String getListingName() {
        return listingName;
    }

    public void setListingName(String listingName) {
        this.listingName = listingName;
    }

    public boolean isTradeCompleted() {
        return tradeCompleted;
    }

    public void setTradeCompleted(boolean tradeCompleted) {
        this.tradeCompleted = tradeCompleted;
    }
}
