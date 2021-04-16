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
import com.example.democ.adapter.SearchDescriptionAdapter;
import com.example.democ.adapter.SearchShareDescriptionAdapter;
import com.example.democ.iclick.IClickSearchDescription;
import com.example.democ.model.PostData;
import com.example.democ.model.PostSearchDescription;

import java.util.ArrayList;


public class SearchShareByDescriptionFragment extends Fragment implements IClickSearchDescription {

    private final static String KEY_BUNDLE_DESCRIPTION = "KEY_BUNDLE_DESCRIPTION";
    private View mView;
    private TextView mTxtSearchValue;
    private RecyclerView mRecyclerSearchDescription;
    private ArrayList<PostSearchDescription> mListDescription;
    private SearchShareDescriptionAdapter mSearchDescriptionAdapter;

    private SearchActivity mSearchActivity;
    private String searchValue = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_search_share_by_description, container, false);

        initialView();
        initialData();
        return mView;
    }
    private void initialView() {

        mTxtSearchValue = mView.findViewById(R.id.txt_search_value);
        mTxtSearchValue.setText("dddddddddd");

        mSearchActivity = (SearchActivity) getActivity();
        searchValue = mSearchActivity.getmSearchValue();
        if (!searchValue.equals("")) {
            mTxtSearchValue.setText(searchValue);
        }

        //nhan list vegetable tu search activity
        mListDescription = (ArrayList<PostSearchDescription>) mSearchActivity.getmListSearchDescription();
        if (mListDescription.size() == 0) {
            mTxtSearchValue.setText("Không có: " + searchValue);
        }
        System.out.println("list vegetable search description");
        System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
        System.out.println(mListDescription.size());
        System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
        System.out.println("list vegetable search description");

        mRecyclerSearchDescription = (RecyclerView) mView.findViewById(R.id.recycler_search_description);
        mRecyclerSearchDescription.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerSearchDescription.setLayoutManager(layoutManager);
    }
    private void initialData() {
        updateUI();
    }

    private void updateUI() {
        if (mSearchDescriptionAdapter == null) {
//            mSearchDescriptionAdapter = new SearchShareDescriptionAdapter(mListDescription, getContext().getApplicationContext());
            mSearchDescriptionAdapter = new SearchShareDescriptionAdapter(mListDescription,
                    getContext().getApplicationContext(), this);
            mRecyclerSearchDescription.setAdapter(mSearchDescriptionAdapter);
        } else {
            mSearchDescriptionAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void iClickPost(PostSearchDescription postSearchDescription) {
        Intent intent = new Intent(getContext(), PostDetailActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_BUNDLE_DESCRIPTION, postSearchDescription);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
