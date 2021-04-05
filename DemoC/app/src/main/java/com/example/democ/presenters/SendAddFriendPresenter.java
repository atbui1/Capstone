package com.example.democ.presenters;

import android.app.Application;
import android.content.Context;

import com.example.democ.capstone.CapstoneRepository;
import com.example.democ.capstone.CapstoneRepositoryImp;
import com.example.democ.model.AddFriendRequest;
import com.example.democ.utils.CallBackData;
import com.example.democ.views.SendAddFriendView;

public class SendAddFriendPresenter {
    private CapstoneRepository mCapstoneRepository;
    private Application mApplication;
    private Context mContext;
    private SendAddFriendView mSendAddFriendView;

    public SendAddFriendPresenter(Application mApplication, Context mContext, SendAddFriendView mSendAddFriendView) {
        this.mApplication = mApplication;
        this.mContext = mContext;
        this.mSendAddFriendView = mSendAddFriendView;
        mCapstoneRepository = new CapstoneRepositoryImp();
    }

    public void sendAddFriend(AddFriendRequest addFriendRequest, String token) {
        mCapstoneRepository.sendAddFriend(mContext, addFriendRequest, token, new CallBackData<AddFriendRequest>() {
            @Override
            public void onSuccess(AddFriendRequest addFriendRequest) {
                mSendAddFriendView.sendAddFriendSuccess(addFriendRequest);
            }

            @Override
            public void onFail(String msgFail) {
                mSendAddFriendView.sendAddFriendFail();
            }
        });
    }
}
