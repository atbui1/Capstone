package com.example.democ.presenters;

import android.app.Application;
import android.content.Context;

import com.example.democ.capstone.CapstoneRepository;
import com.example.democ.capstone.CapstoneRepositoryImp;
import com.example.democ.utils.CallBackData;
import com.example.democ.views.ConfirmExchangeFinishView;

public class ConfirmExchangeFinishPresenter {
    private CapstoneRepository mCapstoneRepository;
    private Application mApplication;
    private Context mContext;
    private ConfirmExchangeFinishView mConfirmExchangeFinishView;

    public ConfirmExchangeFinishPresenter(Application mApplication, Context mContext, ConfirmExchangeFinishView mConfirmExchangeFinishView) {
        this.mApplication = mApplication;
        this.mContext = mContext;
        this.mConfirmExchangeFinishView = mConfirmExchangeFinishView;
        mCapstoneRepository = new CapstoneRepositoryImp();
    }

    public void confirmExchangeFinish(String exchangeId, String token) {
        mCapstoneRepository.confirmExchangeFinish(mContext, exchangeId, token, new CallBackData<String>() {
            @Override
            public void onSuccess(String s) {
                mConfirmExchangeFinishView.confirmExchangeFinishSuccess();
            }

            @Override
            public void onFail(String msgFail) {
                mConfirmExchangeFinishView.confirmExchangeFinishFail();
            }
        });
    }
}
