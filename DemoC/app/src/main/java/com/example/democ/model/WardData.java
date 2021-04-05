package com.example.democ.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class WardData implements Serializable {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("districtID")
    private int districtID;
    @SerializedName("district")
    private String district;

    public WardData(int id, String name, int districtID, String district) {
        this.id = id;
        this.name = name;
        this.districtID = districtID;
        this.district = district;
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

    public int getDistrictID() {
        return districtID;
    }

    public void setDistrictID(int districtID) {
        this.districtID = districtID;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }
}
