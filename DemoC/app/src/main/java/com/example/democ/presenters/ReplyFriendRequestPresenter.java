package com.example.democ.presenters;

import android.app.Application;
import android.content.Context;

import com.example.democ.capstone.CapstoneRepository;
import com.example.democ.capstone.CapstoneRepositoryImp;
import com.example.democ.utils.CallBackData;
import com.example.democ.views.ReplyFriendRequestView;

public class ReplyFriendRequestPresenter {
    private CapstoneRepository mCapstoneRepository;
    private Application mApplication;
    private Context mContext;
    private ReplyFriendRequestView mReplyFriendRequestView;

    public ReplyFriendRequestPresenter(Application mApplication, Context mContext, ReplyFriendRequestView mReplyFriendRequestView) {
        this.mApplication = mApplication;
        this.mContext = mContext;
        this.mReplyFriendRequestView = mReplyFriendRequestView;
        mCapstoneRepository = new CapstoneRepositoryImp();
    }

    public void replyFriendRequest(int idRequest, int status, String token) {
        mCapstoneRepository.replyFriendRequest(mContext, idRequest, status, token, new CallBackData<String>() {
            @Override
            public void onSuccess(String s) {
                mReplyFriendRequestView.replyFriendRequestSuccess();
            }

            @Override
            public void onFail(String msgFail) {
                mReplyFriendRequestView.replyFriendRequestFail();
            }
        });
    }
}
