package com.example.democ.views;

import com.example.democ.model.WardData;

import java.util.List;

public interface WardView {
    void getWardSuccess(List<WardData> wardData);
    void getWardFail();
}
