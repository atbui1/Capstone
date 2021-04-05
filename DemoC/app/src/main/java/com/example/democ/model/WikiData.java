package com.example.democ.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class WikiData implements Serializable {
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("feature")
    private String feature;

    public WikiData(String name, String description, String feature) {
        this.name = name;
        this.description = description;
        this.feature = feature;
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
}
