package com.example.democ.views;

import com.example.democ.model.PostSearchDescription;

import java.util.List;

public interface SearchShareByDescriptionView {
    void SearchShareByDescriptionSuccess(List<PostSearchDescription> postSearchDescriptions);
    void SearchShareByDescriptionFail();
}
