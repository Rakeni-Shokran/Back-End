package org.example.rakkenishokran.Entities;

public class PenaltyReport {
    private long userId;
    private String username;
    private String email;
    private String phone;
    private String messageType;
    private java.sql.Timestamp notificationTimeStamp;

    public PenaltyReport(long userId, String username, String email, String phone, String messageType, java.sql.Timestamp notificationTimeStamp) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.messageType = messageType;
        this.notificationTimeStamp = notificationTimeStamp;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public java.sql.Timestamp getNotificationTimeStamp() {
        return notificationTimeStamp;
    }

    public void setNotificationTimeStamp(java.sql.Timestamp notificationTimeStamp) {
        this.notificationTimeStamp = notificationTimeStamp;
    }
}
