package com.example.democ.presenters;

import android.app.Application;
import android.content.Context;

import com.example.democ.capstone.CapstoneRepository;
import com.example.democ.capstone.CapstoneRepositoryImp;
import com.example.democ.utils.CallBackData;
import com.example.democ.views.DeleteVegetableView;

public class DeleteVegetablePresenter {
    private CapstoneRepository mCapstoneRepository;
    private Application mApplication;
    private Context mContext;
    private DeleteVegetableView mDeleteVegetableView;

    public DeleteVegetablePresenter(Application mApplication, Context mContext, DeleteVegetableView mDeleteVegetableView) {
        this.mApplication = mApplication;
        this.mContext = mContext;
        this.mDeleteVegetableView = mDeleteVegetableView;
        mCapstoneRepository = new CapstoneRepositoryImp();
    }

    public void deleteVegetable(int noVeg, int gardenId, String token) {
        mCapstoneRepository.deleteVegetable(mContext, noVeg, gardenId, token, new CallBackData<String>() {
            @Override
            public void onSuccess(String s) {
                mDeleteVegetableView.DeleteVegetableSuccess();
            }

            @Override
            public void onFail(String msgFail) {
                mDeleteVegetableView.DeleteVegetableFail();
            }
        });
    }
}
