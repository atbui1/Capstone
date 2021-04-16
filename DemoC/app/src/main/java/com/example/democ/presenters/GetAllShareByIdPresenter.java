package com.example.democ.presenters;

import android.app.Application;
import android.content.Context;

import com.example.democ.capstone.CapstoneRepository;
import com.example.democ.capstone.CapstoneRepositoryImp;
import com.example.democ.model.PostData;
import com.example.democ.utils.CallBackData;
import com.example.democ.views.GetAllShareByIdView;

import java.util.List;

public class GetAllShareByIdPresenter {
    private CapstoneRepository mCapstoneRepository;
    private Application mApplication;
    private Context mContext;
    private GetAllShareByIdView mGetAllShareByIdView;

    public GetAllShareByIdPresenter(Application mApplication, Context mContext, GetAllShareByIdView mGetAllShareByIdView) {
        this.mApplication = mApplication;
        this.mContext = mContext;
        this.mGetAllShareByIdView = mGetAllShareByIdView;
        mCapstoneRepository = new CapstoneRepositoryImp();
    }

    public void getAllShareById(String id, String token) {
        mCapstoneRepository.getAllShareById(mContext, id, token, new CallBackData<List<PostData>>() {
            @Override
            public void onSuccess(List<PostData> postData) {
                mGetAllShareByIdView.getAllShareByIdSuccess(postData);
            }

            @Override
            public void onFail(String msgFail) {
                mGetAllShareByIdView.getAllShareByIdFail();
            }
        });
    }
}
