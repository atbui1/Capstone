package com.example.democ.presenters;

import android.app.Application;
import android.content.Context;

import com.example.democ.capstone.CapstoneRepository;
import com.example.democ.capstone.CapstoneRepositoryImp;
import com.example.democ.model.AccountData;
import com.example.democ.utils.CallBackData;
import com.example.democ.views.GetInfoAccountView;

public class GetInfoAccountPresenter {
    private CapstoneRepository mCapstoneRepository;
    private Application mApplication;
    private Context mContext;
    private GetInfoAccountView mGetInfoAccountView;

    public GetInfoAccountPresenter(Application mApplication, Context mContext, GetInfoAccountView mGetInfoAccountView) {
        this.mApplication = mApplication;
        this.mContext = mContext;
        this.mGetInfoAccountView = mGetInfoAccountView;
        mCapstoneRepository = new CapstoneRepositoryImp();
    }

    public void getInfoAccount(String accountId, String token) {
        mCapstoneRepository.getInfoAccount(mContext, accountId, token, new CallBackData<AccountData>() {
            @Override
            public void onSuccess(AccountData accountData) {
                mGetInfoAccountView.getInfoAccountSuccess(accountData);
            }

            @Override
            public void onFail(String msgFail) {
                mGetInfoAccountView.getInfoAccountFail();
            }
        });
    }
}
