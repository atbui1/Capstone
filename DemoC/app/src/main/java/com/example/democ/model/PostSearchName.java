package com.example.democ.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class PostSearchName implements Serializable {
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
    @SerializedName("statius")
    private int statius;
    @SerializedName("content")
    private String content;
    @SerializedName("vegetableShare")
    private List<VegetableShare> vegetableShareList;
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
    public PostSearchName(String id, String vegName, String vegDescription, String vegFeature, int quantity, int statius,
                    String content, List<VegetableShare> vegetableShareList, String createdDate, String accountId,
                    String fullName, String avatar, String phoneNumber, List<ImageVegetable> imageVegetablesList) {
        this.id = id;
        this.vegName = vegName;
        this.vegDescription = vegDescription;
        this.vegFeature = vegFeature;
        this.quantity = quantity;
        this.statius = statius;
        this.content = content;
        this.vegetableShareList = vegetableShareList;
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

    public int getStatius() {
        return statius;
    }

    public void setStatius(int statius) {
        this.statius = statius;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<VegetableShare> getVegetableShareList() {
        return vegetableShareList;
    }

    public void setVegetableShareList(List<VegetableShare> vegetableShareList) {
        this.vegetableShareList = vegetableShareList;
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
