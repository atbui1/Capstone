package com.example.democ.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.democ.R;
import com.example.democ.activity.PostDetailActivity;
import com.example.democ.activity.SearchActivity;
import com.example.democ.adapter.SearchShareDescriptionAdapter;
import com.example.democ.adapter.SearchShareNameAdapter;
import com.example.democ.iclick.IClickSearchPostName;
import com.example.democ.model.PostData;
import com.example.democ.model.PostSearchName;

import java.util.ArrayList;
import java.util.List;

public class SearchShareByNameFragment extends Fragment implements IClickSearchPostName {

    private final static String KEY_BUNDLE_NAME = "KEY_BUNDLE_NAME";
    private View mView;
    private TextView mTxtSearchValue;
    private RecyclerView mRecyclerView;
    private SearchShareNameAdapter mSearchShareNameAdapter;

    private List<PostSearchName> mList;
    private SearchActivity mSearchActivity;
    private String searchValue = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_search_share_by_name, container, false);

        initialView();
        initialData();
        return mView;
    }

    private void initialView() {
        mTxtSearchValue = mView.findViewById(R.id.txt_search_value);
        mTxtSearchValue.setText("nnnnnnnnn");

        mSearchActivity = (SearchActivity) getActivity();
        searchValue = mSearchActivity.getmSearchValue();
        if (!searchValue.equals("")) {
            mTxtSearchValue.setText(searchValue);
        }
        //nhan list vegetable tu search activity
        mList = mSearchActivity.getmListSearchName();
        if (mList == null) {
            mTxtSearchValue.setText("Không có: " + searchValue);
        }
        mRecyclerView = mView.findViewById(R.id.recycler_search_name);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
    }
    private void initialData() {
        updateUI();
    }
    private void updateUI() {
        if (mSearchShareNameAdapter == null) {
            mSearchShareNameAdapter = new SearchShareNameAdapter(mList,
                    getContext().getApplicationContext(), this);
            mRecyclerView.setAdapter(mSearchShareNameAdapter);
        } else {
            mSearchShareNameAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void iClickSearchShareName(PostSearchName postSearchName) {
        Intent intent = new Intent(getContext(), PostDetailActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_BUNDLE_NAME, postSearchName);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
