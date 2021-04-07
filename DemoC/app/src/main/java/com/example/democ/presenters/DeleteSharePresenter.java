package com.example.democ.presenters;

import android.app.Application;
import android.content.Context;

import com.example.democ.capstone.CapstoneRepository;
import com.example.democ.capstone.CapstoneRepositoryImp;
import com.example.democ.utils.CallBackData;
import com.example.democ.views.DeleteShareView;

public class DeleteSharePresenter {
    private CapstoneRepository mCapstoneRepository;
    private Application mApplication;
    private Context mContext;
    private DeleteShareView mDeleteShareView;

    public DeleteSharePresenter(Application mApplication, Context mContext, DeleteShareView mDeleteShareView) {
        this.mApplication = mApplication;
        this.mContext = mContext;
        this.mDeleteShareView = mDeleteShareView;
        mCapstoneRepository = new CapstoneRepositoryImp();
    }

    public void deleteShare(String shareId, String token) {
        mCapstoneRepository.deleteShare(mContext, shareId, token, new CallBackData<String>() {
            @Override
            public void onSuccess(String s) {
                mDeleteShareView.deleteShareSuccess();
            }

            @Override
            public void onFail(String msgFail) {
                mDeleteShareView.deleteShareFail();
            }
        });
    }
}
