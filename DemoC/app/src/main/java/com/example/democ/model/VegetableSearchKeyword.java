package com.example.democ.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class VegetableSearchKeyword implements Serializable {
    @SerializedName("no")
    private int stt;
    @SerializedName("idName")
    private String idName;
    @SerializedName("idDescription")
    private String idDescription;
    @SerializedName("idFeature")
    private String idFeature;
    @SerializedName("idImage")
    private String idImage;
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("feature")
    private String feature;
    @SerializedName("images")
    private List<ImageVegetable> imageVegetables;
    @SerializedName("gardenId")
    private int gardenId;
    @SerializedName("quantity")
    private String quantity;

    public VegetableSearchKeyword(int stt, String idName, String idDescription, String idFeature, String idImage, String name,
                                  String description, String feature, List<ImageVegetable> imageVegetables, int gardenId, String quantity) {
        this.stt = stt;
        this.idName = idName;
        this.idDescription = idDescription;
        this.idFeature = idFeature;
        this.idImage = idImage;
        this.name = name;
        this.description = description;
        this.feature = feature;
        this.imageVegetables = imageVegetables;
        this.gardenId = gardenId;
        this.quantity = quantity;
    }

    public int getStt() {
        return stt;
    }

    public void setStt(int stt) {
        this.stt = stt;
    }

    public String getIdName() {
        return idName;
    }

    public void setIdName(String idName) {
        this.idName = idName;
    }

    public String getIdDescription() {
        return idDescription;
    }

    public void setIdDescription(String idDescription) {
        this.idDescription = idDescription;
    }

    public String getIdFeature() {
        return idFeature;
    }

    public void setIdFeature(String idFeature) {
        this.idFeature = idFeature;
    }

    public String getIdImage() {
        return idImage;
    }

    public void setIdImage(String idImage) {
        this.idImage = idImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public List<ImageVegetable> getImageVegetables() {
        return imageVegetables;
    }

    public void setImageVegetables(List<ImageVegetable> imageVegetables) {
        this.imageVegetables = imageVegetables;
    }

    public int getGardenId() {
        return gardenId;
    }

    public void setGardenId(int gardenId) {
        this.gardenId = gardenId;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
