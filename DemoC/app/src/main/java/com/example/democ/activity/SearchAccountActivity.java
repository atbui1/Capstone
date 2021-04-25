package com.example.democ.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.democ.PersonalFragment;
import com.example.democ.R;
import com.example.democ.adapter.SearchAccountByNameAdapter;
import com.example.democ.iclick.IClickAccountName;
import com.example.democ.model.AccountSearchByName;
import com.example.democ.presenters.PersonalPresenter;
import com.example.democ.presenters.SearchAccountByNamePresenter;
import com.example.democ.room.entities.User;
import com.example.democ.views.PersonalView;
import com.example.democ.views.SearchAccountByNameView;

import java.util.ArrayList;
import java.util.List;

public class SearchAccountActivity extends AppCompatActivity implements View.OnClickListener, IClickAccountName,
        PersonalView, SearchAccountByNameView {

    private final static String SEARCH_ACCOUNT = "SearchAccount";
    private final static String KEY_FLAG = "KEY_FLAG";

    private LinearLayout mLnlBack, mLnlSearch, mLnlSearchResult;
    private EditText mEdtSearchValue;
    private TextView mTxtSearchResult;
    private RecyclerView mRecyclerView;

    private SearchAccountByNameAdapter mSearchAccountByNameAdapter;

    private List<AccountSearchByName> mListAccount;
    private PersonalPresenter mPersonalPresenter;
    private SearchAccountByNamePresenter mSearchAccountByNamePresenter;
    private User mUser;

    private String mStrSearchValue = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_account);

        initialView();
        initialData();
    }


    private void initialView() {
        mLnlBack = findViewById(R.id.lnl_back);
        mLnlBack.setOnClickListener(this);
        mLnlSearch = findViewById(R.id.lnl_search_account);
        mLnlSearch.setOnClickListener(this);
        mLnlSearchResult = findViewById(R.id.lnl_search_result);
        mLnlSearchResult.setVisibility(View.GONE);
        mEdtSearchValue = findViewById(R.id.edt_search_value);
        mTxtSearchResult = findViewById(R.id.txt_search_result);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_search_account);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);

//        mStrSearchValue = mEdtSearchValue.getText().toString().trim();
//        mListAccount = new ArrayList<>();
    }
    private void initialData() {
        mPersonalPresenter = new PersonalPresenter(getApplicationContext(), this);
        mPersonalPresenter.getInfoPersonal();
        mSearchAccountByNamePresenter = new SearchAccountByNamePresenter(getApplication(), getApplicationContext(), this);
    }

    private void updateUI() {
        mSearchAccountByNameAdapter = new SearchAccountByNameAdapter( mListAccount, this);
        mRecyclerView.setAdapter(mSearchAccountByNameAdapter);
    }

    private void searchAccountApi() {
        mStrSearchValue = mEdtSearchValue.getText().toString().trim();
        System.out.println("aaaaaaaaaaaaaaaa");
        System.out.println("aa: " + mStrSearchValue);
        System.out.println("aaaaaaaaaaaaaaaa");
        mSearchAccountByNamePresenter.searchAccountByName(mStrSearchValue, mUser.getToken());
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lnl_back:
                finish();
                break;
            case R.id.lnl_search_account:
                searchAccountApi();
                break;
        }
    }

    @Override
    public void clickAccountName(AccountSearchByName accountSearchByName) {
        if (mUser.getAccountId().equals(accountSearchByName.getAccountId())) {
            Intent intent = new Intent(SearchAccountActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            Bundle bundle = new Bundle();
            bundle.putInt(KEY_FLAG, 4);
            bundle.putSerializable(SEARCH_ACCOUNT, accountSearchByName);
            intent.putExtras(bundle);
            startActivity(intent);
        } else {
            Intent intent = new Intent(SearchAccountActivity.this, PosterProfileActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            Bundle bundle = new Bundle();
            bundle.putSerializable(SEARCH_ACCOUNT, accountSearchByName);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }


    @Override
    public void showInfoPersonal(User user) {
        mUser = user;
    }

    @Override
    public void searchAccountByNameSuccess(List<AccountSearchByName> accountSearchByNames) {
        mListAccount = new ArrayList<>();
        mListAccount = accountSearchByNames;
        System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
        System.out.println("bb: " +mListAccount);
        System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
        if (mListAccount != null) {
            updateUI();
            mLnlSearchResult.setVisibility(View.GONE);
            if (mListAccount.size() == 0) {
                mLnlSearchResult.setVisibility(View.VISIBLE);
                mStrSearchValue = mEdtSearchValue.getText().toString().trim();
                mTxtSearchResult.setText("Không tìm thấy người dùng: " + mStrSearchValue);
            }
        }
    }

    @Override
    public void SearchAccountByNameFail() {

    }
}
