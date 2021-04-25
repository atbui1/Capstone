package com.example.democ.views;

import com.example.democ.model.AccountData;

public interface GetInfoAccountView {
    void getInfoAccountSuccess(AccountData accountData);
    void getInfoAccountFail();
}
