package com.example.democ.presenters;

import android.app.Application;
import android.content.Context;

import com.example.democ.capstone.CapstoneRepository;
import com.example.democ.capstone.CapstoneRepositoryImp;
import com.example.democ.model.ImageVegetable;
import com.example.democ.utils.CallBackData;
import com.example.democ.views.UploadAvatarView;

import java.util.List;

import okhttp3.MultipartBody;

public class UploadAvatarPresenter {
    private CapstoneRepository mCapstoneRepository;
    private Application mApplication;
    private Context mContext;
    private UploadAvatarView mUploadAvatarView;

    public UploadAvatarPresenter(Application mApplication, Context mContext, UploadAvatarView mUploadAvatarView) {
        this.mApplication = mApplication;
        this.mContext = mContext;
        this.mUploadAvatarView = mUploadAvatarView;
        mCapstoneRepository = new CapstoneRepositoryImp();
    }

    public void uploadImage(MultipartBody.Part newItem, String token) {
        mCapstoneRepository.uploadAvatar(mContext, newItem, token, new CallBackData<String>() {
            @Override
            public void onSuccess(String s) {
                mUploadAvatarView.uploadAvatarSuccess();
            }

            @Override
            public void onFail(String msgFail) {
                mUploadAvatarView.uploadAvatarFail();
            }
        });
    }
}
