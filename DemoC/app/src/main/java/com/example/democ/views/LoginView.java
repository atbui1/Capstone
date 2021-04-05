package com.example.democ.views;

import com.example.democ.room.entities.User;

public interface LoginView {
    void loginSuccess(User user);
    void loginFail();
}
