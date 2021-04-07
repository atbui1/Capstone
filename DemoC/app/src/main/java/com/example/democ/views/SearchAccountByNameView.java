package com.example.democ.views;

import com.example.democ.model.AccountSearchByName;

import java.util.List;

public interface SearchAccountByNameView {
    void searchAccountByNameSuccess(List<AccountSearchByName> accountSearchByNames);
    void SearchAccountByNameFail();
}
