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
import com.example.democ.adapter.SearchKeywordAdapter;
import com.example.democ.adapter.SearchShareKeywordAdapter;
import com.example.democ.iclick.IClickSearchKeyword;
import com.example.democ.model.PostSearchKeyword;
import com.example.democ.model.VegetableSearchKeyword;

import java.util.ArrayList;

public class SearchShareByKeywordFragment extends Fragment implements IClickSearchKeyword {

    private final static String KEY_BUNDLE_KEYWORD = "KEY_BUNDLE_KEYWORD";
    private View mView;
    private TextView mTxtSearchValue;
    private RecyclerView mRecyclerSearchKeyword;
    private SearchActivity mSearchActivity;
    private String searchValue = "";
    private ArrayList<PostSearchKeyword> mList;
    private SearchShareKeywordAdapter mSearchShareKeywordAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_search_by_keyword, container, false);

        initialView();
        initialData();
        return mView;
    }

    private void initialView() {
        mTxtSearchValue = (TextView) mView.findViewById(R.id.txt_search_value);

        mSearchActivity = (SearchActivity) getActivity();
        searchValue = mSearchActivity.getmSearchValue();
        //nhan list vegetable tu search activity
        mList = (ArrayList<PostSearchKeyword>) mSearchActivity.getmListSearchKeyword();
        if (mList == null) {
            mTxtSearchValue.setText("Không có: " + searchValue);
        } else {
            mTxtSearchValue.setText("Kết quả: " + searchValue);
        }
        System.out.println("list vegetable search keyword");
        System.out.println("CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC");
//        System.out.println(mList.size());
        System.out.println("CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC");
        System.out.println("list vegetable search keyword");

        mRecyclerSearchKeyword = (RecyclerView) mView.findViewById(R.id.recycler_search_key_word);
        mRecyclerSearchKeyword.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerSearchKeyword.setLayoutManager(layoutManager);
    }
    private void initialData() {
        updateUI();
    }

    private void updateUI() {
        if (mSearchShareKeywordAdapter == null) {
            mSearchShareKeywordAdapter = new SearchShareKeywordAdapter(mList,
                    getContext().getApplicationContext(), this);
            mRecyclerSearchKeyword.setAdapter(mSearchShareKeywordAdapter);
        } else {
            mSearchShareKeywordAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void iClickPostSearchKeyword(PostSearchKeyword postSearchKeyword) {
        Intent intent = new Intent(getContext(), PostDetailActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_BUNDLE_KEYWORD, postSearchKeyword);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
