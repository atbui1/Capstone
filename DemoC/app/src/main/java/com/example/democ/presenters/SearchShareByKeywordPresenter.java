package com.example.democ.presenters;

import android.app.Application;
import android.content.Context;

import com.example.democ.capstone.CapstoneRepository;
import com.example.democ.capstone.CapstoneRepositoryImp;
import com.example.democ.model.PostSearchKeyword;
import com.example.democ.utils.CallBackData;
import com.example.democ.views.SearchShareByKeywordView;

import java.util.List;

public class SearchShareByKeywordPresenter {
    private CapstoneRepository mCapstoneRepository;
    private Application mApplication;
    private Context mContext;
    private SearchShareByKeywordView mSearchShareByKeywordView;

    public SearchShareByKeywordPresenter(Application mApplication, Context mContext, SearchShareByKeywordView mSearchShareByKeywordView) {
        this.mApplication = mApplication;
        this.mContext = mContext;
        this.mSearchShareByKeywordView = mSearchShareByKeywordView;
        mCapstoneRepository = new CapstoneRepositoryImp();
    }

    public void searchShareByKeyword(String valueSearch, String token) {
        mCapstoneRepository.searchShareByKeyword(mContext, valueSearch, token, new CallBackData<List<PostSearchKeyword>>() {
            @Override
            public void onSuccess(List<PostSearchKeyword> postSearchKeywords) {
                mSearchShareByKeywordView.SearchShareByKeywordSuccess(postSearchKeywords);
            }

            @Override
            public void onFail(String msgFail) {
                mSearchShareByKeywordView.SearchShareByKeywordFail();
            }
        });
    }
}
