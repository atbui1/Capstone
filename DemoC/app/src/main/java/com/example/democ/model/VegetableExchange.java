package com.example.democ.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class VegetableExchange implements Serializable {

    @SerializedName("vegetableExchangeId")
    private String vegetableExchangeId;
    @SerializedName("vegetableExchangeName")
    private String vegetableExchangeName;

    public VegetableExchange(String vegetableExchangeId, String vegetableExchangeName) {
        this.vegetableExchangeId = vegetableExchangeId;
        this.vegetableExchangeName = vegetableExchangeName;
    }

    public String getVegetableExchangeId() {
        return vegetableExchangeId;
    }

    public void setVegetableExchangeId(String vegetableExchangeId) {
        this.vegetableExchangeId = vegetableExchangeId;
    }

    public String getVegetableExchangeName() {
        return vegetableExchangeName;
    }

    public void setVegetableExchangeName(String vegetableExchangeName) {
        this.vegetableExchangeName = vegetableExchangeName;
    }

    @Override
    public String toString() {
        return vegetableExchangeName;
    }
}
