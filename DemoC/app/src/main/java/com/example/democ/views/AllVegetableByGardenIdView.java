package com.example.democ.views;

import com.example.democ.model.VegetableData;

import java.util.List;

public interface AllVegetableByGardenIdView {
    void getAllVegetableByGardenIdSuccess(List<VegetableData> vegetableData);
    void getAllVegetableByGardenIdFail();
}
