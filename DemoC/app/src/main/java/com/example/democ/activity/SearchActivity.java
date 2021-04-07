package com.example.democ.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.democ.R;
import com.example.democ.adapter.SearchAccountByNameAdapter;
import com.example.democ.adapter.ViewPagerAdapter;
import com.example.democ.model.AccountSearchByName;
import com.example.democ.model.VegetableData;
import com.example.democ.model.VegetableSearchDescription;
import com.example.democ.model.VegetableSearchKeyword;
import com.example.democ.presenters.SearchAccountByNamePresenter;
import com.example.democ.presenters.SearchByDescriptionPresenter;
import com.example.democ.presenters.SearchByKeywordPresenter;
import com.example.democ.presenters.SearchByNamePresenter;
import com.example.democ.room.entities.User;
import com.example.democ.room.managements.UserManagement;
import com.example.democ.views.SearchAccountByNameView;
import com.example.democ.views.SearchByDescriptionView;
import com.example.democ.views.SearchByKeywordView;
import com.example.democ.views.SearchByNameView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;


public class SearchActivity extends AppCompatActivity implements View.OnClickListener, SearchAccountByNameView {
    //}, SearchByNameView, SearchByDescriptionView, SearchByKeywordView {

    private ViewPager mVpSearch;
    private EditText mEdtSearchValue;
    private LinearLayout mLnlSearch;

    private List<VegetableData> mListSearchName;
    private List<VegetableSearchDescription> mListSearchDescription;
    private List<VegetableSearchKeyword> mListSearchKeyword;
    private SearchByNamePresenter mSearchByNamePresenter;
    private SearchByDescriptionPresenter mSearchByDescriptionPresenter;
    private SearchByKeywordPresenter mSearchByKeywordPresenter;
    private UserManagement mUserManagement;
    private User mUser;
    private String mSearchValue;

    /** search name of account */
    private ArrayList<AccountSearchByName> mListAccount;
    private SearchAccountByNamePresenter mSearchAccountByNamePresenter;

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

//        mVpSearch.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
////                Toast.makeText(getApplication(), "onPageScrolled", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                switch (position) {
//                    case 0:
//                        mSearchValue = "aaa000";
//                        System.out.println(mSearchValue);
//                        break;
//                    case 1:
//                        mSearchValue = "bbb111";
//                        System.out.println(mSearchValue);
//                        break;
//                    case 2:
//                        mSearchValue = "ccc222";
//                        System.out.println(mSearchValue);
//                        break;
//                }
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
////                Toast.makeText(getApplication(), "onPageScrollStateChanged", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    private void initialData() {
 /**
        mListSearchName = new ArrayList<>();
        mListSearchDescription = new ArrayList<>();
        mListSearchKeyword = new ArrayList<>();
*/

/** search vegetable
        mSearchByNamePresenter = new SearchByNamePresenter(getApplication(), this, this);
        mSearchByDescriptionPresenter = new SearchByDescriptionPresenter(getApplication(), this, this);
        mSearchByKeywordPresenter = new SearchByKeywordPresenter(getApplication(), this, this);
*/

        mListAccount = new ArrayList<>();
        mSearchAccountByNamePresenter = new SearchAccountByNamePresenter(getApplication(), this, this);

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
                mSearchValue = mEdtSearchValue.getText().toString();
                System.out.println("==================================================");
                System.out.println(user.getToken());
                System.out.println(mSearchValue);
                System.out.println("==================================================");

                /**
                mSearchByNamePresenter.searchByName(mSearchValue, user.getToken());
                System.out.println("chay search by description presenter");
                mSearchByDescriptionPresenter.searchByDescription(mSearchValue, user.getToken());
                System.out.println("chay search by keyword presenter");
                mSearchByKeywordPresenter.searchByKeyword(mSearchValue, user.getToken());
                 */
                mSearchAccountByNamePresenter.searchAccountByName(mSearchValue, user.getToken());

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
        }
    }


    public String getmSearchValue() {
        return mSearchValue;
    }

    public ArrayList<AccountSearchByName> getmListAccount() {
        return mListAccount;
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

    /** get
    public List<VegetableData> getmListSearchName() {
        return mListSearchName;
    }

    public List<VegetableSearchDescription> getmListSearchDescription() {
        return mListSearchDescription;
    }

    public List<VegetableSearchKeyword> getmListSearchKeyword() {
        return mListSearchKeyword;
    }
     */

    /** implement view
    @Override
    public void searchByNameSuccess(List<VegetableData> vegetableData) {
        System.out.println("*********** list vegetable data searchByNameSuccess *****************");
        this.mListSearchName = vegetableData;
        System.out.println(mListSearchName.size());
        if (mListSearchName.size() > 0) {
            System.out.println("Chay vao if searchByNameSuccess");

            mSearchValue = mEdtSearchValue.getText().toString();
            System.out.println(mSearchValue);
            initialView();

            System.out.println("SEARCH NAME ********************");
            System.out.println("ket thuc if searchByNameSuccess");
            return;
        } else {
            System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
            mListSearchName = new ArrayList<>();
            System.out.println(mListSearchName);
            initialView();
            System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbbb");
        }
        initialView();
    }


    @Override
    public void searchByNameFail() {
        System.out.println("111111111111111111111111111111111111");
        System.out.println("searchByNameFail");
        System.out.println("11111111111111111111111111111111111111");
        mListSearchName = new ArrayList<>();
        initialView();
        return;
    }

    @Override
    public void SearchByDescriptionSuccess(List<VegetableSearchDescription> vegetableData) {
        System.out.println("*********** list vegetable data searchByDescriptionSuccess *****************");
        this.mListSearchDescription = vegetableData;
        System.out.println(mListSearchDescription.size());
        if (mListSearchDescription.size() > 0) {
            System.out.println("Chay vao if searchByDescriptionSuccess");

            mSearchValue = mEdtSearchValue.getText().toString();
            System.out.println(mSearchValue);
            initialView();

            System.out.println("SEARCH DESCRIPTION ***********************");
            System.out.println("ket thuc if searchByDescriptionSuccess");
            return;
        } else {
            System.out.println("ccccccccccccccccccccccccccc");
            mListSearchDescription = new ArrayList<>();
            System.out.println("dddddddddddddddddddddddddddddd");
            initialView();
        }
        initialView();
    }

    @Override
    public void SearchByDescriptionFail() {
        System.out.println("22222222222222222222222222222222222222222");
        System.out.println("SearchByDescriptionFail");
        System.out.println("2222222222222222222222222222222222222222");
        mListSearchDescription = new ArrayList<>();
        initialView();
        return;
    }

    @Override
    public void SearchByKeywordSuccess(List<VegetableSearchKeyword> vegetableSearchKeywords) {
        this.mListSearchKeyword = vegetableSearchKeywords;
        System.out.println("*********** list search key word");
        System.out.println(mListSearchKeyword.size());
        if (mListSearchKeyword.size() > 0) {
            mSearchValue = mEdtSearchValue.getText().toString();
            initialView();
            System.out.println("SEARCH KEYWORD ***********************");
            System.out.println("ket thuc if SearchByKeywordSuccess");
            return;
        } else {
            System.out.println("eeeeeeeeeeeeeeeeeeeeeeeeee");
            mListSearchKeyword = new ArrayList<>();
            System.out.println("fffffffffffffffffffffffffff");
            initialView();
        }
        initialView();
    }

    @Override
    public void SearchByKeywordFail() {
        System.out.println("3333333333333333333333333333333333333333333333");
        System.out.println("SearchByKeywordFail");
        System.out.println("333333333333333333333333333333333333333333333");
        mListSearchKeyword = new ArrayList<>();
        initialView();
        return;
    }
    */
}
