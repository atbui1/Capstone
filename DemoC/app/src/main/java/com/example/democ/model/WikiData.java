package com.example.democ.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class WikiData implements Serializable {
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("feature")
    private String feature;
    @SerializedName("listText")
    private List<String> listText;

    public WikiData(String name, String description, String feature, List<String> listText) {
        this.name = name;
        this.description = description;
        this.feature = feature;
        this.listText = listText;
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

    public List<String> getListText() {
        return listText;
    }

    public void setListText(List<String> listText) {
        this.listText = listText;
    }
}
