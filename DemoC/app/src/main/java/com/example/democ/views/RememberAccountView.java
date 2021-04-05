package com.example.democ.views;

import com.example.democ.room.entities.User;

public interface RememberAccountView {
    void rememberAccountSuccess(User user);
    void rememberAccountFail();
}
