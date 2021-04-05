package com.example.democ.presenters;

import android.app.Application;
import android.content.Context;

import com.example.democ.capstone.CapstoneRepository;
import com.example.democ.capstone.CapstoneRepositoryImp;
import com.example.democ.model.Account;
import com.example.democ.utils.CallBackData;
import com.example.democ.views.UpdateAccountView;

public class UpdateAccountPresenter {
    private CapstoneRepository mCapstoneRepository;
    private Application mApplication;
    private Context mContext;
    private UpdateAccountView mUpdateAccountView;

    public UpdateAccountPresenter(Application mApplication, Context mContext, UpdateAccountView mUpdateAccountView) {
        this.mApplication = mApplication;
        this.mContext = mContext;
        this.mUpdateAccountView = mUpdateAccountView;
        mCapstoneRepository = new CapstoneRepositoryImp();
    }

    public void updateAccount(Account account, String token) {
        mCapstoneRepository.updateAccount(mContext, account, token, new CallBackData<Account>() {
            @Override
            public void onSuccess(Account account) {
                mUpdateAccountView.updateAccountSuccess(account);
            }

            @Override
            public void onFail(String msgFail) {
                mUpdateAccountView.updateAccountFail();
            }
        });
    }

}
