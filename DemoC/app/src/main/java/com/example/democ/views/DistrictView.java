package com.example.democ.views;

import com.example.democ.model.DistrictData;

import java.util.List;

public interface DistrictView {
    void getDistrictSuccess(List<DistrictData> districtData);
    void getDistrictFail();
}
