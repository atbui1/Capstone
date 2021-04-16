package com.example.democ.views;

import com.example.democ.model.PostSearchName;

import java.util.List;

public interface SearchShareByNameView {
    void SearchShareByNameSuccess(List<PostSearchName> postSearchNames);
    void SearchShareByNameFail();
}
