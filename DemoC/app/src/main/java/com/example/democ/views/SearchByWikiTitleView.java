package com.example.democ.views;

import com.example.democ.model.WikiDataTitle;

import java.util.List;

public interface SearchByWikiTitleView {
    void searchByWikiTitleSuccess(List<WikiDataTitle> wikiDataTitles);
    void searchByWikiTitleFail();
}
