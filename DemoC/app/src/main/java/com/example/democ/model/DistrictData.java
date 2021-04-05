package com.example.democ.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DistrictData implements Serializable {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("provinceID")
    private int provinceID;
    @SerializedName("province")
    private String province;

    public DistrictData(int id, String name, int provinceID, String province) {
        this.id = id;
        this.name = name;
        this.provinceID = provinceID;
        this.province = province;
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

    public int getProvinceID() {
        return provinceID;
    }

    public void setProvinceID(int provinceID) {
        this.provinceID = provinceID;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
}
