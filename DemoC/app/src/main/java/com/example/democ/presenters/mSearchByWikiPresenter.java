package com.example.democ.presenters;

import android.app.Application;
import android.content.Context;

import com.example.democ.capstone.CapstoneRepository;
import com.example.democ.model.WikiData;
import com.example.democ.utils.CallBackData;
import com.example.democ.views.SearchByWikiView;

import java.util.List;

public class mSearchByWikiPresenter {
    private CapstoneRepository mCapstoneRepository;
    private Application mApplication;
    private Context mContext;
    private SearchByWikiView mSearchByWikiView;

    public mSearchByWikiPresenter(Application mApplication, Context mContext, SearchByWikiView mSearchByWikiView) {
        this.mApplication = mApplication;
        this.mContext = mContext;
        this.mSearchByWikiView = mSearchByWikiView;
    }

    public void searchByWiki(String searchValue, String token) {
        mCapstoneRepository.searchByWiki(mContext, searchValue, token, new CallBackData<List<WikiData>>() {
            @Override
            public void onSuccess(List<WikiData> wikiData) {
                mSearchByWikiView.searchByWikiSuccess(wikiData);
            }

            @Override
            public void onFail(String msgFail) {
                mSearchByWikiView.searchByWikiFail();
            }
        });
    }
}
