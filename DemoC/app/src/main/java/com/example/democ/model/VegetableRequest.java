package com.example.democ.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import okhttp3.MultipartBody;

public class VegetableRequest implements Serializable {

    @SerializedName("title")
    private String title;
    @SerializedName("description")
    private String description;
//    @SerializedName("featture")
//    private String feature;
    @SerializedName("newFeatture")
    private String newFeature;

    @SerializedName("gardenId")
    private int gardenId;
    @SerializedName("newImages")
    private List<MultipartBody.Part> newImages;

    public VegetableRequest(String title, String description, String newFeature, int gardenId, List<MultipartBody.Part> newImages) {
        this.title = title;
        this.description = description;
        this.newFeature = newFeature;
        this.gardenId = gardenId;
        this.newImages = newImages;
    }

    public VegetableRequest(String title, String description, String newFeature, int gardenId) {
        this.title = title;
        this.description = description;
        this.newFeature = newFeature;
        this.gardenId = gardenId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    public String getFeature() {
//        return feature;
//    }
//
//    public void setFeature(String feature) {
//        this.feature = feature;
//    }


    public List<MultipartBody.Part> getNewImages() {
        return newImages;
    }

    public void setNewImages(List<MultipartBody.Part> newImages) {
        this.newImages = newImages;
    }

    public String getNewFeature() {
        return newFeature;
    }

    public void setNewFeature(String newFeature) {
        this.newFeature = newFeature;
    }

    public int getGardenId() {
        return gardenId;
    }

    public void setGardenId(int gardenId) {
        this.gardenId = gardenId;
    }
}
