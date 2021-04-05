package com.example.democ.presenters;

import android.app.Application;
import android.content.Context;

import com.example.democ.capstone.CapstoneRepository;
import com.example.democ.capstone.CapstoneRepositoryImp;
import com.example.democ.model.DistrictData;
import com.example.democ.utils.CallBackData;
import com.example.democ.views.DistrictView;

import java.util.List;

public class DistrictPresenter {
    private CapstoneRepository mCapstoneRepository;
    private Application mApplication;
    private Context mContext;
    private DistrictView mDistrictView;

    public DistrictPresenter(Application mApplication, Context mContext, DistrictView mDistrictView) {
        this.mApplication = mApplication;
        this.mContext = mContext;
        this.mDistrictView = mDistrictView;
        mCapstoneRepository = new CapstoneRepositoryImp();
    }

    public  void getDistrictById(int id, String token) {
        mCapstoneRepository.getDistrictById(mContext, id, token, new CallBackData<List<DistrictData>>() {
            @Override
            public void onSuccess(List<DistrictData> districtData) {
                mDistrictView.getDistrictSuccess(districtData);
            }

            @Override
            public void onFail(String msgFail) {
                mDistrictView.getDistrictFail();
            }
        });
    }
}
