package com.example.democ.presenters;

import android.app.Application;
import android.content.Context;

import com.example.democ.capstone.CapstoneRepository;
import com.example.democ.capstone.CapstoneRepositoryImp;
import com.example.democ.model.ExchangeData;
import com.example.democ.model.ExchangeRequest;
import com.example.democ.utils.CallBackData;
import com.example.democ.views.CreateExchangeView;

import java.util.List;

public class CreateExchangePresenter {
    private CapstoneRepository mCapstoneRepository;
    private Application mApplication;
    private Context mContext;
    private CreateExchangeView mCreateExchangeView;

    public CreateExchangePresenter(Application mApplication, Context mContext, CreateExchangeView mCreateExchangeView) {
        this.mApplication = mApplication;
        this.mContext = mContext;
        this.mCreateExchangeView = mCreateExchangeView;
        mCapstoneRepository = new CapstoneRepositoryImp();
    }

    public void createExchange(ExchangeRequest exchangeRequest, String token) {
        mCapstoneRepository.createExchange(mContext, exchangeRequest, token, new CallBackData<List<ExchangeData>>() {
            @Override
            public void onSuccess(List<ExchangeData> exchangeData) {
                mCreateExchangeView.createExchangeSuccess(exchangeData);
            }

            @Override
            public void onFail(String msgFail) {
                mCreateExchangeView.createExchangeFail();
            }
        });
    }
}
