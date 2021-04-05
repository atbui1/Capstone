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
//import com.example.democ.adapter.SearchNameAdapter;
//import com.example.democ.model.VegetableData;
//
//import java.util.ArrayList;
//
//
//public class SearchByNameFragment extends Fragment {
//
//    private View mView;
//    private RecyclerView mRecyclerSearchName;
//    private ArrayList<VegetableData> mVegetableList;
//    private SearchNameAdapter mSearchNameAdapter;
//
//    private SearchActivity mSearchActivity;
//    private TextView mTxtSearchValue;
//    private String searchValue = "";
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        mView = inflater.inflate(R.layout.fragment_search_by_name, container, false);
//
//        initialView();
//        initialData();
//        return mView;
//    }
//
//    private void initialView() {
//
//        mTxtSearchValue = mView.findViewById(R.id.txt_search_value);
//        mTxtSearchValue.setText("aaaaaaaaaaaa");
//
//        mSearchActivity = (SearchActivity) getActivity();
//        searchValue = mSearchActivity.getmSearchValue();
//        if (!searchValue.equals("")) {
//            mTxtSearchValue.setText(searchValue);
//        }
//
//        //nhan list vegetable tu search activity
//        mVegetableList = (ArrayList<VegetableData>) mSearchActivity.getmListSearchName();
//        System.out.println("list vegetable search name");
//        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
//        System.out.println(mVegetableList.size());
//        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
//        System.out.println("list vegetable search name");
//
//
//
//        mRecyclerSearchName = (RecyclerView) mView.findViewById(R.id.recycler_search_name);
//        mRecyclerSearchName.setHasFixedSize(true);
////        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),
////                LinearLayoutManager.VERTICAL, false);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
//        mRecyclerSearchName.setLayoutManager(layoutManager);
//    }
//
//    private void initialData() {
//        updateUI();
//    }
//
//    private void updateUI() {
//        System.out.println(mSearchNameAdapter);
//        if (mSearchNameAdapter == null) {
//            mSearchNameAdapter = new SearchNameAdapter(mVegetableList, getContext().getApplicationContext());
//            mRecyclerSearchName.setAdapter(mSearchNameAdapter);
//        } else {
//            mSearchNameAdapter.notifyDataSetChanged();
//        }
//
//    }
//
//}
