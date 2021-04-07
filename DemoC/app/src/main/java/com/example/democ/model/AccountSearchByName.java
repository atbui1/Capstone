package com.example.democ.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AccountSearchByName implements Serializable {

    @SerializedName("id")
    private String accountId;
    @SerializedName("text")
    private String accountName;

    public AccountSearchByName(String accountId, String accountName) {
        this.accountId = accountId;
        this.accountName = accountName;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
}
