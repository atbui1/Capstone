package com.example.democ.presenters;

import android.app.Application;
import android.content.Context;

import com.example.democ.capstone.CapstoneRepository;
import com.example.democ.capstone.CapstoneRepositoryImp;
import com.example.democ.model.WikiData;
import com.example.democ.utils.CallBackData;
import com.example.democ.views.GetDescriptionByWikiView;

import java.util.List;

public class GetDescriptionByWikiPresenter {
    private CapstoneRepository mCapstoneRepository;
    private Application mApplication;
    private Context mContext;
    private GetDescriptionByWikiView mGetDescriptionByWikiView;

    public GetDescriptionByWikiPresenter(Application mApplication, Context mContext, GetDescriptionByWikiView mGetDescriptionByWikiView) {
        this.mApplication = mApplication;
        this.mContext = mContext;
        this.mGetDescriptionByWikiView = mGetDescriptionByWikiView;
        mCapstoneRepository = new CapstoneRepositoryImp();
    }

    public void getDescriptionByWiki(String searchValue, String token) {
        mCapstoneRepository.getDescriptionWiki(mContext, searchValue, token, new CallBackData<WikiData>() {
            @Override
            public void onSuccess(WikiData wikiData) {
                mGetDescriptionByWikiView.getDescriptionByWikiSuccess(wikiData);
            }

            @Override
            public void onFail(String msgFail) {
                mGetDescriptionByWikiView.getDescriptionByWikiFail();
            }
        });
    }
}
