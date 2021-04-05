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
//import com.example.democ.adapter.SearchDescriptionAdapter;
//import com.example.democ.model.VegetableSearchDescription;
//
//import java.util.ArrayList;
//
//
//public class SearchByDescriptionFragment extends Fragment {
//
//    private View mView;
//    private TextView mTxtSearchValue;
//    private RecyclerView mRecyclerSearchDescription;
//    private ArrayList<VegetableSearchDescription> mVegetableList;
//    private SearchDescriptionAdapter mSearchDescriptionAdapter;
//
//    private SearchActivity mSearchActivity;
//    private String searchValue = "";
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        mView = inflater.inflate(R.layout.fragment_search_by_description, container, false);
//
//        initialView();
//        initialData();
//        return mView;
//    }
//    private void initialView() {
//
//        mTxtSearchValue = mView.findViewById(R.id.txt_search_value);
//        mTxtSearchValue.setText("bbbbbbbbbbb");
//
//        mSearchActivity = (SearchActivity) getActivity();
//        searchValue = mSearchActivity.getmSearchValue();
//        if (!searchValue.equals("")) {
//            mTxtSearchValue.setText(searchValue);
//        }
//
//        //nhan list vegetable tu search activity
//        mVegetableList = (ArrayList<VegetableSearchDescription>) mSearchActivity.getmListSearchDescription();
//        System.out.println("list vegetable search description");
//        System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
//        System.out.println(mVegetableList.size());
//        System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
//        System.out.println("list vegetable search description");
//
//        mRecyclerSearchDescription = (RecyclerView) mView.findViewById(R.id.recycler_search_description);
//        mRecyclerSearchDescription.setHasFixedSize(true);
////        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),
////                LinearLayoutManager.VERTICAL, false);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
//        mRecyclerSearchDescription.setLayoutManager(layoutManager);
//    }
//    private void initialData() {
//        updateUI();
//    }
//
//    private void updateUI() {
//        if (mSearchDescriptionAdapter == null) {
//            mSearchDescriptionAdapter = new SearchDescriptionAdapter(mVegetableList, getContext().getApplicationContext());
//            mRecyclerSearchDescription.setAdapter(mSearchDescriptionAdapter);
//        } else {
//            mSearchDescriptionAdapter.notifyDataSetChanged();
//        }
//    }
//}
