package com.example.democ.views;

import com.example.democ.model.PostSearchKeyword;

import java.util.List;

public interface SearchShareByKeywordView {
    void SearchShareByKeywordSuccess(List<PostSearchKeyword> postSearchKeywords);
    void SearchShareByKeywordFail();
}
