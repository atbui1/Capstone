package com.example.democ.presenters;

import android.app.Application;
import android.content.Context;

import com.example.democ.capstone.CapstoneRepository;
import com.example.democ.capstone.CapstoneRepositoryImp;
import com.example.democ.utils.CallBackData;
import com.example.democ.views.IsAcceptExchangeView;

import java.util.ArrayList;

public class IsAcceptExchangePresenter {
    private CapstoneRepository mCapstoneRepository;
    private Application mApplication;
    private Context mContext;
    private IsAcceptExchangeView mIsAcceptExchangeView;

    public IsAcceptExchangePresenter(Application mApplication, Context mContext, IsAcceptExchangeView mIsAcceptExchangeView) {
        this.mApplication = mApplication;
        this.mContext = mContext;
        this.mIsAcceptExchangeView = mIsAcceptExchangeView;
        mCapstoneRepository = new CapstoneRepositoryImp();
    }

    public void isAcceptExchange(String id, int status, String token) {
        mCapstoneRepository.isAcceptExchange(mContext, id, status, token, new CallBackData<String>() {
            @Override
            public void onSuccess(String s) {
                mIsAcceptExchangeView.isAcceptExchangeSuccess();
            }

            @Override
            public void onFail(String msgFail) {
                mIsAcceptExchangeView.isAcceptExchangeFail();
            }
        });
    }
}
