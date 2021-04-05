package com.example.democ.views;

import com.example.democ.model.ExchangeData;
import com.example.democ.model.ExchangeRequest;

import java.util.List;

public interface CreateExchangeView {
    void createExchangeSuccess(List<ExchangeData> exchangeData);
    void createExchangeFail();
}
