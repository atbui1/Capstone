package com.example.democ.presenters;

import android.app.Application;
import android.content.Context;

import com.example.democ.capstone.CapstoneRepository;
import com.example.democ.capstone.CapstoneRepositoryImp;
import com.example.democ.utils.CallBackData;
import com.example.democ.views.GetQRCodeView;

public class GetQRCodePresenter {
    private CapstoneRepository mCapstoneRepository;
    private Application mApplication;
    private Context mContext;
    private GetQRCodeView mGetQRCodeView;

    public GetQRCodePresenter(Application mApplication, Context mContext, GetQRCodeView mGetQRCodeView) {
        this.mApplication = mApplication;
        this.mContext = mContext;
        this.mGetQRCodeView = mGetQRCodeView;
        mCapstoneRepository = new CapstoneRepositoryImp();
    }

    public void getQRCode(String exchangeId, String phoneNumber, String token) {
        mCapstoneRepository.getQRCode(mContext, exchangeId, phoneNumber, token, new CallBackData<String>() {
            @Override
            public void onSuccess(String s) {
                mGetQRCodeView.GetQRCodeSuccess(s);
            }

            @Override
            public void onFail(String msgFail) {
                mGetQRCodeView.GetQRCodeFail();
            }
        });
    }
}
