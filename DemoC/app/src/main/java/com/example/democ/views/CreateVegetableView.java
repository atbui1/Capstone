package com.example.democ.views;

import com.example.democ.model.VegetableData;

public interface CreateVegetableView {
    void createVegetableSuccess(VegetableData vegetableData);
    void createVegetableFail();
}
