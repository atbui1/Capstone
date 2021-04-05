package com.example.democ.views;

import com.example.democ.model.AddFriendRequest;

public interface SendAddFriendView {
    void sendAddFriendSuccess(AddFriendRequest addFriendRequest);
    void sendAddFriendFail();
}
