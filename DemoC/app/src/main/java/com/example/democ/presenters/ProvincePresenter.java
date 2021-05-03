package com.example.democ.presenters;

import android.app.Application;
import android.content.Context;

import com.example.democ.capstone.CapstoneRepository;
import com.example.democ.capstone.CapstoneRepositoryImp;
import com.example.democ.model.ProvinceData;
import com.example.democ.utils.CallBackData;
import com.example.democ.views.ProvinceView;

import java.util.List;

public class ProvincePresenter {
    private CapstoneRepository mCapstoneRepository;
    private Application mApplication;
    private Context mContext;
    private ProvinceView mProvinceView;

    public ProvincePresenter(Application mApplication, Context mContext, ProvinceView mProvinceView) {
        this.mApplication = mApplication;
        this.mContext = mContext;
        this.mProvinceView = mProvinceView;
        mCapstoneRepository = new CapstoneRepositoryImp();
    }

    public void getAllProvince() {
        mCapstoneRepository.getAllProvince(mContext, new CallBackData<List<ProvinceData>>() {
            @Override
            public void onSuccess(List<ProvinceData> provinceData) {
                mProvinceView.getProvinceSuccess(provinceData);
            }

            @Override
            public void onFail(String msgFail) {
                mProvinceView.getProvinceFail();
            }
        });
    }
}
