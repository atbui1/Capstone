package com.example.democ.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class VegetableCheckIsExist implements Serializable {
    @SerializedName("")
    private List<String> vegetableCheckIsExist;

    public VegetableCheckIsExist(List<String> vegetableCheckIsExist) {
        this.vegetableCheckIsExist = vegetableCheckIsExist;
    }

    public List<String> getVegetableCheckIsExist() {
        return vegetableCheckIsExist;
    }

    public void setVegetableCheckIsExist(List<String> vegetableCheckIsExist) {
        this.vegetableCheckIsExist = vegetableCheckIsExist;
    }
}
