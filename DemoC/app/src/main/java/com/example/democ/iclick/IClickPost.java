package com.example.democ.iclick;

import com.example.democ.model.PostData;

public interface IClickPost {
    void clickBtnExchange(PostData postData);
    void clickPosterUser(PostData postData);
    void clickReportPost(PostData postData);
    void clickPostDetail(PostData postData);
    void clickCallPhone(PostData postData);
}
