package com.example.democ.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.democ.R;
import com.example.democ.adapter.PostByAccountAdapter;
import com.example.democ.adapter.PosterAccountAdapter;
import com.example.democ.iclick.IClickPostAccount;
import com.example.democ.model.AccountSearchByName;
import com.example.democ.model.AddFriendRequest;
import com.example.democ.model.PostData;
import com.example.democ.presenters.GetAllShareByIdPresenter;
import com.example.democ.presenters.PersonalPresenter;
import com.example.democ.presenters.SendAddFriendPresenter;
import com.example.democ.room.entities.User;
import com.example.democ.views.GetAllShareByIdView;
import com.example.democ.views.PersonalView;
import com.example.democ.views.SendAddFriendView;

import java.util.ArrayList;
import java.util.List;

public class PosterProfileActivity extends AppCompatActivity implements View.OnClickListener, SendAddFriendView, PersonalView,
        GetAllShareByIdView, IClickPostAccount {

    private LinearLayout mLnlBackProfileHome;
    private TextView mTxtPosterFullName, mTxtTotalPosts;
    private Button mBtnAddFriend;
    private SendAddFriendPresenter mSendAddFriendPresenter;
    private String mStrNameOfShare, mStrAccountUserId, mStrAccountShareId;
    private PersonalPresenter mPersonalPresenter;
    private User mUser;
    private final static String ADD_FRIEND = "ket ban";
    private final static String SEARCH_ACCOUNT = "SearchAccount";

    //RecyclerView
//    private PostByAccountAdapter mPostByAccountAdapter;
    private PosterAccountAdapter mPosterAccountAdapter;
    private RecyclerView mRecyclerView;
    private GetAllShareByIdPresenter mGetAllShareByIdPresenter;
    private ArrayList<PostData> mListPost;
    private int mIntTotalPosts = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_poster_profile);
        setContentView(R.layout.activity_poster_profile);
        initialView();
        initialData();
    }

    private void initialView() {
        mPersonalPresenter = new PersonalPresenter(getApplicationContext(), this);
        getDataPostExchange();
        getDataSearchAccount();

        mPersonalPresenter.getInfoPersonal();

        mLnlBackProfileHome = (LinearLayout) findViewById(R.id.lnl_back_profile_home);
        mLnlBackProfileHome.setOnClickListener((View.OnClickListener) this);
        mTxtPosterFullName = (TextView) findViewById(R.id.txt_poster_full_name);
        mTxtPosterFullName.setText(mStrNameOfShare);

        //add friend
        mBtnAddFriend = (Button) findViewById(R.id.btn_add_friend);
        mBtnAddFriend.setOnClickListener(this);
        mBtnAddFriend.setText(ADD_FRIEND);
        mSendAddFriendPresenter = new SendAddFriendPresenter(getApplication(), getApplicationContext(), this);

//        RecyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_share_by_account);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setNestedScrollingEnabled(false);

        mGetAllShareByIdPresenter = new GetAllShareByIdPresenter(getApplication(), getApplicationContext(), this);
        mListPost = new ArrayList<>();
        mTxtTotalPosts = (TextView) findViewById(R.id.txt_total_posts);
    }

    private void initialData() {
//        mGetAllShareByIdPresenter.getAllShareById(mStrAccountUserId, mUser.getToken());

    }

    private void getDataSearchAccount() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        AccountSearchByName accountSearchByName = (AccountSearchByName) bundle.getSerializable(SEARCH_ACCOUNT);
        if (accountSearchByName != null) {
            mStrAccountShareId = accountSearchByName.getAccountId();
            mStrNameOfShare = accountSearchByName.getAccountName();
            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXx");
            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXx");
            System.out.println("xxxxxxxxxxxx: " + mStrAccountShareId);
            System.out.println("xxxxxxxxxxxx: " + mStrNameOfShare);
            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXx");
            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXx");
        }
    }
    private void getDataPostExchange() {
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
//        mStrNameOfShare = (String) intent.getSerializableExtra("OWNER_PROFILE");
        if (bundle != null) {
            mStrNameOfShare = bundle.getString("NAME_SHARE");
            mStrAccountUserId = bundle.getString("ACCOUNT_ID");
            mStrAccountShareId = bundle.getString("ACCOUNT_SHARE");
        }
    }
    private void clickSendAddFriend() {
        AddFriendRequest addFriendRequest = new AddFriendRequest(mUser.getAccountId(), mStrAccountShareId);
        mSendAddFriendPresenter.sendAddFriend(addFriendRequest, mUser.getToken());
    }
    public void updateUI() {
//        if (mPostByAccountAdapter == null) {
//            mPostByAccountAdapter = new PostByAccountAdapter(mListPost, getApplicationContext(), this);
//            mRecyclerView.setAdapter(mPostByAccountAdapter);
//        } else {
//            mPostByAccountAdapter.notifyDataSetChanged();
//        }
        if (mPosterAccountAdapter == null) {
            mPosterAccountAdapter = new PosterAccountAdapter(mListPost, getApplicationContext(), this);
            mRecyclerView.setAdapter(mPosterAccountAdapter);
        } else {
            mPosterAccountAdapter.notifyDataSetChanged();
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lnl_back_profile_home:
                finish();
                break;
            case R.id.btn_add_friend:
                Toast.makeText(getApplicationContext(), "send add friend", Toast.LENGTH_SHORT).show();
                clickSendAddFriend();
                break;
        }
    }

    @Override
    public void sendAddFriendSuccess(AddFriendRequest addFriendRequest) {
        mBtnAddFriend.setText("Da gui kb");
        System.out.println("SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS");
        System.out.println("SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS");
        System.out.println("sendAddFriendSuccess");
        System.out.println("SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS");
        System.out.println("SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS");
    }

    @Override
    public void sendAddFriendFail() {
        System.out.println("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF");
        System.out.println("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF");
        System.out.println("sendAddFriendFail");
        System.out.println("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF");
        System.out.println("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF");
    }

    @Override
    public void showInfoPersonal(User user) {
        mUser = user;
        AddFriendRequest addFriendRequest = new AddFriendRequest(mStrAccountUserId, mStrAccountShareId);
        mSendAddFriendPresenter.sendAddFriend(addFriendRequest, user.getToken());
        mGetAllShareByIdPresenter.getAllShareById(mStrAccountShareId, mUser.getToken());
    }

    @Override
    public void getAllShareByIdSuccess(List<PostData> postDataList) {
        mListPost = (ArrayList<PostData>) postDataList;
        if (mListPost.size() > 0) {
            mIntTotalPosts = mListPost.size();
            System.out.println("TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT");
            System.out.println("TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT");
            System.out.println("mIntTotalPosts: " + mIntTotalPosts);
            System.out.println("TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT");
            System.out.println("TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT");
            mTxtTotalPosts.setText(String.valueOf(mIntTotalPosts));
            updateUI();
        }
    }

    @Override
    public void getAllShareByIdFail() {

    }

    @Override
    public void clickPostAccount(PostData postData) {
        if (postData.getStatius() == 1) {

        } else if (postData.getStatius() == 2) {

        }
    }

    @Override
    public void clickDeletePostAccount(PostData postData, int positionDelete) {

    }
}
