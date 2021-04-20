package com.example.democ.presenters;

import android.app.Application;
import android.content.Context;

import com.example.democ.capstone.CapstoneRepository;
import com.example.democ.capstone.CapstoneRepositoryImp;
import com.example.democ.model.UpdateVegetableResponse;
import com.example.democ.model.VegetableData;
import com.example.democ.utils.CallBackData;
import com.example.democ.views.UpdateVegetableView;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class UpdateVegetablePresenter {
    private CapstoneRepository mCapstoneRepository;
    private Application mApplication;
    private Context mContext;
    private UpdateVegetableView mUpdateVegetableView;

    public UpdateVegetablePresenter(Application mApplication, Context mContext, UpdateVegetableView mUpdateVegetableView) {
        this.mApplication = mApplication;
        this.mContext = mContext;
        this.mUpdateVegetableView = mUpdateVegetableView;
        mCapstoneRepository = new CapstoneRepositoryImp();
    }

    public void updateVegetable(RequestBody idVeg, RequestBody title, RequestBody description, RequestBody featture,
                                RequestBody quantity, RequestBody gardenId, RequestBody images, MultipartBody.Part newImages, String token) {
        mCapstoneRepository.updateVegetable(mContext, idVeg, title, description, featture, quantity, gardenId, images,
                newImages, token, new CallBackData<UpdateVegetableResponse>() {
                    @Override
                    public void onSuccess(UpdateVegetableResponse updateVegetableResponse) {
                        mUpdateVegetableView.updateVegetableSuccess(updateVegetableResponse);
                    }

                    @Override
                    public void onFail(String msgFail) {
                        mUpdateVegetableView.updateVegetableFail();
                    }
                });
    }

}
