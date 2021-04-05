package com.example.democ.presenters;

import android.app.Application;
import android.content.Context;

import com.example.democ.room.entities.User;
import com.example.democ.room.managements.UserManagement;
import com.example.democ.views.LogoutView;

public class LogoutPresenter {
    private UserManagement mUserManagement;
    private Application mApplication;
    private Context mContext;
    private LogoutView mLogoutView;

    public LogoutPresenter(Application mApplication, Context mContext, LogoutView mLogoutView) {
        this.mApplication = mApplication;
        this.mContext = mContext;
        this.mLogoutView = mLogoutView;
        this.mUserManagement = new UserManagement(mApplication);
    }

    public void deleteAccountRoom() {
        mUserManagement.deleteUser(new UserManagement.OnDataCallBackUser() {
            @Override
            public void onDataSuccess(User user) {
                mLogoutView.logoutSuccess();
            }

            @Override
            public void onDataFail() {
                mLogoutView.logoutFail();
            }
        });
    }
}
