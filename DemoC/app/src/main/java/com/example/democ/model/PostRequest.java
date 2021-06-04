package com.example.democ.model;

import java.util.List;

public class PostRequest {
    private String id;
    private String content;
    private int quantity;
    private int type;
    private int provinceId;
    private int districtId;
    private int wardId;
    private String address;
    private String vegetableId;
    private List<String> vegetableNeedId;

    /*post - share - 8 element*/
    public PostRequest(String content, int quantity, int type, int provinceId, int districtId, int wardId,
                       String address, String vegetableId) {
        this.content = content;
        this.quantity = quantity;
        this.type = type;
        this.provinceId = provinceId;
        this.districtId = districtId;
        this.wardId = wardId;
        this.address = address;
        this.vegetableId = vegetableId;
    }

    /*post - exchange - 9 element*/
    public PostRequest(String content, int quantity, int type, int provinceId, int districtId, int wardId,
                       String address, String vegetableId, List<String> vegetableNeedId) {
        this.content = content;
        this.quantity = quantity;
        this.type = type;
        this.provinceId = provinceId;
        this.districtId = districtId;
        this.wardId = wardId;
        this.address = address;
        this.vegetableId = vegetableId;
        this.vegetableNeedId = vegetableNeedId;
    }

    /*post - update - 10 element - update post*/
    public PostRequest(String id, String content, int quantity, int type, int provinceId, int districtId, int wardId,
                       String address, String vegetableId, List<String> vegetableNeedId) {
        this.id = id;
        this.content = content;
        this.quantity = quantity;
        this.type = type;
        this.provinceId = provinceId;
        this.districtId = districtId;
        this.wardId = wardId;
        this.address = address;
        this.vegetableId = vegetableId;
        this.vegetableNeedId = vegetableNeedId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public String getVegetableId() {
        return vegetableId;
    }

    public void setVegetableId(String vegetableId) {
        this.vegetableId = vegetableId;
    }

    public List<String> getVegetableNeedId() {
        return vegetableNeedId;
    }

    public void setVegetableNeedId(List<String> vegetableNeedId) {
        this.vegetableNeedId = vegetableNeedId;
    }
}
