package com.example.democ.presenters;

import android.app.Application;
import android.content.Context;

import com.example.democ.capstone.CapstoneRepository;
import com.example.democ.capstone.CapstoneRepositoryImp;
import com.example.democ.model.WardData;
import com.example.democ.utils.CallBackData;
import com.example.democ.views.WardView;

import java.util.List;

public class WardPresenter {
    private CapstoneRepository mCapstoneRepository;
    private Application mApplication;
    private Context mContext;
    private WardView mWardView;

    public WardPresenter(Application mApplication, Context mContext, WardView mWardView) {
        this.mApplication = mApplication;
        this.mContext = mContext;
        this.mWardView = mWardView;
        mCapstoneRepository = new CapstoneRepositoryImp();
    }

    public void getWardById(int id, String token) {
        mCapstoneRepository.getWardById(mContext, id, token, new CallBackData<List<WardData>>() {
            @Override
            public void onSuccess(List<WardData> wardData) {
                mWardView.getWardSuccess(wardData);
            }

            @Override
            public void onFail(String msgFail) {
                mWardView.getWardFail();
            }
        });
    }
}
