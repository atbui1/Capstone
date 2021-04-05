package com.example.democ.views;

import com.example.democ.model.PostData;

import java.util.List;

public interface GetAllShareByIdView {
    void getAllShareByIdSuccess(List<PostData> postDataList);
    void getAllShareByIdFail();
}
