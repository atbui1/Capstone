package com.example.democ.presenters;

import android.app.Application;
import android.content.Context;

import com.example.democ.capstone.CapstoneRepository;
import com.example.democ.capstone.CapstoneRepositoryImp;
import com.example.democ.model.AccountData;
import com.example.democ.utils.CallBackData;
import com.example.democ.views.RegisterAccountView;

public class RegisterAccountPresenter {

    private CapstoneRepository mCapstoneRepository;
    private Application mApplication;
    private Context mContext;
    private RegisterAccountView mRegisterAccountView;

    public RegisterAccountPresenter(Application application, Context context, RegisterAccountView registerAccountView) {
        mApplication = application;
        mContext = context;
        mRegisterAccountView = registerAccountView;
        mCapstoneRepository = new CapstoneRepositoryImp();
    }

    public void registerAccount(AccountData accountData) {
        mCapstoneRepository.register(mContext, accountData, new CallBackData<AccountData>() {
            @Override
            public void onSuccess(AccountData accountData) {
                mRegisterAccountView.registerAccountSuccess();
                System.out.println("************** register presenter 31");
            }

            @Override
            public void onFail(String msgFail) {
                mRegisterAccountView.registerAccountFail();
                System.out.println("************** register presenter 37");
            }
        });
    }
}
