package com.example.democ.presenters;

import android.app.Application;
import android.content.Context;

import com.example.democ.capstone.CapstoneRepository;
import com.example.democ.capstone.CapstoneRepositoryImp;
import com.example.democ.model.WikiDataTitle;
import com.example.democ.utils.CallBackData;
import com.example.democ.views.SearchByWikiTitleView;

import java.util.List;

public class SearchByWikiTitlePresenter {
    private CapstoneRepository mCapstoneRepository;
    private Application mApplication;
    private Context mContext;
    private SearchByWikiTitleView mSearchByWikiTitleView;

    public SearchByWikiTitlePresenter(Application mApplication, Context mContext, SearchByWikiTitleView mSearchByWikiTitleView) {
        this.mApplication = mApplication;
        this.mContext = mContext;
        this.mSearchByWikiTitleView = mSearchByWikiTitleView;
        mCapstoneRepository = new CapstoneRepositoryImp();
    }

    public void searchByWikiTitle(String valueSearch, String token) {
        mCapstoneRepository.searchByWikiTitle(mContext, valueSearch, token, new CallBackData<List<WikiDataTitle>>() {
            @Override
            public void onSuccess(List<WikiDataTitle> wikiDataTitles) {
                mSearchByWikiTitleView.searchByWikiTitleSuccess(wikiDataTitles);
            }

            @Override
            public void onFail(String msgFail) {
                mSearchByWikiTitleView.searchByWikiTitleFail();
            }
        });
    }
}
