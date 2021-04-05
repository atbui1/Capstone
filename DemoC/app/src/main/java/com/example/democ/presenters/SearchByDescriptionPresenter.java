package com.example.democ.presenters;

import android.app.Application;
import android.content.Context;

import com.example.democ.capstone.CapstoneRepository;
import com.example.democ.capstone.CapstoneRepositoryImp;
import com.example.democ.model.VegetableData;
import com.example.democ.model.VegetableSearchDescription;
import com.example.democ.utils.CallBackData;
import com.example.democ.views.SearchByDescriptionView;

import java.util.List;

public class SearchByDescriptionPresenter {
    private CapstoneRepository mCapstoneRepository;
    private Application mApplication;
    private Context mContext;
    private SearchByDescriptionView mSearchByDescriptionView;

    public SearchByDescriptionPresenter(Application mApplication, Context mContext, SearchByDescriptionView mSearchByDescriptionView) {
        this.mApplication = mApplication;
        this.mContext = mContext;
        this.mSearchByDescriptionView = mSearchByDescriptionView;
        mCapstoneRepository = new CapstoneRepositoryImp();
    }

    public void searchByDescription(String searchValue, String token) {
        mCapstoneRepository.searchByDescription(mContext, searchValue, token, new CallBackData<List<VegetableData>>() {
            @Override
            public void onSuccess(List<VegetableData> vegetableData) {
                mSearchByDescriptionView.SearchByDescriptionSuccess(vegetableData);
            }

            @Override
            public void onFail(String msgFail) {
                mSearchByDescriptionView.SearchByDescriptionFail();
            }
        });
    }
}
