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
import com.example.democ.adapter.VegetablePostAdapter;
import com.example.democ.iclick.IClickVegetable;
import com.example.democ.model.VegetableData;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class VegetableBottomSheetFragment extends BottomSheetDialogFragment {
    private List<VegetableData> mListVegetable;
    private IClickVegetable iClickVegetable;

    public VegetableBottomSheetFragment(List<VegetableData> mListVegetable, IClickVegetable iClickVegetable) {
        this.mListVegetable = mListVegetable;
        this.iClickVegetable = iClickVegetable;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
       final BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_vegetable_bottom_sheet, null);
        bottomSheetDialog.setContentView(view);
        RecyclerView recyclerViewVegetable = view.findViewById(R.id.recycler_view_vegetable_bottom_sheet);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerViewVegetable.setLayoutManager(layoutManager);

        VegetablePostAdapter vegetablePostAdapter = new VegetablePostAdapter((ArrayList<VegetableData>) mListVegetable, new IClickVegetable() {
            @Override
            public void clickVegetable(VegetableData vegetableData) {
                iClickVegetable.clickVegetable(vegetableData);
                bottomSheetDialog.dismiss();
            }
        });
        recyclerViewVegetable.setAdapter(vegetablePostAdapter);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        recyclerViewVegetable.addItemDecoration(itemDecoration);
        return bottomSheetDialog;
    }
}
