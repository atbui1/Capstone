package com.example.democ.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

//import com.example.democ.fragment.SearchByDescriptionFragment;
//import com.example.democ.fragment.SearchByKeywordFragment;
import com.example.democ.fragment.SearchByNameAccountFragment;
//import com.example.democ.fragment.SearchByNameFragment;
import com.example.democ.model.VegetableData;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private String listTabName [] = {"search name account"};
//    private String listTabName [] = {"search name", "search description", "search keyword"};
//    private SearchByNameFragment mSearchName;
//    private SearchByDescriptionFragment mSearchDescription;
//    private SearchByKeywordFragment mSearchByKeyword;
    private SearchAccountByNameAdapter mSearchAccountByNameAdapter;

//    public ViewPagerAdapter(@NonNull FragmentManager fm) {
//        super(fm);
////        mSearchName = new SearchByNameFragment();
////        mSearchDescription = new SearchByDescriptionFragment();
////        mSearchByKeyword = new SearchByKeywordFragment();
//    }


    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
//            case 0:
//                System.out.println("0000000000000000000000000000");
//                return new SearchByNameFragment();
//            case 1:
//                System.out.println("1111111111111111111111111111");
//                return new SearchByDescriptionFragment();
//            case 2:
//                System.out.println("22222222222222222222222222222");
//                return new SearchByKeywordFragment();
//                default:
//                    System.out.println("DDDDDDDDDDDDDD  0000000000000   DDDDDDDDDDD");
//                    return new SearchByNameFragment();
            case 0:
                return new SearchByNameAccountFragment();
                default:
                    return new SearchByNameAccountFragment();
        }
//        return null;
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
