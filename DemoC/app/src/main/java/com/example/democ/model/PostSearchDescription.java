package com.example.democ.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class PostSearchDescription implements Serializable {
    @SerializedName("id")
    private String id;
    @SerializedName("vegName")
    private String vegName;
    @SerializedName("vegDescription")
    private String vegDescription;
    @SerializedName("vegFeature")
    private String vegFeature;
    @SerializedName("quantity")
    private int quantity;
    @SerializedName("type")
    private int type;
    @SerializedName("content")
    private String content;
    @SerializedName("vegetableExchange")
    private List<VegetableExchange> vegetableExchange;
    @SerializedName("createdDate")
    private String createdDate;
    @SerializedName("accountId")
    private String accountId;
    @SerializedName("fullName")
    private String fullName;
    @SerializedName("avatar")
    private String avatar;
    @SerializedName("phoneNumber")
    private String phoneNumber;
    @SerializedName("images")
    private List<ImageVegetable> imageVegetablesList;

    //CST GetAllShareById - 12 element
    public PostSearchDescription(String id, String vegName, String vegDescription, String vegFeature, int quantity, int type,
                                 String content, List<VegetableExchange> vegetableExchange, String createdDate, String accountId,
                                 String fullName, String avatar, String phoneNumber, List<ImageVegetable> imageVegetablesList) {
        this.id = id;
        this.vegName = vegName;
        this.vegDescription = vegDescription;
        this.vegFeature = vegFeature;
        this.quantity = quantity;
        this.type = type;
        this.content = content;
        this.vegetableExchange = vegetableExchange;
        this.createdDate = createdDate;
        this.accountId = accountId;
        this.fullName = fullName;
        this.avatar = avatar;
        this.phoneNumber = phoneNumber;
        this.imageVegetablesList = imageVegetablesList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVegName() {
        return vegName;
    }

    public void setVegName(String vegName) {
        this.vegName = vegName;
    }

    public String getVegDescription() {
        return vegDescription;
    }

    public void setVegDescription(String vegDescription) {
        this.vegDescription = vegDescription;
    }

    public String getVegFeature() {
        return vegFeature;
    }

    public void setVegFeature(String vegFeature) {
        this.vegFeature = vegFeature;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<VegetableExchange> getVegetableExchange() {
        return vegetableExchange;
    }

    public void setVegetableExchange(List<VegetableExchange> vegetableExchange) {
        this.vegetableExchange = vegetableExchange;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<ImageVegetable> getImageVegetablesList() {
        return imageVegetablesList;
    }

    public void setImageVegetablesList(List<ImageVegetable> imageVegetablesList) {
        this.imageVegetablesList = imageVegetablesList;
    }
}
