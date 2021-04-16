package com.example.democ.iclick;

import com.example.democ.model.ExchangeData;

public interface IClickExChange {
    void clickExchangeAccept(ExchangeData exchangeData, int positionClick);
    void clickExchangeRemove(ExchangeData exchangeData, int positionClick);
}
