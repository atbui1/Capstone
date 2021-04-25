package com.example.democ.iclick;

import com.example.democ.model.GardenResult;

public interface IClickGardenFull {
    void clickGarden(GardenResult gardenResult);
    void clickGardenDelete(GardenResult gardenResult, int position);
    void clickGardenUpdate(GardenResult gardenResult);
}
