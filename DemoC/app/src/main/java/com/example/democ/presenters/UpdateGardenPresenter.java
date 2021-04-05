package com.example.democ.presenters;

import android.app.Application;
import android.content.Context;

import com.example.democ.capstone.CapstoneRepository;
import com.example.democ.capstone.CapstoneRepositoryImp;
import com.example.democ.model.Garden;
import com.example.democ.utils.CallBackData;
import com.example.democ.views.UpdateGardenView;

public class UpdateGardenPresenter {
    private CapstoneRepository mCapstoneRepository;
    private Application mApplication;
    private Context mContext;
    private UpdateGardenView mUpdateGardenView;

    public UpdateGardenPresenter(Application mApplication, Context mContext, UpdateGardenView mUpdateGardenView) {
        this.mApplication = mApplication;
        this.mContext = mContext;
        this.mUpdateGardenView = mUpdateGardenView;
        mCapstoneRepository = new CapstoneRepositoryImp();
    }

    public void updateGarden(Garden garden, String token) {
        mCapstoneRepository.updateGarden(mContext, garden, token, new CallBackData<Garden>() {
            @Override
            public void onSuccess(Garden garden) {
                mUpdateGardenView.updateGardenSuccess(garden);
            }

            @Override
            public void onFail(String msgFail) {
                mUpdateGardenView.updateGardenFail();
            }
        });
    }
}
