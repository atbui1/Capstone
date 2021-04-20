package com.example.democ.iclick;

import com.example.democ.model.PostData;

public interface IClickPostAccount {
    void clickPostAccount(PostData postData);
    void clickDeletePostAccount(PostData postData, int positionDelete);
    void clickCallPhone(PostData postData);
}
