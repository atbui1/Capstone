package com.example.democ.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class VegetableNeedAll implements Serializable {
    @SerializedName("id")
    private String id;
    @SerializedName("text")
    private String text;

    public VegetableNeedAll(String id, String text) {
        this.id = id;
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
