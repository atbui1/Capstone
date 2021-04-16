package com.example.democ.presenters;

import android.app.Application;
import android.content.Context;

import com.example.democ.capstone.CapstoneRepository;
import com.example.democ.capstone.CapstoneRepositoryImp;
import com.example.democ.model.QRCodeData;
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

    public void getQRCode(String exchangeId, String token) {
        mCapstoneRepository.getQRCode(mContext, exchangeId, token, new CallBackData<QRCodeData>() {
            @Override
            public void onSuccess(QRCodeData qrCodeData) {
                mGetQRCodeView.GetQRCodeSuccess(qrCodeData);
            }

            @Override
            public void onFail(String msgFail) {
                mGetQRCodeView.GetQRCodeFail();
            }
        });
    }
}
