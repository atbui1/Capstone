package com.example.democ.presenters;

import android.app.Application;
import android.content.Context;

import com.example.democ.capstone.CapstoneRepository;
import com.example.democ.capstone.CapstoneRepositoryImp;
import com.example.democ.model.VegetableData;
import com.example.democ.utils.CallBackData;
import com.example.democ.views.CheckVegetableOfAccountView;

import java.util.List;

public class CheckVegetableOfAccountPresenter {
    private CapstoneRepository mCapstoneRepository;
    private Application mApplication;
    private Context mContext;
    private CheckVegetableOfAccountView mCheckVegetableOfAccountView;

    public CheckVegetableOfAccountPresenter(Application mApplication, Context mContext, CheckVegetableOfAccountView mCheckVegetableOfAccountView) {
        this.mApplication = mApplication;
        this.mContext = mContext;
        this.mCheckVegetableOfAccountView = mCheckVegetableOfAccountView;
        mCapstoneRepository = new CapstoneRepositoryImp();
    }

    public void CheckVegetableOfAccountPresenter(String vegetableNeedId, String vegetableNeedName, String token) {
        mCapstoneRepository.checkVegetableOfAccount(mContext, vegetableNeedId, vegetableNeedName, token,
                new CallBackData<List<VegetableData>>() {
                    @Override
                    public void onSuccess(List<VegetableData> vegetableData) {
                        mCheckVegetableOfAccountView.checkVegetableOfAccountSuccess(vegetableData);
                    }

                    @Override
                    public void onFail(String msgFail) {
                        mCheckVegetableOfAccountView.checkVegetableOfAccountFail();
                    }
                });
    }
}
