package com.example.democ.views;

import com.example.democ.model.VegetableData;
import com.example.democ.model.VegetableSearchKeyword;

import java.util.List;

public interface SearchByKeywordView {
    void SearchByKeywordSuccess(List<VegetableData> vegetableSearchKeywords);
    void SearchByKeywordFail();
}
