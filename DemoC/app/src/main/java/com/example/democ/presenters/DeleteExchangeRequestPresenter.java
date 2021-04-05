package com.example.democ.presenters;

import android.app.Application;
import android.content.Context;

import com.example.democ.capstone.CapstoneRepository;
import com.example.democ.capstone.CapstoneRepositoryImp;
import com.example.democ.utils.CallBackData;
import com.example.democ.views.DeleteExchangeRequestView;

public class DeleteExchangeRequestPresenter {
    private CapstoneRepository mCapstoneRepository;
    private Application mApplication;
    private Context mContext;
    private DeleteExchangeRequestView mDeleteExchangeRequestView;

    public DeleteExchangeRequestPresenter(Application mApplication, Context mContext, DeleteExchangeRequestView mDeleteExchangeRequestView) {
        this.mApplication = mApplication;
        this.mContext = mContext;
        this.mDeleteExchangeRequestView = mDeleteExchangeRequestView;
        mCapstoneRepository = new CapstoneRepositoryImp();
    }

    public void deleteExchangeRequest(String exchangeId, String token) {
        mCapstoneRepository.deleteExchangeRequest(mContext, exchangeId, token, new CallBackData<String>() {
            @Override
            public void onSuccess(String s) {
                mDeleteExchangeRequestView.deleteExchangeRequestSuccess();
            }

            @Override
            public void onFail(String msgFail) {
                mDeleteExchangeRequestView.deleteExchangeRequestFail();
            }
        });
    }
}
