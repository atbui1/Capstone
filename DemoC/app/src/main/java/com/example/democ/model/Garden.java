package com.example.democ.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Garden implements Serializable {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("address")
    private String address;

    public Garden(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public Garden(int id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
