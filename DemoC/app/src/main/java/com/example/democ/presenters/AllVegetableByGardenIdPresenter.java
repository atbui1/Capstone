package com.example.democ.presenters;

import android.app.Application;
import android.content.Context;

import com.example.democ.capstone.CapstoneRepository;
import com.example.democ.capstone.CapstoneRepositoryImp;
import com.example.democ.model.VegetableData;
import com.example.democ.utils.CallBackData;
import com.example.democ.views.AllVegetableByGardenIdView;

import java.util.List;

public class AllVegetableByGardenIdPresenter {
    private CapstoneRepository mCapstoneRepository;
    private Application mApplication;
    private Context mContext;
    private AllVegetableByGardenIdView mAllVegetableByGardenIdView;

    public AllVegetableByGardenIdPresenter(Application mApplication, Context mContext, AllVegetableByGardenIdView mAllVegetableByGardenIdView) {
        this.mApplication = mApplication;
        this.mContext = mContext;
        this.mAllVegetableByGardenIdView = mAllVegetableByGardenIdView;
        mCapstoneRepository = new CapstoneRepositoryImp();
    }

    public void getAllVegetableByGardenId(int gardenId, String token) {
        mCapstoneRepository.getAllVegetableByGardenId(mContext, gardenId, token, new CallBackData<List<VegetableData>>() {
            @Override
            public void onSuccess(List<VegetableData> vegetableData) {
                System.out.println("AllVegetableByGardenIdPresenter thanh cong222222222222222222222222222222");
                System.out.println("AllVegetableByGardenIdPresenter size: " + vegetableData.size());
                System.out.println("AllVegetableByGardenIdPresenter thanh cong222222222222222222222222222222");
                mAllVegetableByGardenIdView.getAllVegetableByGardenIdSuccess(vegetableData);
            }

            @Override
            public void onFail(String msgFail) {
                mAllVegetableByGardenIdView.getAllVegetableByGardenIdFail();
            }
        });
    }
}
