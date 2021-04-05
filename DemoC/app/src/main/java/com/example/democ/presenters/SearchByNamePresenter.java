package com.example.democ.presenters;

import android.app.Application;
import android.content.Context;

import com.example.democ.capstone.CapstoneRepository;
import com.example.democ.capstone.CapstoneRepositoryImp;
import com.example.democ.model.VegetableData;
import com.example.democ.utils.CallBackData;
import com.example.democ.views.SearchByNameView;

import java.util.List;

public class SearchByNamePresenter {

    private CapstoneRepository mCapstoneRepository;
    private Application mApplication;
    private Context mContext;
    private SearchByNameView mSearchByNameView;

    public SearchByNamePresenter(Application mApplication, Context mContext, SearchByNameView mSearchByNameView) {
        this.mApplication = mApplication;
        this.mContext = mContext;
        this.mSearchByNameView = mSearchByNameView;
        mCapstoneRepository = new CapstoneRepositoryImp();
    }

    public void searchByName(String searchValue, String token) {
        mCapstoneRepository.searchByName(mContext, searchValue, token, new CallBackData<List<VegetableData>>() {
            @Override
            public void onSuccess(List<VegetableData> vegetableData) {
                mSearchByNameView.searchByNameSuccess(vegetableData);
            }

            @Override
            public void onFail(String msgFail) {
                mSearchByNameView.searchByNameFail();
            }
        });
    }
}
