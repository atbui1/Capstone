package com.example.democ.iclick;

import com.example.democ.model.AddFriendRequest;

public interface IClickAddFriend {
    void clickFriendAdmit(AddFriendRequest addFriendRequest, int position);
    void clickFriendReject(AddFriendRequest addFriendRequest, int position);
}
