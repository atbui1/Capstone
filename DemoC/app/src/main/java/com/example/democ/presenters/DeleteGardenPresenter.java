package com.example.democ.presenters;

import android.app.Application;
import android.content.Context;

import com.example.democ.capstone.CapstoneRepository;
import com.example.democ.capstone.CapstoneRepositoryImp;
import com.example.democ.model.Garden;
import com.example.democ.model.GardenResult;
import com.example.democ.utils.CallBackData;
import com.example.democ.views.DeleteGardenView;

import java.util.List;

public class DeleteGardenPresenter {
    private CapstoneRepository mCapstoneRepository;
    private Application mApplication;
    private Context mContext;
    private DeleteGardenView mDeleteGardenView;

    public DeleteGardenPresenter(Application mApplication, Context mContext, DeleteGardenView mDeleteGardenView) {
        this.mApplication = mApplication;
        this.mContext = mContext;
        this.mDeleteGardenView = mDeleteGardenView;
        mCapstoneRepository = new CapstoneRepositoryImp();
    }

    public void deleteGarden(final int gardenId, final String token) {

        mCapstoneRepository.deleteGarden(mContext, gardenId, token, new CallBackData<String>() {
            @Override
            public void onSuccess(String msg) {
                mDeleteGardenView.deleteGardenSuccess();
            }

            @Override
            public void onFail(String msgFail) {
                mDeleteGardenView.deleteGardenFail();
            }
        });
    }
}
