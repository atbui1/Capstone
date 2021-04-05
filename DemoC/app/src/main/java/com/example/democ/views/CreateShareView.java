package com.example.democ.views;

import com.example.democ.model.ShareData;

public interface CreateShareView {
    void createShareViewSuccess(ShareData shareData);
    void createShareViewFail();
}
