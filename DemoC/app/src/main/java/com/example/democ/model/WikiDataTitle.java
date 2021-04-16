package com.example.democ.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class WikiDataTitle implements Serializable {
    @SerializedName("title")
    private String title;
    @SerializedName("image")
    private String image;

    public WikiDataTitle(String title, String image) {
        this.title = title;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
