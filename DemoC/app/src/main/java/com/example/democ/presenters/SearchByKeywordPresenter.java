package com.example.democ.presenters;

import android.app.Application;
import android.content.Context;

import com.example.democ.capstone.CapstoneRepository;
import com.example.democ.capstone.CapstoneRepositoryImp;
import com.example.democ.model.VegetableData;
import com.example.democ.model.VegetableSearchKeyword;
import com.example.democ.utils.CallBackData;
import com.example.democ.views.SearchByKeywordView;

import java.util.List;

public class SearchByKeywordPresenter {
    private CapstoneRepository mCapstoneRepository;
    private Application mApplication;
    private Context mContext;
    private SearchByKeywordView mSearchByKeywordView;

    public SearchByKeywordPresenter(Application mApplication, Context mContext, SearchByKeywordView mSearchByKeywordView) {
        this.mApplication = mApplication;
        this.mContext = mContext;
        this.mSearchByKeywordView = mSearchByKeywordView;
        mCapstoneRepository = new CapstoneRepositoryImp();
    }

    public void searchByKeyword(String searchValue, String token) {
        mCapstoneRepository.searchByKeyword(mContext, searchValue, token, new CallBackData<List<VegetableData>>() {
            @Override
            public void onSuccess(List<VegetableData> vegetableSearchKeywords) {
                mSearchByKeywordView.SearchByKeywordSuccess(vegetableSearchKeywords);
            }

            @Override
            public void onFail(String msgFail) {
                mSearchByKeywordView.SearchByKeywordFail();
            }
        });
    }
}
