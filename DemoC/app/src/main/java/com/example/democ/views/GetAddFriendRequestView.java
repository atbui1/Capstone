package com.example.democ.views;

import com.example.democ.model.AddFriendRequest;

import java.util.List;

public interface GetAddFriendRequestView {
    void getAddFriendRequestSuccess(List<AddFriendRequest> addFriendRequests);
    void getAddFriendRequestFail();
}
