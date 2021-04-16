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
import com.example.democ.activity.PosterProfileActivity;
import com.example.democ.activity.SearchActivity;
import com.example.democ.adapter.SearchAccountByNameAdapter;
import com.example.democ.iclick.IClickAccountName;
import com.example.democ.model.AccountSearchByName;

import java.util.ArrayList;
import java.util.List;


public class SearchByNameAccountFragment extends Fragment implements IClickAccountName {

    private View mView;
    private TextView mTxtSearchValue;
    private RecyclerView mRecyclerView;

    private SearchActivity mSearchActivity;
    private String searchValue = "";
    private ArrayList<AccountSearchByName> mListAccount;
    private SearchAccountByNameAdapter mSearchAccountByNameAdapter;
    private IClickAccountName mIClickAccountName;

    public static String SEARCH_ACCOUNT = "SearchAccount";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_search_by_name_account, container, false);
        initialView();
        initialData();
        return mView;
    }

    private void initialView() {
        mTxtSearchValue = mView.findViewById(R.id.txt_search_value);
        mTxtSearchValue.setText("aaaaaaaaaaaa");

        /** get search value from searchActivity */
        mSearchActivity = (SearchActivity) getActivity();
        searchValue = mSearchActivity.getmSearchValue();
        if (!searchValue.equals("")) {
            mTxtSearchValue.setText(searchValue);
        }
        /** get list account from searchActivity */
        mListAccount = mSearchActivity.getmListAccount();
        if (mListAccount.size() == 0) {
            mTxtSearchValue.setText("Không có: " + searchValue);
        }

        mRecyclerView = (RecyclerView) mView.findViewById(R.id.recycler_account_name);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
    }

    private void initialData() {
        updateUI();
    }

    private void updateUI() {
        //khoi tao adapter
        if (mSearchAccountByNameAdapter == null) {
            mSearchAccountByNameAdapter = new SearchAccountByNameAdapter(mListAccount, this);
            mRecyclerView.setAdapter(mSearchAccountByNameAdapter);
        } else {
            mSearchAccountByNameAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void clickAccountName(AccountSearchByName accountSearchByName) {
        Intent intent = new Intent(getContext().getApplicationContext(), PosterProfileActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        Bundle bundle = new Bundle();
        bundle.putSerializable(SEARCH_ACCOUNT, accountSearchByName);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
