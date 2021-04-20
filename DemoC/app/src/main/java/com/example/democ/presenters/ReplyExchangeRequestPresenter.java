package com.example.democ.presenters;

import android.app.Application;
import android.content.Context;

import com.example.democ.capstone.CapstoneRepository;
import com.example.democ.capstone.CapstoneRepositoryImp;
import com.example.democ.utils.CallBackData;
import com.example.democ.views.ReplyExchangeRequestView;

public class ReplyExchangeRequestPresenter {
    private CapstoneRepository mCapstoneRepository;
    private Application mApplication;
    private Context mContext;
    private ReplyExchangeRequestView mReplyExchangeRequestView;

    public ReplyExchangeRequestPresenter(Application mApplication, Context mContext, ReplyExchangeRequestView mReplyExchangeRequestView) {
        this.mApplication = mApplication;
        this.mContext = mContext;
        this.mReplyExchangeRequestView = mReplyExchangeRequestView;
        mCapstoneRepository = new CapstoneRepositoryImp();
    }

    public void replyExchangeRequest(String idRequest, int status, String token) {
        mCapstoneRepository.replyExchangeRequest(mContext, idRequest, status, token, new CallBackData<String>() {
            @Override
            public void onSuccess(String s) {
                mReplyExchangeRequestView.replyExchangeRequestSuccess();
            }

            @Override
            public void onFail(String msgFail) {
                mReplyExchangeRequestView.replyExchangeRequestFail();
            }
        });
    }
}
