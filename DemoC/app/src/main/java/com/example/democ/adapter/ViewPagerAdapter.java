package com.example.democ.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

//import com.example.democ.fragment.SearchShareByDescriptionFragment;
//import com.example.democ.fragment.SearchShareByKeywordFragment;
import com.example.democ.fragment.SearchByNameAccountFragment;
//import com.example.democ.fragment.SearchByNameFragment;
import com.example.democ.fragment.SearchShareByDescriptionFragment;
import com.example.democ.fragment.SearchShareByKeywordFragment;
import com.example.democ.fragment.SearchShareByNameFragment;
import com.example.democ.model.VegetableData;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private String listTabName [] = {"Mô tả", "Tên rau", "Công dụng"};

    private SearchAccountByNameAdapter mSearchAccountByNameAdapter;

    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new SearchShareByDescriptionFragment();
            case 1:
                return new SearchShareByNameFragment();
            case 2:
                return new SearchShareByKeywordFragment();
//            case 3:
//                return new SearchByNameAccountFragment();
                default:
                    return new SearchShareByDescriptionFragment();
        }
    }

    @Override
    public int getCount() {
        return listTabName.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return listTabName[position];
    }
}
