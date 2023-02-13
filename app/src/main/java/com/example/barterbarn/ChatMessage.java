package com.example.barterbarn;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ChatMessage {
    private String messageText;
    private String toUser;
    private String fromUser;
    private Date messageTime;
    private String messageID;
    private boolean isSender;

    public ChatMessage() {
    }

    public ChatMessage(String toUser, String fromUser, String messageID, String messageText) {
        this.messageText = messageText;
        this.toUser = toUser;
        this.fromUser = fromUser;
        this.messageTime = Calendar.getInstance().getTime();
        this.messageID = messageID;
        this.isSender = true;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public Date getMessageTime() {
        return messageTime;
    }

    public String getStringTime() {return new SimpleDateFormat("h:mm a, E. d").format(messageTime); }

    public void setMessageTime(Date messageTime) {
        this.messageTime = messageTime;
    }

    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    public void setSender(boolean sender) {
        isSender = sender;
    }

    public boolean getSender(){
        return isSender;
    }
}
