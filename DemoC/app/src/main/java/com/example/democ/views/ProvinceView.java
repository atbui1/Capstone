package com.example.democ.views;

import com.example.democ.model.ProvinceData;

import java.util.List;

public interface ProvinceView {
    void getProvinceSuccess(List<ProvinceData> provinceData);
    void getProvinceFail();
}
