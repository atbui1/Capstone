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
    @SerializedName("avatar")
    private String avatar;

    public FriendData(int id, String accountSend, String friendName, String requestedDate, String avatar) {
        this.id = id;
        this.accountSend = accountSend;
        this.friendName = friendName;
        this.requestedDate = requestedDate;
        this.avatar = avatar;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
