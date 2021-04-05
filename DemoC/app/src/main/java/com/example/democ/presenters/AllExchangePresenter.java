package com.example.democ.presenters;

import android.app.Application;
import android.content.Context;

import com.example.democ.capstone.CapstoneRepository;
import com.example.democ.capstone.CapstoneRepositoryImp;
import com.example.democ.model.ExchangeData;
import com.example.democ.utils.CallBackData;
import com.example.democ.views.AllExchangeView;

import java.util.List;

public class AllExchangePresenter {
    private CapstoneRepository mCapstoneRepository;
    private Application mApplication;
    private Context mContext;
    private AllExchangeView mAllExchangeView;

    public AllExchangePresenter(Application mApplication, Context mContext, AllExchangeView mAllExchangeView) {
        this.mApplication = mApplication;
        this.mContext = mContext;
        this.mAllExchangeView = mAllExchangeView;
        mCapstoneRepository = new CapstoneRepositoryImp();
    }

    public void getAllExchange(String token) {
        mCapstoneRepository.getAllExchange(mContext, token, new CallBackData<List<ExchangeData>>() {
            @Override
            public void onSuccess(List<ExchangeData> exchangeData) {
                mAllExchangeView.allExchangeSuccess(exchangeData);
            }

            @Override
            public void onFail(String msgFail) {
                mAllExchangeView.allExchangeFail();
            }
        });
    }
}
