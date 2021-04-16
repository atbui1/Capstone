package com.example.democ.views;

import com.example.democ.model.ExchangeData;

import java.util.List;

public interface GetHistoryExchangeView {
    void GetHistoryExchangeSuccess(List<ExchangeData> exchangeData);
    void GetHistoryExchangeFail();
}
