package com.example.democ.presenters;

import android.app.Application;
import android.content.Context;

import com.example.democ.capstone.CapstoneRepository;
import com.example.democ.capstone.CapstoneRepositoryImp;
import com.example.democ.model.PostData;
import com.example.democ.model.PostSearchName;
import com.example.democ.utils.CallBackData;
import com.example.democ.views.SearchShareByNameView;

import java.util.List;

public class SearchShareByNamePresenter {
    private CapstoneRepository mCapstoneRepository;
    private Application mApplication;
    private Context mContext;
    private SearchShareByNameView mSearchShareByNameView;

    public SearchShareByNamePresenter(Application mApplication, Context mContext, SearchShareByNameView mSearchShareByNameView) {
        this.mApplication = mApplication;
        this.mContext = mContext;
        this.mSearchShareByNameView = mSearchShareByNameView;
        mCapstoneRepository = new CapstoneRepositoryImp();
    }

    public void searchShareByName(String valueSearch, String token) {
        mCapstoneRepository.searchShareByName(mContext, valueSearch, token, new CallBackData<List<PostSearchName>>() {
            @Override
            public void onSuccess(List<PostSearchName> postSearchNames) {
                mSearchShareByNameView.SearchShareByNameSuccess(postSearchNames);
            }

            @Override
            public void onFail(String msgFail) {
                mSearchShareByNameView.SearchShareByNameFail();
            }
        });
    }
}
