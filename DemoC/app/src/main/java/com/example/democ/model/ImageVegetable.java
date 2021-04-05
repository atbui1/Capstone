package com.example.democ.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ImageVegetable implements Serializable {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("thumbnail")
    private String thumbnail;
    @SerializedName("url")
    private String url;
    @SerializedName("localUrl")
    private String localUrl;
    @SerializedName("vegetableDescriptionId")
    private String vegetableDescriptionId;
    @SerializedName("vegetableDescription")
    private VegetableDescription vegetableDescription;

    public ImageVegetable(int id, String name, String thumbnail, String url, String localUrl,
                          String vegetableDescriptionId, VegetableDescription vegetableDescription) {
        this.id = id;
        this.name = name;
        this.thumbnail = thumbnail;
        this.url = url;
        this.localUrl = localUrl;
        this.vegetableDescriptionId = vegetableDescriptionId;
        this.vegetableDescription = vegetableDescription;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLocalUrl() {
        return localUrl;
    }

    public void setLocalUrl(String localUrl) {
        this.localUrl = localUrl;
    }

    public String getVegetableDescriptionId() {
        return vegetableDescriptionId;
    }

    public void setVegetableDescriptionId(String vegetableDescriptionId) {
        this.vegetableDescriptionId = vegetableDescriptionId;
    }

    public VegetableDescription getVegetableDescription() {
        return vegetableDescription;
    }

    public void setVegetableDescription(VegetableDescription vegetableDescription) {
        this.vegetableDescription = vegetableDescription;
    }
}
