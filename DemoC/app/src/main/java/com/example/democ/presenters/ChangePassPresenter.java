package com.example.democ.presenters;

import android.app.Application;
import android.content.Context;

import com.example.democ.capstone.CapstoneRepository;
import com.example.democ.capstone.CapstoneRepositoryImp;
import com.example.democ.utils.CallBackData;
import com.example.democ.views.ChangePassView;

public class ChangePassPresenter {
    private CapstoneRepository mCapstoneRepository;
    private Application mApplication;
    private Context mContext;
    private ChangePassView mChangePassView;

    public ChangePassPresenter(Application mApplication, Context mContext, ChangePassView mChangePassView) {
        this.mApplication = mApplication;
        this.mContext = mContext;
        this.mChangePassView = mChangePassView;
        mCapstoneRepository = new CapstoneRepositoryImp();
    }

    public void changePass(String oldPass, String newPass, String token) {
        mCapstoneRepository.changePass(mContext, oldPass, newPass, token, new CallBackData<String>() {
            @Override
            public void onSuccess(String s) {
                mChangePassView.changePassSuccess();
            }

            @Override
            public void onFail(String msgFail) {
                mChangePassView.changePassFail();
            }
        });
    }
}
