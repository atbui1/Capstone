package com.example.democ.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AddFriendRequest implements Serializable {
    @SerializedName("id")
    private int id;
    @SerializedName("accountSend")
    private String accountSend;
    @SerializedName("accountSendName")
    private String accountSendName;
    @SerializedName("accountReceived")
    private String accountReceived;
    @SerializedName("requestedDate")
    private String requestedDate;
    @SerializedName("status")
    private int status;
    @SerializedName("appAccount")
    private AppAccount appAccount;

    //CST resquest
    public AddFriendRequest(String accountSend, String accountReceived) {
        this.accountSend = accountSend;
        this.accountReceived = accountReceived;
    }

    //CST response


    public AddFriendRequest(int id, String accountSend, String accountSendName, String accountReceived,
                            String requestedDate, int status, AppAccount appAccount) {
        this.id = id;
        this.accountSend = accountSend;
        this.accountSendName = accountSendName;
        this.accountReceived = accountReceived;
        this.requestedDate = requestedDate;
        this.status = status;
        this.appAccount = appAccount;
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

    public String getAccountSendName() {
        return accountSendName;
    }

    public void setAccountSendName(String accountSendName) {
        this.accountSendName = accountSendName;
    }

    public String getAccountReceived() {
        return accountReceived;
    }

    public void setAccountReceived(String accountReceived) {
        this.accountReceived = accountReceived;
    }

    public String getRequestedDate() {
        return requestedDate;
    }

    public void setRequestedDate(String requestedDate) {
        this.requestedDate = requestedDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public AppAccount getAppAccount() {
        return appAccount;
    }

    public void setAppAccount(AppAccount appAccount) {
        this.appAccount = appAccount;
    }
}
