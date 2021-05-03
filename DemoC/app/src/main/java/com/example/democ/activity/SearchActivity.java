package com.example.democ.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.democ.R;
import com.example.democ.adapter.SearchAccountByNameAdapter;
import com.example.democ.adapter.ViewPagerAdapter;
import com.example.democ.fragment.SearchShareByDescriptionFragment;
import com.example.democ.model.AccountSearchByName;
import com.example.democ.model.PostData;
import com.example.democ.model.PostSearchDescription;
import com.example.democ.model.PostSearchKeyword;
import com.example.democ.model.PostSearchName;
import com.example.democ.model.VegetableData;
import com.example.democ.model.VegetableSearchDescription;
import com.example.democ.model.VegetableSearchKeyword;
import com.example.democ.presenters.SearchAccountByNamePresenter;
import com.example.democ.presenters.SearchByDescriptionPresenter;
import com.example.democ.presenters.SearchByKeywordPresenter;
import com.example.democ.presenters.SearchByNamePresenter;
import com.example.democ.presenters.SearchShareByDescriptionPresenter;
import com.example.democ.presenters.SearchShareByKeywordPresenter;
import com.example.democ.presenters.SearchShareByNamePresenter;
import com.example.democ.room.entities.User;
import com.example.democ.room.managements.UserManagement;
import com.example.democ.views.SearchAccountByNameView;
import com.example.democ.views.SearchByDescriptionView;
import com.example.democ.views.SearchByKeywordView;
import com.example.democ.views.SearchByNameView;
import com.example.democ.views.SearchShareByDescriptionView;
import com.example.democ.views.SearchShareByKeywordView;
import com.example.democ.views.SearchShareByNameView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;


public class SearchActivity extends AppCompatActivity implements View.OnClickListener, SearchAccountByNameView,
        SearchShareByDescriptionView, SearchShareByNameView, SearchShareByKeywordView {

    private ViewPager mVpSearch;
    private EditText mEdtSearchValue;
    private LinearLayout mLnlSearch, mLnlBack;

    private List<PostSearchDescription> mListSearchDescription;
    private List<PostSearchName> mListSearchName;
    private List<PostSearchKeyword> mListSearchKeyword;
    private UserManagement mUserManagement;
    private String mSearchValue;

    /** search name of account */
    private ArrayList<AccountSearchByName> mListAccount;
    private SearchAccountByNamePresenter mSearchAccountByNamePresenter;
    private SearchShareByDescriptionPresenter mSearchShareByDescriptionPresenter;
    private SearchShareByNamePresenter mSearchShareByNamePresenter;
    private SearchShareByKeywordPresenter mSearchShareByKeywordPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

//        initialView();
        initialData();
    }

    private void initialView() {


        mVpSearch = (ViewPager) findViewById(R.id.vp_search);
        mVpSearch.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT));
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tl_search);
        tabLayout.setupWithViewPager(mVpSearch);

        mLnlBack = (LinearLayout) findViewById(R.id.lnl_back);
        mLnlBack.setOnClickListener(this);
    }

    private void initialData() {

        mListAccount = new ArrayList<>();
        mListSearchDescription = new ArrayList<>();
        mSearchAccountByNamePresenter = new SearchAccountByNamePresenter(getApplication(), this, this);
        mSearchShareByDescriptionPresenter = new SearchShareByDescriptionPresenter(getApplication(), this, this);
        mSearchShareByNamePresenter = new SearchShareByNamePresenter(getApplication(), this, this);
        mSearchShareByKeywordPresenter = new SearchShareByKeywordPresenter(getApplication(), this, this);

        mUserManagement = new UserManagement(getApplication());

        mEdtSearchValue = (EditText) findViewById(R.id.edt_search_value);
        mLnlSearch = (LinearLayout) findViewById(R.id.lnl_search);
        mLnlSearch.setOnClickListener(this);
        mSearchValue = mEdtSearchValue.getText().toString();

    }

    private void searchVegetable() {
        mUserManagement.getmUserInfo(new UserManagement.OnDataCallBackUser() {
            @Override
            public void onDataSuccess(User user) {
                mSearchValue = mEdtSearchValue.getText().toString().trim();
                System.out.println("==================================================");
                System.out.println(user.getToken());
                System.out.println(mSearchValue);
                System.out.println("==================================================");

//                mSearchAccountByNamePresenter.searchAccountByName(mSearchValue, user.getToken());
                mSearchShareByDescriptionPresenter.searchShareByDescription(mSearchValue, user.getToken());
                mSearchShareByNamePresenter.searchShareByName(mSearchValue, user.getToken());
                mSearchShareByKeywordPresenter.searchShareByKeyword(mSearchValue, user.getToken());

            }

            @Override
            public void onDataFail() {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lnl_search:
                System.out.println("********************** btn img search name *********************************");
                searchVegetable();
                break;
            case R.id.lnl_back:
                Intent intentHome = new Intent(SearchActivity.this, MainActivity.class);
                startActivity(intentHome);
                break;
        }
    }


    public String getmSearchValue() {
        return mSearchValue;
    }

    public ArrayList<AccountSearchByName> getmListAccount() {
        return mListAccount;
    }

    public List<PostSearchDescription> getmListSearchDescription() {
        return mListSearchDescription;
    }

    public List<PostSearchName> getmListSearchName() {
        return mListSearchName;
    }

    public List<PostSearchKeyword> getmListSearchKeyword() {
        return mListSearchKeyword;
    }

    @Override
    public void searchAccountByNameSuccess(List<AccountSearchByName> accountSearchByNames) {
        mListAccount = (ArrayList<AccountSearchByName>) accountSearchByNames;
        initialView();
    }

    @Override
    public void SearchAccountByNameFail() {
        initialView();
    }


    @Override
    public void SearchShareByDescriptionSuccess(List<PostSearchDescription> postSearchDescriptions) {
        mListSearchDescription = postSearchDescriptions;
        initialView();
    }

    @Override
    public void SearchShareByDescriptionFail() {
        initialView();
    }


    @Override
    public void SearchShareByNameSuccess(List<PostSearchName> postSearchNames) {
        mListSearchName = postSearchNames;
        initialView();
    }

    @Override
    public void SearchShareByNameFail() {
        initialView();
    }

    @Override
    public void SearchShareByKeywordSuccess(List<PostSearchKeyword> postSearchKeywords) {
        mListSearchKeyword = postSearchKeywords;
        initialView();
    }

    @Override
    public void SearchShareByKeywordFail() {
        initialView();
    }
}
