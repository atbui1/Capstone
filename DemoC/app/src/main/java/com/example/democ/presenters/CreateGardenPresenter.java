package com.example.democ.presenters;

import android.app.Application;
import android.content.Context;

import com.example.democ.capstone.CapstoneRepository;
import com.example.democ.capstone.CapstoneRepositoryImp;
import com.example.democ.model.Garden;
import com.example.democ.model.GardenResult;
import com.example.democ.utils.CallBackData;
import com.example.democ.views.CreateGardenView;

public class CreateGardenPresenter {

    private CapstoneRepository mCapstoneRepository;
    private Application mApplication;
    private Context mContext;
    private CreateGardenView mCreateGardenView;

    public CreateGardenPresenter(Application mApplication, Context mContext, CreateGardenView mCreateGardenView) {
        this.mApplication = mApplication;
        this.mContext = mContext;
        this.mCreateGardenView = mCreateGardenView;
        mCapstoneRepository = new CapstoneRepositoryImp();
    }

    public void createGarden(Garden garden, String token) {

        mCapstoneRepository.createGarden(mContext, garden, token, new CallBackData<GardenResult>() {
            @Override
            public void onSuccess(GardenResult gardenResult) {
                mCreateGardenView.createGardenSuccess(gardenResult);
            }

            @Override
            public void onFail(String msgFail) {
                mCreateGardenView.createGardenFail();
            }
        });
    }
}
