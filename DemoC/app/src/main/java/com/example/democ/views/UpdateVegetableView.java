package com.example.democ.views;

import com.example.democ.model.UpdateVegetableResponse;
import com.example.democ.model.VegetableData;

public interface UpdateVegetableView {
    void updateVegetableSuccess(UpdateVegetableResponse updateVegetableResponse);
    void updateVegetableFail();
}
