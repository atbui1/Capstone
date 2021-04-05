package com.example.democ.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProvinceData implements Serializable {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;

    public ProvinceData(int id, String name) {
        this.id = id;
        this.name = name;
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
}
