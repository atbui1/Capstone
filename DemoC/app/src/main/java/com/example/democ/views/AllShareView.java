package com.example.democ.views;

import com.example.democ.model.PostData;

import java.util.List;

public interface AllShareView {
    void allShareSuccess(List<PostData> postData);
    void allShareFail();
}
