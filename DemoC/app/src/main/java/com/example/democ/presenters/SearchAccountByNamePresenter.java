package com.example.democ.presenters;

import android.app.Application;
import android.content.Context;

import com.example.democ.capstone.CapstoneRepository;
import com.example.democ.capstone.CapstoneRepositoryImp;
import com.example.democ.model.AccountSearchByName;
import com.example.democ.utils.CallBackData;
import com.example.democ.views.SearchAccountByNameView;

import java.util.List;

public class SearchAccountByNamePresenter {
    private CapstoneRepository mCapstoneRepository;
    private Application mApplication;
    private Context mContext;
    private SearchAccountByNameView mSearchAccountByNameView;

    public SearchAccountByNamePresenter(Application mApplication, Context mContext, SearchAccountByNameView mSearchAccountByNameView) {
        this.mApplication = mApplication;
        this.mContext = mContext;
        this.mSearchAccountByNameView = mSearchAccountByNameView;
        mCapstoneRepository = new CapstoneRepositoryImp();
    }

    public void searchAccountByName(String searchValue, String token) {
        mCapstoneRepository.searchAccountByName(mContext, searchValue, token, new CallBackData<List<AccountSearchByName>>() {
            @Override
            public void onSuccess(List<AccountSearchByName> accountSearchByNames) {
                mSearchAccountByNameView.searchAccountByNameSuccess(accountSearchByNames);
            }

            @Override
            public void onFail(String msgFail) {
                mSearchAccountByNameView.SearchAccountByNameFail();
            }
        });
    }
}
