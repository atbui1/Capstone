package com.example.democ.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class PostData implements Serializable {

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
    @SerializedName("vegetableNeedId")
    private String vegetableNeedId;
    @SerializedName("vegetableNeedName")
    private String vegetableNeedName;
    @SerializedName("createdDate")
    private String createdDate;
    @SerializedName("accountId")
    private String accountId;
    @SerializedName("fullName")
    private String fullName;
    @SerializedName("images")
    private List<ImageVegetable> imageVegetablesList;

    public PostData(String id, String vegName, String vegDescription, String vegFeature, int quantity, int statius, String content,
                    String vegetableNeedId, String vegetableNeedName, String createdDate, String accountId,
                    String fullName, List<ImageVegetable> imageVegetablesList) {
        this.id = id;
        this.vegName = vegName;
        this.vegDescription = vegDescription;
        this.vegFeature = vegFeature;
        this.quantity = quantity;
        this.statius = statius;
        this.content = content;
        this.vegetableNeedId = vegetableNeedId;
        this.vegetableNeedName = vegetableNeedName;
        this.createdDate = createdDate;
        this.accountId = accountId;
        this.fullName = fullName;
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

    public String getVegetableNeedId() {
        return vegetableNeedId;
    }

    public void setVegetableNeedId(String vegetableNeedId) {
        this.vegetableNeedId = vegetableNeedId;
    }

    public String getVegetableNeedName() {
        return vegetableNeedName;
    }

    public void setVegetableNeedName(String vegetableNeedName) {
        this.vegetableNeedName = vegetableNeedName;
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

    public List<ImageVegetable> getImageVegetablesList() {
        return imageVegetablesList;
    }

    public void setImageVegetablesList(List<ImageVegetable> imageVegetablesList) {
        this.imageVegetablesList = imageVegetablesList;
    }
}