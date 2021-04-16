package com.example.democ.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ExchangeData implements Serializable {
    @SerializedName("id")
    private String id;
    @SerializedName("vegNameReceive")
    private String vegNameReceive;
    @SerializedName("quantity")
    private int quantity;
    @SerializedName("status")
    private int status;
    @SerializedName("createdDate")
    private String createdDate;
    @SerializedName("fullNameHost")
    private String fullNameHost;
    @SerializedName("fullNameReceiver")
    private String fullNameReceiver;
    @SerializedName("accountHostId")
    private String accountHostId;
    @SerializedName("receiverId")
    private String receiverId;
    @SerializedName("shareDetailId")
    private String shareDetailId;

    public ExchangeData(String id, String vegNameReceive, int quantity, int status, String createdDate, String fullNameHost,
                        String fullNameReceiver, String accountHostId, String receiverId, String shareDetailId) {
        this.id = id;
        this.vegNameReceive = vegNameReceive;
        this.quantity = quantity;
        this.status = status;
        this.createdDate = createdDate;
        this.fullNameHost = fullNameHost;
        this.fullNameReceiver = fullNameReceiver;
        this.accountHostId = accountHostId;
        this.receiverId = receiverId;
        this.shareDetailId = shareDetailId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVegNameReceive() {
        return vegNameReceive;
    }

    public void setVegNameReceive(String vegNameReceive) {
        this.vegNameReceive = vegNameReceive;
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

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getFullNameHost() {
        return fullNameHost;
    }

    public void setFullNameHost(String fullNameHost) {
        this.fullNameHost = fullNameHost;
    }

    public String getFullNameReceiver() {
        return fullNameReceiver;
    }

    public void setFullNameReceiver(String fullNameReceiver) {
        this.fullNameReceiver = fullNameReceiver;
    }

    public String getAccountHostId() {
        return accountHostId;
    }

    public void setAccountHostId(String accountHostId) {
        this.accountHostId = accountHostId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getShareDetailId() {
        return shareDetailId;
    }

    public void setShareDetailId(String shareDetailId) {
        this.shareDetailId = shareDetailId;
    }
}
