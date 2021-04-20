package com.example.democ.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.democ.R;
import com.example.democ.adapter.SearchWikiTitleAdapter;
import com.example.democ.iclick.IClickWiki;
import com.example.democ.iclick.IClickWikiTitle;
import com.example.democ.model.WikiData;
import com.example.democ.model.WikiDataTitle;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

public class SearchByWikiBottomSheetFragment extends BottomSheetDialogFragment {
    private ArrayList<WikiDataTitle> mListWiki;
    private IClickWikiTitle mIClickWikiTitle;
    private BottomSheetDialog mBottomSheetDialog;

    public SearchByWikiBottomSheetFragment(ArrayList<WikiDataTitle> mListWiki, IClickWikiTitle mIClickWikiTitle) {
        this.mListWiki = mListWiki;
        this.mIClickWikiTitle = mIClickWikiTitle;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        mBottomSheetDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_search_by_wiki_bottom_sheet, null);
        mBottomSheetDialog.setContentView(view);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_search_by_wiki_bottom_sheet);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        SearchWikiTitleAdapter searchWikiTitleAdapter = new SearchWikiTitleAdapter(mListWiki, new IClickWikiTitle() {
            @Override
            public void clickWikiTitle(WikiDataTitle wikiDataTitle) {
                mIClickWikiTitle.clickWikiTitle(wikiDataTitle);
//                mBottomSheetDialog.dismiss();
            }
        });

        recyclerView.setAdapter(searchWikiTitleAdapter);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        return mBottomSheetDialog;
    }
}
