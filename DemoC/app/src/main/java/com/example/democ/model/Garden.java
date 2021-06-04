package com.example.democ.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Garden implements Serializable {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("provinceId")
    private int provinceId;
    @SerializedName("districtId")
    private int districtId;
    @SerializedName("wardId")
    private int wardId;

    @SerializedName("address")
    private String address;

    /*request garden - create garden */
    public Garden(String name, int provinceId, int districtId, int wardId, String address) {
        this.name = name;
        this.provinceId = provinceId;
        this.districtId = districtId;
        this.wardId = wardId;
        this.address = address;
    }

    public Garden(int id, String name, int provinceId, int districtId, int wardId, String address) {
        this.id = id;
        this.name = name;
        this.provinceId = provinceId;
        this.districtId = districtId;
        this.wardId = wardId;
        this.address = address;
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

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }

    public int getWardId() {
        return wardId;
    }

    public void setWardId(int wardId) {
        this.wardId = wardId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
