package com.example.democ.presenters;

import android.app.Application;
import android.content.Context;

import com.example.democ.capstone.CapstoneRepository;
import com.example.democ.capstone.CapstoneRepositoryImp;
import com.example.democ.utils.CallBackData;
import com.example.democ.views.DeleteRequestFriendView;

public class DeleteRequestFriendPresenter {
    private CapstoneRepository mCapstoneRepository;
    private Application mApplication;
    private Context mContext;
    private DeleteRequestFriendView mDeleteRequestFriendView;

    public DeleteRequestFriendPresenter(Application mApplication, Context mContext, DeleteRequestFriendView mDeleteRequestFriendView) {
        this.mApplication = mApplication;
        this.mContext = mContext;
        this.mDeleteRequestFriendView = mDeleteRequestFriendView;
        mCapstoneRepository = new CapstoneRepositoryImp();
    }

    public void deleteRequestFriend(int id, String token) {
        mCapstoneRepository.deleteRequestFriend(mContext, id, token, new CallBackData<String>() {
            @Override
            public void onSuccess(String s) {
                mDeleteRequestFriendView.deleteRequestFriendSuccess();
            }

            @Override
            public void onFail(String msgFail) {
                mDeleteRequestFriendView.deleteRequestFriendFail();
            }
        });
    }
}
