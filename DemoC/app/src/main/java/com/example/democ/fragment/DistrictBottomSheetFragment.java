package com.example.democ.fragment;

import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.democ.R;
import com.example.democ.adapter.DistrictAdapter;
import com.example.democ.iclick.IClickDistrict;
import com.example.democ.model.DistrictData;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

public class DistrictBottomSheetFragment extends BottomSheetDialogFragment {
    ArrayList<DistrictData> mListDistrict;
    IClickDistrict mIClickDistrict;

    SearchView mSearchView;
    BottomSheetDialog mBottomSheetDialog;
    DistrictAdapter mDistrictAdapter;

    public DistrictBottomSheetFragment(ArrayList<DistrictData> mListDistrict, IClickDistrict mIClickDistrict) {
        this.mListDistrict = mListDistrict;
        this.mIClickDistrict = mIClickDistrict;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        mBottomSheetDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        mBottomSheetDialog = new BottomSheetDialog(getContext(), R.style.BottomSheetDialogTheme);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_province_bottom_sheet, null);
        mBottomSheetDialog.setContentView(view);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_province_bottom_sheet);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        final BottomSheetDialog finalBottomSheetDialog = mBottomSheetDialog;
        mDistrictAdapter = new DistrictAdapter(mListDistrict, new IClickDistrict() {
            @Override
            public void clickDistrict(DistrictData districtData) {
                mIClickDistrict.clickDistrict(districtData);
                finalBottomSheetDialog.dismiss();
            }
        });

        recyclerView.setAdapter(mDistrictAdapter);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        getSearchView();

        return mBottomSheetDialog;
    }

    //searchview
    public void getSearchView() {
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        mSearchView = (SearchView)  mBottomSheetDialog.findViewById(R.id.search_view);

        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        mSearchView.setMaxWidth(Integer.MAX_VALUE);

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mDistrictAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mDistrictAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }
}
