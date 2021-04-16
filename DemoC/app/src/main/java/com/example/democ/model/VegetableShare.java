package com.example.democ.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class VegetableShare implements Serializable {
//    @SerializedName("id")
//    private int id;
//    @SerializedName("vegetableDesciptionId")
//    private String vegetableDesciptionId;
//    @SerializedName("shareDetailId")
//    private String shareDetailId;
    @SerializedName("vegetableShareId")
    private String vegetableShareId;
    @SerializedName("vegetableShareName")
    private String vegetableShareName;

    public VegetableShare(String vegetableShareId, String vegetableShareName) {
        this.vegetableShareId = vegetableShareId;
        this.vegetableShareName = vegetableShareName;
    }

    public String getVegetableShareId() {
        return vegetableShareId;
    }

    public void setVegetableShareId(String vegetableShareId) {
        this.vegetableShareId = vegetableShareId;
    }

    public String getVegetableShareName() {
        return vegetableShareName;
    }

    public void setVegetableShareName(String vegetableShareName) {
        this.vegetableShareName = vegetableShareName;
    }

    @Override
    public String toString() {
        return vegetableShareName;
    }
}
