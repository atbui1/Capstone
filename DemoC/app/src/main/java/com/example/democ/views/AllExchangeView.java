package com.example.democ.views;

import com.example.democ.model.ExchangeData;

import java.util.List;

public interface AllExchangeView {
    void allExchangeSuccess(List<ExchangeData> exchangeData);
    void allExchangeFail();
}
