package com.example.democ.views;

import com.example.democ.model.Account;

public interface UpdateAccountView {
    void updateAccountSuccess(Account account);
    void updateAccountFail();
}
