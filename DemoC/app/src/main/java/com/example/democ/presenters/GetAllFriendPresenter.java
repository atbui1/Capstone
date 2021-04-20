package com.example.democ.presenters;

import android.app.Application;
import android.content.Context;

import com.example.democ.capstone.CapstoneRepository;
import com.example.democ.capstone.CapstoneRepositoryImp;
import com.example.democ.model.FriendData;
import com.example.democ.utils.CallBackData;
import com.example.democ.views.GetAllFriendView;

import java.util.List;

public class GetAllFriendPresenter {
    private CapstoneRepository mCapstoneRepository;
    private Application mApplication;
    private Context mContext;
    private GetAllFriendView mGetAllFriendView;

    public GetAllFriendPresenter(Application mApplication, Context mContext, GetAllFriendView mGetAllFriendView) {
        this.mApplication = mApplication;
        this.mContext = mContext;
        this.mGetAllFriendView = mGetAllFriendView;
        mCapstoneRepository = new CapstoneRepositoryImp();
    }

    public void getAllFriend(String token) {
        mCapstoneRepository.getAllFriend(mContext, token, new CallBackData<List<FriendData>>() {
            @Override
            public void onSuccess(List<FriendData> friendData) {
                mGetAllFriendView.getAllFriendSuccess(friendData);
            }

            @Override
            public void onFail(String msgFail) {
                mGetAllFriendView.getAllFriendFail();
            }
        });
    }
}
