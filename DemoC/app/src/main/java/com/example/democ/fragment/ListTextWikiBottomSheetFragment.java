package com.example.democ.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.democ.R;
import com.example.democ.adapter.ListTextWikiAdapter;
import com.example.democ.iclick.IClickListTextWiki;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class ListTextWikiBottomSheetFragment extends BottomSheetDialogFragment {
    private List<String> mListText;
    private IClickListTextWiki mIClickListTextWiki;
    private BottomSheetDialog mBottomSheetDialog;

    public ListTextWikiBottomSheetFragment(List<String> mListText, IClickListTextWiki mIClickListTextWiki) {
        this.mListText = mListText;
        this.mIClickListTextWiki = mIClickListTextWiki;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        mBottomSheetDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        mBottomSheetDialog = new BottomSheetDialog(getContext(), R.style.BottomSheetDialogTheme);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_list_text_wiki_bottom_sheet, null);
        mBottomSheetDialog.setContentView(view);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_search_by_wiki_bottom_sheet);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        ListTextWikiAdapter listTextWikiAdapter = new ListTextWikiAdapter(mListText, new IClickListTextWiki() {
            @Override
            public void clickListText(String msg, int pos) {
                mIClickListTextWiki.clickListText(msg, pos);
                mBottomSheetDialog.dismiss();
            }
        });
        recyclerView.setAdapter(listTextWikiAdapter);
//        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
//        recyclerView.addItemDecoration(itemDecoration);


        return mBottomSheetDialog;
    }

}
