package com.example.democ.views;

import com.example.democ.model.ShareDetail;

public interface CreateShareView {
    void createShareViewSuccess(ShareDetail shareDetail);
    void createShareViewFail();
}
