package com.example.democ.views;

import com.example.democ.model.VegetableData;

import java.util.List;

public interface SearchByNameView {
    void searchByNameSuccess(List<VegetableData> vegetableData);
    void searchByNameFail();
}
