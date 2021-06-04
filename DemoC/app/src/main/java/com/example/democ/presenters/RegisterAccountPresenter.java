package com.example.democ.presenters;

import android.app.Application;
import android.content.Context;

import com.example.democ.capstone.CapstoneRepository;
import com.example.democ.capstone.CapstoneRepositoryImp;
import com.example.democ.model.AccountData;
import com.example.democ.utils.CallBackData;
import com.example.democ.views.RegisterAccountView;

import okhttp3.RequestBody;

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

//    public void registerAccount(AccountData accountData) {
//        mCapstoneRepository.register(mContext, accountData, new CallBackData<AccountData>() {
//            @Override
//            public void onSuccess(AccountData accountData) {
//                mRegisterAccountView.registerAccountSuccess();
//                System.out.println("************** register presenter 31");
//            }
//
//            @Override
//            public void onFail(String msgFail) {
//                mRegisterAccountView.registerAccountFail();
//                System.out.println("************** register presenter 37");
//            }
//        });
//    }

    public void registerAccount(RequestBody phoneNumber, RequestBody password, RequestBody fullName, RequestBody birthDate,
                                RequestBody sex, RequestBody address, RequestBody email, RequestBody provinceId,
                                RequestBody districtId, RequestBody wardId) {
        mCapstoneRepository.register(mContext, phoneNumber, password, fullName, birthDate, sex, address, email,
                provinceId, districtId, wardId, new CallBackData<String>() {
            @Override
            public void onSuccess(String s) {
                mRegisterAccountView.registerAccountSuccess();
            }

            @Override
            public void onFail(String msgFail) {
                mRegisterAccountView.registerAccountFail();
            }
        });
    }
}
