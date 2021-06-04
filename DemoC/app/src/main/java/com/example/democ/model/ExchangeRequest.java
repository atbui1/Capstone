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
    @SerializedName("provinceId")
    private int provinceId;
    @SerializedName("districtId")
    private int districtId;
    @SerializedName("wardId")
    private int wardId;
    @SerializedName("address")
    private String address;
    @SerializedName("fullAddress")
    private String fullAddress;
    @SerializedName("postId")
    private String postId;
    @SerializedName("vegetableId")
    private String vegetableId;

    public ExchangeRequest(int quantity, int quantityExchange, int provinceId, int districtId,
                           int wardId, String address, String fullAddress, String postId, String vegetableId) {
        this.quantity = quantity;
        this.quantityExchange = quantityExchange;
        this.provinceId = provinceId;
        this.districtId = districtId;
        this.wardId = wardId;
        this.address = address;
        this.fullAddress = fullAddress;
        this.postId = postId;
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

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }

    public int getWardId() {
        return wardId;
    }

    public void setWardId(int wardId) {
        this.wardId = wardId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getVegetableId() {
        return vegetableId;
    }

    public void setVegetableId(String vegetableId) {
        this.vegetableId = vegetableId;
    }
}
