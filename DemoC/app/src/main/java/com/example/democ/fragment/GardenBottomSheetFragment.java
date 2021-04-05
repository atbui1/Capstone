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
import com.example.democ.adapter.GardenPostAdapter;
import com.example.democ.iclick.IClickGarden;
import com.example.democ.model.GardenResult;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class GardenBottomSheetFragment extends BottomSheetDialogFragment {
    private List<GardenResult> mListGarden;
    private IClickGarden iClickGarden;

    public GardenBottomSheetFragment(List<GardenResult> mListGarden, IClickGarden iClickGarden) {
        this.mListGarden = mListGarden;
        this.iClickGarden = iClickGarden;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_garden_bottom_sheet, null);
        bottomSheetDialog.setContentView(view);
        RecyclerView recyclerViewGarden = view.findViewById(R.id.recycler_view_garden_bottom_sheet);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerViewGarden.setLayoutManager(layoutManager);

        GardenPostAdapter gardenPostAdapter = new GardenPostAdapter((ArrayList<GardenResult>) mListGarden, new IClickGarden() {
            @Override
            public void clickGarden(GardenResult gardenResult) {
                iClickGarden.clickGarden(gardenResult);
                bottomSheetDialog.dismiss();
            }
        });
        recyclerViewGarden.setAdapter(gardenPostAdapter);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        recyclerViewGarden.addItemDecoration(itemDecoration);
        return bottomSheetDialog;
    }
}
