package com.example.democ.presenters;

import android.app.Application;
import android.content.Context;

import com.example.democ.capstone.CapstoneRepository;
import com.example.democ.capstone.CapstoneRepositoryImp;
import com.example.democ.model.GardenResult;
import com.example.democ.utils.CallBackData;
import com.example.democ.views.AllGardenView;

import java.util.List;

public class AllGardenPresenter {
    private CapstoneRepository mCapstoneRepository;
    private Application mApplication;
    private Context mContext;
    private AllGardenView mAllGardenView;

    public AllGardenPresenter(Application mApplication, Context mContext, AllGardenView mAllGardenView) {
        this.mApplication = mApplication;
        this.mContext = mContext;
        this.mAllGardenView = mAllGardenView;
        mCapstoneRepository = new CapstoneRepositoryImp();
    }

    public void getAllGarden(String token) {
        mCapstoneRepository.getAllGarden(mContext, token, new CallBackData<List<GardenResult>>() {
            @Override
            public void onSuccess(List<GardenResult> gardenResults) {
                if (gardenResults != null) {
                    mAllGardenView.getAllGardenSuccess(gardenResults);
                }
            }

            @Override
            public void onFail(String msgFail) {
                mAllGardenView.getAllGardenFail();
            }
        });
    }
}
