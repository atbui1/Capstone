//package com.example.democ.fragment;
//
//
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.democ.R;
//import com.example.democ.activity.SearchActivity;
//import com.example.democ.adapter.SearchKeywordAdapter;
//import com.example.democ.model.VegetableSearchKeyword;
//
//import java.util.ArrayList;
//
//public class SearchByKeywordFragment extends Fragment {
//    private View mView;
//    private TextView mTxtSearchValue;
//    private RecyclerView mRecyclerSearchKeyword;
//    private SearchActivity mSearchActivity;
//    private String searchValue = "";
//    private ArrayList<VegetableSearchKeyword> mVegetableList;
//    private SearchKeywordAdapter mSearchKeywordAdapter;
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        mView = inflater.inflate(R.layout.fragment_search_by_keyword, container, false);
//
//        initialView();
//        initialData();
//        return mView;
//    }
//
//    private void initialView() {
//        mTxtSearchValue = (TextView) mView.findViewById(R.id.txt_search_value);
//        mTxtSearchValue.setText("ccccccccccccc");
//
//        mSearchActivity = (SearchActivity) getActivity();
//        searchValue = mSearchActivity.getmSearchValue();
//        if (!searchValue.equals("")) {
//            mTxtSearchValue.setText(searchValue);
//        }
//
//        //nhan list vegetable tu search activity
//        mVegetableList = (ArrayList<VegetableSearchKeyword>) mSearchActivity.getmListSearchKeyword();
//        System.out.println("list vegetable search keyword");
//        System.out.println("CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC");
//        System.out.println(mVegetableList.size());
//        System.out.println("CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC");
//        System.out.println("list vegetable search keyword");
//
//        mRecyclerSearchKeyword = (RecyclerView) mView.findViewById(R.id.recycler_search_key_word);
//        mRecyclerSearchKeyword.setHasFixedSize(true);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
//        mRecyclerSearchKeyword.setLayoutManager(layoutManager);
//    }
//    private void initialData() {
//        updateUI();
//    }
//
//    private void updateUI() {
//        if (mSearchKeywordAdapter == null) {
//            mSearchKeywordAdapter = new SearchKeywordAdapter(mVegetableList, getContext().getApplicationContext());
//            mRecyclerSearchKeyword.setAdapter(mSearchKeywordAdapter);
//        } else {
//            mSearchKeywordAdapter.notifyDataSetChanged();
//        }
//    }
//}
