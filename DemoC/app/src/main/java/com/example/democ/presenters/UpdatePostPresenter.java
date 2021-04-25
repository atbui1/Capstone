package com.example.democ.presenters;

import android.app.Application;
import android.content.Context;

import com.example.democ.capstone.CapstoneRepository;
import com.example.democ.capstone.CapstoneRepositoryImp;
import com.example.democ.model.ShareDetail;
import com.example.democ.model.ShareRequest;
import com.example.democ.utils.CallBackData;
import com.example.democ.views.UpdatePostView;

public class UpdatePostPresenter {
    private CapstoneRepository mCapstoneRepository;
    private Application mApplication;
    private Context mContext;
    private UpdatePostView mUpdatePostView;

    public UpdatePostPresenter(Application mApplication, Context mContext, UpdatePostView mUpdatePostView) {
        this.mApplication = mApplication;
        this.mContext = mContext;
        this.mUpdatePostView = mUpdatePostView;
        mCapstoneRepository = new CapstoneRepositoryImp();
    }

    public void updatePost(ShareRequest shareRequest, String token) {
        mCapstoneRepository.updatePost(mContext, shareRequest, token, new CallBackData<ShareDetail>() {
            @Override
            public void onSuccess(ShareDetail shareDetail) {
                mUpdatePostView.updatePostSuccess(shareDetail);
            }

            @Override
            public void onFail(String msgFail) {
                mUpdatePostView.updatePostFail();
            }
        });
    }
}
