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
import com.example.democ.adapter.ProvinceAdapter;
import com.example.democ.iclick.IClickProvince;
import com.example.democ.model.ProvinceData;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class ProvinceBottomSheetFragment extends BottomSheetDialogFragment {
    private ArrayList<ProvinceData> mListProvince;
    private IClickProvince mIClickProvince;

    private SearchView mSearchView;
    private BottomSheetDialog mBottomSheetDialog;
    ProvinceAdapter mProvinceAdapter;

    public ProvinceBottomSheetFragment(ArrayList<ProvinceData> mListProvince, IClickProvince mIClickProvince) {
        this.mListProvince = mListProvince;
        this.mIClickProvince = mIClickProvince;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
//        BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        mBottomSheetDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);

//        bottomSheetDialog = new BottomSheetDialog(getContext(), R.style.BottomSheetDialogTheme);
        mBottomSheetDialog = new BottomSheetDialog(getContext(), R.style.BottomSheetDialogTheme);

        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_province_bottom_sheet, null);
//        bottomSheetDialog.setContentView(view);
        mBottomSheetDialog.setContentView(view);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_province_bottom_sheet);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

//        final BottomSheetDialog finalBottomSheetDialog = bottomSheetDialog;
        final BottomSheetDialog finalBottomSheetDialog = mBottomSheetDialog;
        mProvinceAdapter = new ProvinceAdapter(mListProvince, new IClickProvince() {
            @Override
            public void clickProvince(ProvinceData provinceData) {
                mIClickProvince.clickProvince(provinceData);
                finalBottomSheetDialog.dismiss();
            }
        });

        recyclerView.setAdapter(mProvinceAdapter);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        getSearchView();

//        return bottomSheetDialog;
        return mBottomSheetDialog;
    }

    //searchview
    public void getSearchView() {
//        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

//        searchView = (SearchView) findViewById(R.id.search_view);
        mSearchView = (SearchView)  mBottomSheetDialog.findViewById(R.id.search_view);

//        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        mSearchView.setMaxWidth(Integer.MAX_VALUE);

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mProvinceAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mProvinceAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }
}
