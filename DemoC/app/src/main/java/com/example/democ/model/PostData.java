package com.example.democ.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class PostData implements Serializable {

    @SerializedName("id")
    private String id;
    @SerializedName("vegetableId")
    private String vegetableId;
    @SerializedName("vegName")
    private String vegName;
    @SerializedName("vegDescription")
    private String vegDescription;
    @SerializedName("vegFeature")
    private String vegFeature;
    @SerializedName("quantity")
    private int quantity;
    @SerializedName("quantityVeg")
    private int quantityVeg;
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
    @SerializedName("address")
    private String address;

    //CST GetAllShareById - 12 element
    public PostData(String id, String vegetableId, String vegName, String vegDescription, String vegFeature, int quantity, int quantityVeg, int type,
                    String content, List<VegetableExchange> vegetableExchange, String createdDate, String accountId,
                    String fullName, String avatar, String phoneNumber, List<ImageVegetable> imageVegetablesList, String address) {
        this.id = id;
        this.vegetableId = vegetableId;
        this.vegName = vegName;
        this.vegDescription = vegDescription;
        this.vegFeature = vegFeature;
        this.quantity = quantity;
        this.quantityVeg = quantityVeg;
        this.type = type;
        this.content = content;
        this.vegetableExchange = vegetableExchange;
        this.createdDate = createdDate;
        this.accountId = accountId;
        this.fullName = fullName;
        this.avatar = avatar;
        this.phoneNumber = phoneNumber;
        this.imageVegetablesList = imageVegetablesList;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVegetableId() {
        return vegetableId;
    }

    public void setVegetableId(String vegetableId) {
        this.vegetableId = vegetableId;
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

    public int getQuantityVeg() {
        return quantityVeg;
    }

    public void setQuantityVeg(int quantityVeg) {
        this.quantityVeg = quantityVeg;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public List<ImageVegetable> getImageVegetablesList() {
        return imageVegetablesList;
    }

    public void setImageVegetablesList(List<ImageVegetable> imageVegetablesList) {
        this.imageVegetablesList = imageVegetablesList;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}