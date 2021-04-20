package com.example.democ.presenters;

import android.app.Application;
import android.content.Context;

import com.example.democ.capstone.CapstoneRepository;
import com.example.democ.capstone.CapstoneRepositoryImp;
import com.example.democ.utils.CallBackData;
import com.example.democ.views.DeleteHistoryExchangeView;

public class DeleteHistoryExchangePresenter {
    private CapstoneRepository mCapstoneRepository;
    private Application mApplication;
    private Context mContext;
    private DeleteHistoryExchangeView mDeleteHistoryExchangeView;

    public DeleteHistoryExchangePresenter(Application mApplication, Context mContext, DeleteHistoryExchangeView mDeleteHistoryExchangeView) {
        this.mApplication = mApplication;
        this.mContext = mContext;
        this.mDeleteHistoryExchangeView = mDeleteHistoryExchangeView;
        mCapstoneRepository = new CapstoneRepositoryImp();
    }

    public void deleteHistoryExchange(String exchangeId, String token) {
        mCapstoneRepository.deleteHistoryExchange(mContext, exchangeId, token, new CallBackData<String>() {
            @Override
            public void onSuccess(String s) {
                mDeleteHistoryExchangeView.deleteHistoryExchangeSuccess();
            }

            @Override
            public void onFail(String msgFail) {
                mDeleteHistoryExchangeView.deleteHistoryExchangeFail();
            }
        });
    }
}
