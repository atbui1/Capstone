package com.example.democ.presenters;

import android.app.Application;
import android.content.Context;

import com.example.democ.capstone.CapstoneRepository;
import com.example.democ.capstone.CapstoneRepositoryImp;
import com.example.democ.model.ExchangeData;
import com.example.democ.utils.CallBackData;
import com.example.democ.views.GetHistoryExchangeView;

import java.util.List;

public class GetHistoryExchangePresenter {
    private CapstoneRepository mCapstoneRepository;
    private Application mApplication;
    private Context mContext;
    private GetHistoryExchangeView mGetHistoryExchangeView;

    public GetHistoryExchangePresenter(Application mApplication, Context mContext, GetHistoryExchangeView mGetHistoryExchangeView) {
        this.mApplication = mApplication;
        this.mContext = mContext;
        this.mGetHistoryExchangeView = mGetHistoryExchangeView;
        mCapstoneRepository = new CapstoneRepositoryImp();
    }

    public void getHistoryExchange(String token) {
        mCapstoneRepository.getHistoryExchange(mContext, token, new CallBackData<List<ExchangeData>>() {
            @Override
            public void onSuccess(List<ExchangeData> exchangeData) {
                mGetHistoryExchangeView.GetHistoryExchangeSuccess(exchangeData);
            }

            @Override
            public void onFail(String msgFail) {
                mGetHistoryExchangeView.GetHistoryExchangeFail();
            }
        });
    }
}
