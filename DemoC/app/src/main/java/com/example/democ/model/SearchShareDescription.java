package com.example.democ.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SearchShareDescription implements Serializable {
    @SerializedName("id")
    private String id;
    @SerializedName("vegName")
    private String vegName;
    @SerializedName("vegDescription")
    private String vegDescription;
    @SerializedName("vegFeature")
    private String vegFeature;
    @SerializedName("quantity")
    private int quantity;
    @SerializedName("statius")
    private int statius;
    @SerializedName("content")
    private String content;
}
