package com.example.democ.views;

import com.example.democ.model.GardenResult;

import java.util.List;

public interface AllGardenView {
    void getAllGardenSuccess(List<GardenResult> listAllGarden);
    void getAllGardenFail();
}
