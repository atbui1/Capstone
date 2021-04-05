package com.example.democ.views;

import com.example.democ.model.WikiData;

import java.util.List;

public interface SearchByWikiView {
    void searchByWikiSuccess(List<WikiData> wikiData);
    void searchByWikiFail();
}
