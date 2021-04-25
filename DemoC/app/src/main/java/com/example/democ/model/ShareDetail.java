package com.example.democ.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ShareDetail implements Serializable {
    @SerializedName("id")
    private String id;
    @SerializedName("content")
    private String content;
    @SerializedName("shareContent")
    private String shareContent;
    @SerializedName("quantity")
    private int quantity;
    @SerializedName("status")
    private int status;
    @SerializedName("dateShare")
    private String dateShare;
    @SerializedName("accountId")
    private String accountId;
    @SerializedName("vegetableId")
    private String vegetableId;
    @SerializedName("lock")
    private boolean lock;
    @SerializedName("appAccount")
    private AppAccount appAccount;

    /*create post share - exchange*/
    public ShareDetail(String id, String shareContent, int quantity, int status, String dateShare,
                       String accountId, String vegetableId, AppAccount appAccount) {
        this.id = id;
        this.shareContent = shareContent;
        this.quantity = quantity;
        this.status = status;
        this.dateShare = dateShare;
        this.accountId = accountId;
        this.vegetableId = vegetableId;
        this.appAccount = appAccount;
    }
    /*update post*/
    public ShareDetail(String id, String content, int quantity, int status, String vegetableId) {
        this.id = id;
        this.content = content;
        this.quantity = quantity;
        this.status = status;
        this.vegetableId = vegetableId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getShareContent() {
        return shareContent;
    }

    public void setShareContent(String shareContent) {
        this.shareContent = shareContent;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDateShare() {
        return dateShare;
    }

    public void setDateShare(String dateShare) {
        this.dateShare = dateShare;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getVegetableId() {
        return vegetableId;
    }

    public void setVegetableId(String vegetableId) {
        this.vegetableId = vegetableId;
    }

    public AppAccount getAppAccount() {
        return appAccount;
    }

    public void setAppAccount(AppAccount appAccount) {
        this.appAccount = appAccount;
    }
}
