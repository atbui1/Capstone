package com.example.democ.iclick;

import com.example.democ.model.FriendData;

public interface IClickFriend {
    void clickFriend(FriendData friendData);
    void clickDeleteFriend(FriendData friendData, int position);
}
