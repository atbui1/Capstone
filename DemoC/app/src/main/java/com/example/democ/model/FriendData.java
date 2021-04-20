package com.example.democ.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FriendData implements Serializable {
    @SerializedName("id")
    private int id;
    @SerializedName("accountSend")
    private String accountSend;
    @SerializedName("friendName")
    private String friendName;
    @SerializedName("requestedDate")
    private String requestedDate;

    public FriendData(int id, String accountSend, String friendName, String requestedDate) {
        this.id = id;
        this.accountSend = accountSend;
        this.friendName = friendName;
        this.requestedDate = requestedDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccountSend() {
        return accountSend;
    }

    public void setAccountSend(String accountSend) {
        this.accountSend = accountSend;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public String getRequestedDate() {
        return requestedDate;
    }

    public void setRequestedDate(String requestedDate) {
        this.requestedDate = requestedDate;
    }
}
