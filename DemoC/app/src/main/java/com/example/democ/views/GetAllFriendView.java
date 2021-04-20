package com.example.democ.views;

import com.example.democ.model.FriendData;

import java.util.List;

public interface GetAllFriendView {
    void getAllFriendSuccess(List<FriendData> friendData);
    void getAllFriendFail();
}
