package com.example.democ.presenters;

import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.Button;

import com.example.democ.R;
import com.example.democ.capstone.CapstoneRepository;
import com.example.democ.capstone.CapstoneRepositoryImp;
import com.example.democ.room.entities.User;
import com.example.democ.room.managements.UserManagement;
import com.example.democ.utils.CallBackData;
import com.example.democ.views.LoginView;

public class LoginPresenter {

    private CapstoneRepository mCapstoneRepository;
    private LoginView mLoginView;
    private Context mContext;
    private UserManagement mUserManagement;
    private Application mApplication;
    private static User mUser;

    public LoginPresenter(Application application, Context context, LoginView loginView) {
        mApplication = application;
        mContext = context;
        mLoginView = loginView;
        mCapstoneRepository = new CapstoneRepositoryImp();
        mUserManagement = new UserManagement(mApplication);
    }

    public void login(final String userName, final String password, String deviceToken) {
        mCapstoneRepository.login(mContext, userName, password, deviceToken, new CallBackData<User>() {
            @Override
            public void onSuccess(User user) {
                if (user != null){
                    //add user to room
                    addUserToRoom(user);
                    mUser = user;
                    System.out.println(user.getToken());
                    System.out.println("******************* LINE 32 loginpresenter");
                } else {
//                    mLoginView.loginFail("Lưu thông tin người dùng thất bại");
                }
            }

            @Override
            public void onFail(String msgFail) {
                System.out.println("******************* onfail line 40 loginpresenter");
                showDialogLoginFail();
            }
        });
    }

    //add account to room
    private void addUserToRoom(User user) {
        mUserManagement.addUser(user, new UserManagement.OnDataCallBackUser() {
            @Override
            public void onDataSuccess(User user) {
                mLoginView.loginSuccess(mUser);
            }

            @Override
            public void onDataFail() {
                mLoginView.loginFail();
            }
        });
    }

    //dialog login fail
    private void showDialogLoginFail() {
        final Dialog dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.dialog_login_fail);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        Button mBtnClose;
        mBtnClose = (Button) dialog.findViewById(R.id.btn_close);
        mBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
