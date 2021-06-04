package com.example.democ.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GardenResult implements Serializable {

    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("provinceId")
    private int provinceId;
    @SerializedName("provinceName")
    private String provinceName;
    @SerializedName("districtId")
    private int districtId;
    @SerializedName("districtName")
    private String districtName;
    @SerializedName("wardId")
    private int wardId;
    @SerializedName("wardName")
    private String wardName;
    @SerializedName("address")
    private String address;
    @SerializedName("accountId")
    private  String accountId;
    @SerializedName("appAccount")
    private  AppAccount appAccount;

    /*garden list get all garden by account*/
    public GardenResult(int id, String name, int provinceId, String provinceName, int districtId,
                        String districtName, int wardId, String wardName, String address) {
        this.id = id;
        this.name = name;
        this.provinceId = provinceId;
        this.provinceName = provinceName;
        this.districtId = districtId;
        this.districtName = districtName;
        this.wardId = wardId;
        this.wardName = wardName;
        this.address = address;
    }
    /*get garden by id*/
    public GardenResult(int id, String name, int provinceId, String provinceName, int districtId, String districtName,
                        int wardId, String wardName, String address, String accountId, AppAccount appAccount) {
        this.id = id;
        this.name = name;
        this.provinceId = provinceId;
        this.provinceName = provinceName;
        this.districtId = districtId;
        this.districtName = districtName;
        this.wardId = wardId;
        this.wardName = wardName;
        this.address = address;
        this.accountId = accountId;
        this.appAccount = appAccount;
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

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public int getWardId() {
        return wardId;
    }

    public void setWardId(int wardId) {
        this.wardId = wardId;
    }

    public String getWardName() {
        return wardName;
    }

    public void setWardName(String wardName) {
        this.wardName = wardName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public AppAccount getAppAccount() {
        return appAccount;
    }

    public void setAppAccount(AppAccount appAccount) {
        this.appAccount = appAccount;
    }
}
