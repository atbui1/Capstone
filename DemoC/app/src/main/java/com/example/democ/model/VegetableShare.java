package com.example.democ.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class VegetableShare implements Serializable {
    @SerializedName("id")
    private int id;
    @SerializedName("vegetableDesciptionId")
    private String vegetableDesciptionId;
    @SerializedName("shareDetailId")
    private String shareDetailId;
}
