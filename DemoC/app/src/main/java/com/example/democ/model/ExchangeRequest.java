package com.example.democ.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ExchangeRequest implements Serializable {
    @SerializedName("id")
    private String id;
    @SerializedName("quantity")
    private int quantity;
    @SerializedName("quantityExchange")
    private int quantityExchange;
    @SerializedName("shareDetailId")
    private String shareDetailId;
    @SerializedName("vegetableId")
    private String vegetableId;

    public ExchangeRequest(String id, int quantity, int quantityExchange, String shareDetailId, String vegetableId) {
        this.id = id;
        this.quantity = quantity;
        this.quantityExchange = quantityExchange;
        this.shareDetailId = shareDetailId;
        this.vegetableId = vegetableId;
    }

    public ExchangeRequest(int quantity, int quantityExchange, String shareDetailId, String vegetableId) {
        this.quantity = quantity;
        this.quantityExchange = quantityExchange;
        this.shareDetailId = shareDetailId;
        this.vegetableId = vegetableId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantityExchange() {
        return quantityExchange;
    }

    public void setQuantityExchange(int quantityExchange) {
        this.quantityExchange = quantityExchange;
    }

    public String getShareDetailId() {
        return shareDetailId;
    }

    public void setShareDetailId(String shareDetailId) {
        this.shareDetailId = shareDetailId;
    }

    public String getVegetableId() {
        return vegetableId;
    }

    public void setVegetableId(String vegetableId) {
        this.vegetableId = vegetableId;
    }
}
