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
    @SerializedName("vegNameReceiveExchangeResponse")
    private String vegNameReceiveExchangeResponse;
    @SerializedName("quantityReceiveExchangeResponse")
    private int quantityReceiveExchangeResponse;
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

    /* new */
    @SerializedName("receiverAddress")
    private String receiverAddress;
    @SerializedName("receiverPhoneNumber")
    private String receiverPhoneNumber;
    @SerializedName("qrCode")
    private String qrCode;
    @SerializedName("typeShare")
    private int typeShare;

    public ExchangeData(String id, String vegNameReceive, int quantity, String vegNameReceiveExchangeResponse,
            int quantityReceiveExchangeResponse, int status, String createdDate, String fullNameHost,
                        String fullNameReceiver, String accountHostId, String receiverId, String shareDetailId) {
        this.id = id;
        this.vegNameReceive = vegNameReceive;
        this.quantity = quantity;
        this.vegNameReceiveExchangeResponse = vegNameReceiveExchangeResponse;
        this.quantityReceiveExchangeResponse = quantityReceiveExchangeResponse;
        this.status = status;
        this.createdDate = createdDate;
        this.fullNameHost = fullNameHost;
        this.fullNameReceiver = fullNameReceiver;
        this.accountHostId = accountHostId;
        this.receiverId = receiverId;
        this.shareDetailId = shareDetailId;
    }

    /*new*/
    public ExchangeData(String id, String vegNameReceive, int quantity, String vegNameReceiveExchangeResponse,
                        int quantityReceiveExchangeResponse, int status, String createdDate, String fullNameHost,
                        String fullNameReceiver, String accountHostId, String receiverId, String shareDetailId,
                        String receiverAddress, String receiverPhoneNumber, String qrCode, int typeShare) {
        this.id = id;
        this.vegNameReceive = vegNameReceive;
        this.quantity = quantity;
        this.vegNameReceiveExchangeResponse = vegNameReceiveExchangeResponse;
        this.quantityReceiveExchangeResponse = quantityReceiveExchangeResponse;
        this.status = status;
        this.createdDate = createdDate;
        this.fullNameHost = fullNameHost;
        this.fullNameReceiver = fullNameReceiver;
        this.accountHostId = accountHostId;
        this.receiverId = receiverId;
        this.shareDetailId = shareDetailId;
        this.receiverAddress = receiverAddress;
        this.receiverPhoneNumber = receiverPhoneNumber;
        this.qrCode = qrCode;
        this.typeShare = typeShare;
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

    public String getVegNameReceiveExchangeResponse() {
        return vegNameReceiveExchangeResponse;
    }

    public void setVegNameReceiveExchangeResponse(String vegNameReceiveExchangeResponse) {
        this.vegNameReceiveExchangeResponse = vegNameReceiveExchangeResponse;
    }

    public int getQuantityReceiveExchangeResponse() {
        return quantityReceiveExchangeResponse;
    }

    public void setQuantityReceiveExchangeResponse(int quantityReceiveExchangeResponse) {
        this.quantityReceiveExchangeResponse = quantityReceiveExchangeResponse;
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

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public String getReceiverPhoneNumber() {
        return receiverPhoneNumber;
    }

    public void setReceiverPhoneNumber(String receiverPhoneNumber) {
        this.receiverPhoneNumber = receiverPhoneNumber;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public int getTypeShare() {
        return typeShare;
    }

    public void setTypeShare(int typeShare) {
        this.typeShare = typeShare;
    }
}
