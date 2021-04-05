package com.example.democ.presenters;

import android.app.Application;
import android.content.Context;

import com.example.democ.capstone.CapstoneRepository;
import com.example.democ.capstone.CapstoneRepositoryImp;
import com.example.democ.model.AddFriendRequest;
import com.example.democ.utils.CallBackData;
import com.example.democ.views.GetAddFriendRequestView;

import java.util.List;

public class GetAddFriendRequestPresenter {
    private CapstoneRepository mCapstoneRepository;
    private Application mApplication;
    private Context mContext;
    private GetAddFriendRequestView mGetAddFriendRequestView;

    public GetAddFriendRequestPresenter(Application mApplication, Context mContext, GetAddFriendRequestView mGetAddFriendRequestView) {
        this.mApplication = mApplication;
        this.mContext = mContext;
        this.mGetAddFriendRequestView = mGetAddFriendRequestView;
        mCapstoneRepository = new CapstoneRepositoryImp();
    }

    public void getAddFriendRequest(String token) {
        mCapstoneRepository.getAddFriendRequest(mContext, token, new CallBackData<List<AddFriendRequest>>() {
            @Override
            public void onSuccess(List<AddFriendRequest> addFriendRequests) {
                mGetAddFriendRequestView.getAddFriendRequestSuccess(addFriendRequests);
            }

            @Override
            public void onFail(String msgFail) {
                mGetAddFriendRequestView.getAddFriendRequestFail();
            }
        });
    }
}
