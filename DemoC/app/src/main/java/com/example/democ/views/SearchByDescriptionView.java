package com.example.democ.views;

import com.example.democ.model.VegetableData;
import com.example.democ.model.VegetableSearchDescription;

import java.util.List;

public interface SearchByDescriptionView {
    void SearchByDescriptionSuccess(List<VegetableData> vegetableData);
    void SearchByDescriptionFail();
}
