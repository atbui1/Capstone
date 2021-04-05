package com.example.democ.presenters;

import android.content.Context;

import com.example.democ.room.entities.User;
import com.example.democ.room.managements.UserManagement;
import com.example.democ.views.PersonalView;

public class PersonalPresenter {
    private Context mContext;
    private PersonalView mPersonalView;
    private UserManagement mUserManagement;

    public PersonalPresenter(Context mContext, PersonalView mPersonalView) {
        this.mContext = mContext;
        this.mPersonalView = mPersonalView;
        mUserManagement = new UserManagement(mContext);
    }

    public void getInfoPersonal() {
        mUserManagement.getmUserInfo(new UserManagement.OnDataCallBackUser() {
            @Override
            public void onDataSuccess(User user) {
                mPersonalView.showInfoPersonal(user);
            }

            @Override
            public void onDataFail() {

            }
        });
    }
}
