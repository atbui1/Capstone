package com.example.democ.presenters;

import android.app.Application;
import android.content.Context;

import com.example.democ.capstone.CapstoneRepository;
import com.example.democ.capstone.CapstoneRepositoryImp;
import com.example.democ.model.VegetableNeedAll;
import com.example.democ.utils.CallBackData;
import com.example.democ.views.AllVegetableNeedView;

import java.util.List;

public class AllVegetableNeedPresenter {
    private CapstoneRepository mCapstoneRepository;
    private Application mApplication;
    private Context mContext;
    private AllVegetableNeedView mAllVegetableNeedView;

    public AllVegetableNeedPresenter(Application mApplication, Context mContext, AllVegetableNeedView mAllVegetableNeedView) {
        this.mApplication = mApplication;
        this.mContext = mContext;
        this.mAllVegetableNeedView = mAllVegetableNeedView;
        mCapstoneRepository = new CapstoneRepositoryImp();
    }

    public void getAllVegetableNeed(String token) {
        mCapstoneRepository.getAllVegetableNeed(mContext, token, new CallBackData<List<VegetableNeedAll>>() {
            @Override
            public void onSuccess(List<VegetableNeedAll> vegetableNeedAlls) {
                mAllVegetableNeedView.allVegetableNeedSuccess(vegetableNeedAlls);
            }

            @Override
            public void onFail(String msgFail) {
            }
        });
    }
}
