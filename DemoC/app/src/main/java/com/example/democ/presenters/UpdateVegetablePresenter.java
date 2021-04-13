package com.example.democ.presenters;

import android.app.Application;
import android.content.Context;

import com.example.democ.capstone.CapstoneRepository;
import com.example.democ.capstone.CapstoneRepositoryImp;
import com.example.democ.model.UpdateVegetableRequest;
import com.example.democ.model.VegetableData;
import com.example.democ.utils.CallBackData;
import com.example.democ.views.UpdateVegetableView;

import java.util.List;

import okhttp3.MultipartBody;

public class UpdateVegetablePresenter {
    private CapstoneRepository mCapstoneRepository;
    private Application mApplication;
    private Context mContext;
    private UpdateVegetableView mUpdateVegetableView;

    public UpdateVegetablePresenter(Application mApplication, Context mContext, UpdateVegetableView mUpdateVegetableView) {
        this.mApplication = mApplication;
        this.mContext = mContext;
        this.mUpdateVegetableView = mUpdateVegetableView;
        mCapstoneRepository = new CapstoneRepositoryImp();
    }

//    public void updateVegetable(String idVeg, String title, String description, String feature, int quantity,
//                                int gardenId, List<MultipartBody.Part> newImages, String token) {
//        mCapstoneRepository.updateVegetable(mContext, idVeg, title, description, feature, quantity, gardenId, newImages, token,
//                new CallBackData<VegetableData>() {
//            @Override
//            public void onSuccess(VegetableData vegetableData) {
//                mUpdateVegetableView.updateVegetableSuccess(vegetableData);
//            }
//
//            @Override
//            public void onFail(String msgFail) {
//                mUpdateVegetableView.updateVegetableFail();
//            }
//        });
//    }
    public void updateVegetable(UpdateVegetableRequest updateVegetableRequest, String token) {
        mCapstoneRepository.updateVegetable(mContext, updateVegetableRequest, token, new CallBackData<VegetableData>() {
            @Override
            public void onSuccess(VegetableData vegetableData) {
                System.out.println("PPPPPPPPPPPPPPPP update vegetable presenter success PPPPPPPPPPPPPPPPP");
            }

            @Override
            public void onFail(String msgFail) {
                System.out.println("PPPPPPPPPPPPPPPP update vegetable presenter FAIL PPPPPPPPPPPPPPPPP");
            }
        });
    }
}
