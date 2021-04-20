package com.example.democ.presenters;

import android.app.Application;
import android.content.Context;

import com.example.democ.capstone.CapstoneRepository;
import com.example.democ.capstone.CapstoneRepositoryImp;
import com.example.democ.utils.CallBackData;
import com.example.democ.views.DeleteFriendView;

public class DeleteFriendPresenter {
    private CapstoneRepository mCapstoneRepository;
    private Application mApplication;
    private Context mContext;
    private DeleteFriendView mDeleteFriendView;

    public DeleteFriendPresenter(Application mApplication, Context mContext, DeleteFriendView mDeleteFriendView) {
        this.mApplication = mApplication;
        this.mContext = mContext;
        this.mDeleteFriendView = mDeleteFriendView;
        mCapstoneRepository = new CapstoneRepositoryImp();
    }

    public void deleteFriend(int idRequest, String token) {
        mCapstoneRepository.deleteFriend(mContext, idRequest, token, new CallBackData<String>() {
            @Override
            public void onSuccess(String s) {
                mDeleteFriendView.deleteFriendSuccess();
            }

            @Override
            public void onFail(String msgFail) {
                mDeleteFriendView.deleteFriendFail();
            }
        });
    }
}
