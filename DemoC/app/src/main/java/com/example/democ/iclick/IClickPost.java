package com.example.democ.iclick;

import com.example.democ.model.PostData;

public interface IClickPost {
    void clickBtnExchange(PostData shareData);
    void clickPosterUser(PostData shareData);
    void clickReportPost(PostData shareData);
    void clickPostDetail(PostData shareData);
    void clickCallPhone(PostData postData);
}
