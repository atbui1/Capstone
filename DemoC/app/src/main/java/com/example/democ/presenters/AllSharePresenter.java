package com.example.democ.presenters;

import android.app.Application;
import android.content.Context;

import com.example.democ.capstone.CapstoneRepository;
import com.example.democ.capstone.CapstoneRepositoryImp;
import com.example.democ.model.PostData;
import com.example.democ.utils.CallBackData;
import com.example.democ.views.AllShareView;

import java.util.List;

public class AllSharePresenter {
    private CapstoneRepository mCapstoneRepository;
    private Application mApplication;
    private Context mContext;
    private AllShareView mAllShareView;

    public AllSharePresenter(Application mApplication, Context mContext, AllShareView mAllShareView) {
        this.mApplication = mApplication;
        this.mContext = mContext;
        this.mAllShareView = mAllShareView;
        mCapstoneRepository = new CapstoneRepositoryImp();
    }

    public void getAllShare(String token) {
        System.out.println("AAAAAAAAAA  AllSharePresenter AAAAAAAAAAAAAAAAAA");
        mCapstoneRepository.getAllShare(mContext, token, new CallBackData<List<PostData>>() {
            @Override
            public void onSuccess(List<PostData> postData) {
                mAllShareView.allShareSuccess(postData);
                System.out.println("BBBBBBBBBBBBBB  AllSharePresenter BBBBBBBBBBBBBBBB");
            }

            @Override
            public void onFail(String msgFail) {
                mAllShareView.allShareFail();
            }
        });
    }
}
