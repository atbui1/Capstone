package com.example.democ.presenters;

import android.app.Application;
import android.content.Context;

import com.example.democ.capstone.CapstoneRepository;
import com.example.democ.capstone.CapstoneRepositoryImp;
import com.example.democ.model.VegetableData;
import com.example.democ.model.VegetableRequest;
import com.example.democ.utils.CallBackData;
import com.example.democ.views.CreateVegetableView;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class CreateVegetablePresenter {
    private CapstoneRepository mCapstoneRepository;
    private Application mApplication;
    private Context mContext;
    private CreateVegetableView mCreateVegetableView;

    public CreateVegetablePresenter(Application mApplication, Context mContext, CreateVegetableView mCreateVegetableView) {
        this.mApplication = mApplication;
        this.mContext = mContext;
        this.mCreateVegetableView = mCreateVegetableView;
        mCapstoneRepository = new CapstoneRepositoryImp();
    }

    public void createVegetable(RequestBody title, RequestBody description, RequestBody featture, RequestBody newFeatture,
                                RequestBody quantity, RequestBody gardenId,
                                RequestBody idDetailName, RequestBody idDetailDescription,
                                RequestBody idDetailFeature, RequestBody idDetailImage,
                                MultipartBody.Part newImages, String token) {
        mCapstoneRepository.createVegetable(mContext, title, description, featture, newFeatture, quantity, gardenId,
                idDetailName, idDetailDescription, idDetailFeature, idDetailImage, newImages, token,
                new CallBackData<String>() {
                    @Override
                    public void onSuccess(String msg) {
                        mCreateVegetableView.createVegetableSuccess();
                    }

                    @Override
                    public void onFail(String msgFail) {
                        mCreateVegetableView.createVegetableFail();
                    }
                });
    }
}
