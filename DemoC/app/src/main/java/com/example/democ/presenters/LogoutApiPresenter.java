package com.example.democ.presenters;

import android.app.Application;
import android.content.Context;

import com.example.democ.capstone.CapstoneRepository;
import com.example.democ.capstone.CapstoneRepositoryImp;
import com.example.democ.utils.CallBackData;
import com.example.democ.views.LogoutApiView;

public class LogoutApiPresenter {
    private CapstoneRepository mCapstoneRepository;
    private Application mApplication;
    private Context mContext;
    private LogoutApiView mLogoutApiView;

    public LogoutApiPresenter(Application mApplication, Context mContext, LogoutApiView mLogoutApiView) {
        this.mApplication = mApplication;
        this.mContext = mContext;
        this.mLogoutApiView = mLogoutApiView;
        mCapstoneRepository = new CapstoneRepositoryImp();
    }

    public void logoutApi(String deviceToken, String token) {
        mCapstoneRepository.logoutApi(mContext, deviceToken, token, new CallBackData<String>() {
            @Override
            public void onSuccess(String s) {
                mLogoutApiView.logoutApiSuccess();
            }

            @Override
            public void onFail(String msgFail) {
                mLogoutApiView.logoutApiFail();
            }
        });
    }
}
