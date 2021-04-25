package com.example.democ.views;

import com.example.democ.model.AccountData;

public interface UpdateAccountView {
    void updateAccountSuccess(AccountData accountData);
    void updateAccountFail();
}
