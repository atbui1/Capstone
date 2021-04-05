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
import com.example.democ.adapter.SearchNameAdapter;
import com.example.democ.iclick.IClickVegetable;
import com.example.democ.model.VegetableData;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

public class SearchByNameBottomSheetFragment extends BottomSheetDialogFragment {
    private ArrayList<VegetableData> mListVegetable;
    private IClickVegetable mIClickVegetable;

    public SearchByNameBottomSheetFragment(ArrayList<VegetableData> mListVegetable, IClickVegetable mIClickVegetable) {
        this.mListVegetable = mListVegetable;
        this.mIClickVegetable = mIClickVegetable;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_search_by_name_bottom_sheet, null);
        bottomSheetDialog.setContentView(view);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_search_by_name_bottom_sheet);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        SearchNameAdapter searchNameAdapter = new SearchNameAdapter(mListVegetable, new IClickVegetable() {
            @Override
            public void clickVegetable(VegetableData vegetableData) {
                mIClickVegetable.clickVegetable(vegetableData);
                bottomSheetDialog.dismiss();
            }
        });
        recyclerView.setAdapter(searchNameAdapter);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
        return bottomSheetDialog;
    }
}
