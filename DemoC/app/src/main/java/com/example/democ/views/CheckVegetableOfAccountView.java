package com.example.democ.views;

import com.example.democ.model.VegetableData;

import java.util.List;

public interface CheckVegetableOfAccountView {
    void checkVegetableOfAccountSuccess(List<VegetableData> vegetableData);
    void checkVegetableOfAccountFail();
}
