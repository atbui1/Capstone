package com.example.democ.presenters;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Base64;

import com.example.democ.capstone.CapstoneRepository;
import com.example.democ.capstone.CapstoneRepositoryImp;
import com.example.democ.model.ImageVegetable;
import com.example.democ.utils.CallBackData;
import com.example.democ.views.UploadImageView;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class UploadImagePresenter {
    private CapstoneRepository mCapstoneRepository;
    private Application mApplication;
    private Context mContext;
    private UploadImageView mUploadImageView;

    public UploadImagePresenter(Application mApplication, Context mContext, UploadImageView mUploadImageView) {
        this.mApplication = mApplication;
        this.mContext = mContext;
        this.mUploadImageView = mUploadImageView;
        mCapstoneRepository = new CapstoneRepositoryImp();
    }

    public void uploadImage(List<MultipartBody.Part> newItem, String token) {
        mCapstoneRepository.uploadImage(mContext, newItem, token, new CallBackData<ImageVegetable>() {
            @Override
            public void onSuccess(ImageVegetable imageVegetables) {
                mUploadImageView.uploadImageSuccess(imageVegetables);
            }

            @Override
            public void onFail(String msgFail) {
                mUploadImageView.uploadImageFail();
            }
        });
    }
}
