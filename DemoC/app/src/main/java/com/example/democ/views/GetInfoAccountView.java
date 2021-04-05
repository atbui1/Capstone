package com.example.democ.views;

import com.example.democ.model.Account;

public interface GetInfoAccountView {
    void getInfoAccountSuccess(Account account);
    void getInfoAccountFail();
}
