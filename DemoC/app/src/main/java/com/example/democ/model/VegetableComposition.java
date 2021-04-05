package com.example.democ.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class VegetableComposition implements Serializable {

    @SerializedName("id")
    private int id;
    @SerializedName("compositionName")
    private String compositionName;

    public VegetableComposition(int id, String compositionName) {
        this.id = id;
        this.compositionName = compositionName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompositionName() {
        return compositionName;
    }

    public void setCompositionName(String compositionName) {
        this.compositionName = compositionName;
    }
}
