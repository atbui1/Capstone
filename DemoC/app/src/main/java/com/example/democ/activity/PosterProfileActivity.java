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
        GetAllShareByIdView {

    private LinearLayout mLnlBackProfileHome;
    private TextView mTxtPosterFullName;
    private Button mBtnAddFriend;
    private SendAddFriendPresenter mSendAddFriendPresenter;
    private String mStrNameOfShare, mStrAccountUserId, mStrAccountShareId;
    private PersonalPresenter mPersonalPresenter;
    private User mUser;
    private final static String ADD_FRIEND = "ket ban";

    //RecyclerView
    private RecyclerView mRecyclerView;
    private GetAllShareByIdPresenter mGetAllShareByIdPresenter;
    private ArrayList<PostData> mListPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_poster_profile);
        setContentView(R.layout.activity_poster_profile);
        initialView();
        initialData();
    }

    private void initialView() {
        mLnlBackProfileHome = (LinearLayout) findViewById(R.id.lnl_back_profile_home);
        mLnlBackProfileHome.setOnClickListener((View.OnClickListener) this);

        mTxtPosterFullName = (TextView) findViewById(R.id.txt_poster_full_name);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
//        mStrNameOfShare = (String) intent.getSerializableExtra("OWNER_PROFILE");
        mStrNameOfShare = bundle.getString("NAME_SHARE");
        mStrAccountUserId = bundle.getString("ACCOUNT_ID");
        mStrAccountShareId = bundle.getString("ACCOUNT_SHARE");
        mTxtPosterFullName.setText(mStrNameOfShare);

        //add friend
        mBtnAddFriend = (Button) findViewById(R.id.btn_add_friend);
        mBtnAddFriend.setOnClickListener(this);
        mBtnAddFriend.setText(ADD_FRIEND);
        mSendAddFriendPresenter = new SendAddFriendPresenter(getApplication(), getApplicationContext(), this);

        mPersonalPresenter = new PersonalPresenter(getApplicationContext(), this);

//        RecyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_share_by_account);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setNestedScrollingEnabled(false);

        mGetAllShareByIdPresenter = new GetAllShareByIdPresenter(getApplication(), getApplicationContext(), this);
        mListPost = new ArrayList<>();
    }

    private void initialData() {
        mPersonalPresenter.getInfoPersonal();
    }

    private void clickSendAddFriend() {
        AddFriendRequest addFriendRequest = new AddFriendRequest(mStrAccountUserId, mStrAccountShareId);
        mSendAddFriendPresenter.sendAddFriend(addFriendRequest, mUser.getToken());
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
    }

    @Override
    public void sendAddFriendFail() {

    }

    @Override
    public void showInfoPersonal(User user) {
        mUser = user;
//        AddFriendRequest addFriendRequest = new AddFriendRequest(mStrAccountUserId, mStrAccountShareId);
//        mSendAddFriendPresenter.sendAddFriend(addFriendRequest, user.getToken());
    }

    @Override
    public void getAllShareByIdSuccess(List<PostData> postDataList) {

    }

    @Override
    public void getAllShareByIdFail() {

    }
}
