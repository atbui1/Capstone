package com.example.democ.presenters;

import android.app.Application;
import android.content.Context;

import com.example.democ.capstone.CapstoneRepository;
import com.example.democ.capstone.CapstoneRepositoryImp;
import com.example.democ.model.PostData;
import com.example.democ.model.PostSearchDescription;
import com.example.democ.utils.CallBackData;
import com.example.democ.views.SearchShareByDescriptionView;

import java.util.List;

public class SearchShareByDescriptionPresenter {
    private CapstoneRepository mCapstoneRepository;
    private Application mApplication;
    private Context mContext;
    private SearchShareByDescriptionView mSearchShareByDescriptionView;

    public SearchShareByDescriptionPresenter(Application mApplication, Context mContext, SearchShareByDescriptionView mSearchShareByDescriptionView) {
        this.mApplication = mApplication;
        this.mContext = mContext;
        this.mSearchShareByDescriptionView = mSearchShareByDescriptionView;
        mCapstoneRepository = new CapstoneRepositoryImp();
    }

    public void searchShareByDescription(String searchValue, String token) {
        mCapstoneRepository.searchShareByDescription(mContext, searchValue, token, new CallBackData<List<PostSearchDescription>>() {
            @Override
            public void onSuccess(List<PostSearchDescription> postSearchDescriptions) {
                mSearchShareByDescriptionView.SearchShareByDescriptionSuccess(postSearchDescriptions);
            }

            @Override
            public void onFail(String msgFail) {
                mSearchShareByDescriptionView.SearchShareByDescriptionFail();
            }
        });
    }
}
