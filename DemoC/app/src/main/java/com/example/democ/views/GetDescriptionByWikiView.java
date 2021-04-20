package com.example.democ.views;

import com.example.democ.model.WikiData;

import java.util.List;

public interface GetDescriptionByWikiView {
    void getDescriptionByWikiSuccess(WikiData wikiData);
    void getDescriptionByWikiFail();
}
