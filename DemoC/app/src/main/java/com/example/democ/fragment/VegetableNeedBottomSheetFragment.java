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
import com.example.democ.adapter.VegetableNeedPostAdapter;
import com.example.democ.iclick.IClickVegetableNeed;
import com.example.democ.model.VegetableNeedAll;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class VegetableNeedBottomSheetFragment extends BottomSheetDialogFragment {
    private List<VegetableNeedAll> mListVegetable;
    private IClickVegetableNeed iClickVegetableNeed;

    public VegetableNeedBottomSheetFragment(List<VegetableNeedAll> mListVegetable, IClickVegetableNeed iClickVegetableNeed) {
        this.mListVegetable = mListVegetable;
        this.iClickVegetableNeed = iClickVegetableNeed;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_vegetable_need_bottom_sheet, null);
        bottomSheetDialog.setContentView(view);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_vegetable_need_bottom_sheet);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        VegetableNeedPostAdapter vegetableNeedPostAdapter = new VegetableNeedPostAdapter(
                (ArrayList<VegetableNeedAll>) mListVegetable, new IClickVegetableNeed() {
            @Override
            public void clickVegetableNeed(VegetableNeedAll vegetableNeedAll) {
                iClickVegetableNeed.clickVegetableNeed(vegetableNeedAll);
                bottomSheetDialog.dismiss();
            }
        });
        recyclerView.setAdapter(vegetableNeedPostAdapter);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        return bottomSheetDialog;
    }
}
