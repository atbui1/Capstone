package com.example.democ.views;

import com.example.democ.model.Garden;
import com.example.democ.model.GardenResult;

public interface CreateGardenView {
    void createGardenSuccess(GardenResult gardenResult);
    void createGardenFail();
}
