package com.example.democ.presenters;

import android.app.Application;
import android.content.Context;

import com.example.democ.room.entities.User;
import com.example.democ.room.managements.UserManagement;
import com.example.democ.views.RememberAccountView;

public class RememberAccountPresenter {
    private Application mApplication;
    private Context mContext;
    private RememberAccountView mRememberAccountView;
    private UserManagement mUserManagement;

    public RememberAccountPresenter(Application mApplication, Context mContext, RememberAccountView mRememberAccountView) {
        this.mApplication = mApplication;
        this.mContext = mContext;
        this.mRememberAccountView = mRememberAccountView;
        this.mUserManagement = new UserManagement(mApplication);
    }

    public void rememberAccount() {
        mUserManagement.selectUser(new UserManagement.OnDataCallBackUser() {
            @Override
            public void onDataSuccess(User user) {
                mRememberAccountView.rememberAccountSuccess(user);
            }

            @Override
            public void onDataFail() {
                mRememberAccountView.rememberAccountFail();
            }
        });
    }
}
