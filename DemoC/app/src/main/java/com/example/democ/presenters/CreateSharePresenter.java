package com.example.democ.presenters;

import android.app.Application;
import android.content.Context;

import com.example.democ.capstone.CapstoneRepository;
import com.example.democ.capstone.CapstoneRepositoryImp;
import com.example.democ.model.ShareDetail;
import com.example.democ.model.ShareRequest;
import com.example.democ.utils.CallBackData;
import com.example.democ.views.CreateShareView;

public class CreateSharePresenter {
    private CapstoneRepository mCapstoneRepository;
    private Application mApplication;
    private Context mContext;
    private CreateShareView mCreateShareView;

    public CreateSharePresenter(Application mApplication, Context mContext, CreateShareView mCreateShareView) {
        this.mApplication = mApplication;
        this.mContext = mContext;
        this.mCreateShareView = mCreateShareView;
        mCapstoneRepository = new CapstoneRepositoryImp();
    }

    public void createShare(ShareRequest shareRequest, String token) {
        mCapstoneRepository.createPostShare(mContext, shareRequest, token, new CallBackData<ShareDetail>() {
            @Override
            public void onSuccess(ShareDetail shareDetail) {
                mCreateShareView.createShareViewSuccess(shareDetail);
            }

            @Override
            public void onFail(String msgFail) {
                mCreateShareView.createShareViewFail();
            }
        });
    }
}
