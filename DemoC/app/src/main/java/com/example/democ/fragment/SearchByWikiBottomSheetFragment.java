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
import com.example.democ.adapter.SearchWikiAdapter;
import com.example.democ.iclick.IClickWiki;
import com.example.democ.model.WikiData;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

public class SearchByWikiBottomSheetFragment extends BottomSheetDialogFragment {
    private ArrayList<WikiData> mListWiki;
    private IClickWiki mIClickWiki;
    private BottomSheetDialog mBottomSheetDialog;

    public SearchByWikiBottomSheetFragment(ArrayList<WikiData> mListWiki, IClickWiki mIClickWiki) {
        this.mListWiki = mListWiki;
        this.mIClickWiki = mIClickWiki;
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

        SearchWikiAdapter searchWikiAdapter = new SearchWikiAdapter(mListWiki, new IClickWiki() {
            @Override
            public void clickWiki(WikiData wikiData) {
                mIClickWiki.clickWiki(wikiData);
                mBottomSheetDialog.dismiss();
            }
        });

        recyclerView.setAdapter(searchWikiAdapter);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        return mBottomSheetDialog;
    }
}
