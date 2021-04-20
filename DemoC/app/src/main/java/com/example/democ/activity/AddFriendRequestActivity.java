package com.example.democ.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.example.democ.R;
import com.example.democ.adapter.AddFriendAdapter;
import com.example.democ.iclick.IClickAddFriend;
import com.example.democ.model.Account;
import com.example.democ.model.AddFriendRequest;
import com.example.democ.presenters.GetAddFriendRequestPresenter;
import com.example.democ.presenters.GetInfoAccountPresenter;
import com.example.democ.presenters.PersonalPresenter;
import com.example.democ.presenters.ReplyFriendRequestPresenter;
import com.example.democ.room.entities.User;
import com.example.democ.views.GetAddFriendRequestView;
import com.example.democ.views.GetInfoAccountView;
import com.example.democ.views.PersonalView;
import com.example.democ.views.ReplyFriendRequestView;

import java.util.ArrayList;
import java.util.List;

public class AddFriendRequestActivity extends AppCompatActivity implements View.OnClickListener, PersonalView, GetAddFriendRequestView,
        IClickAddFriend, ReplyFriendRequestView {

    private LinearLayout mLnlBack;
    private RecyclerView mRecyclerView;
    private AddFriendAdapter mAddFriendAdapter;
    private ArrayList<AddFriendRequest> mListAddFriend;
    private GetAddFriendRequestPresenter mGetAddFriendRequestPresenter;
    private PersonalPresenter mPersonalPresenter;
    private ReplyFriendRequestPresenter mReplyFriendRequestPresenter;
    private User mUser;
    private static int mIntPosition = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend_request);
        
        initialView();
        initialData();
    }

    private void initialView() {

        mListAddFriend = new ArrayList<>();

        mLnlBack = (LinearLayout) findViewById(R.id.lnl_back);
        mLnlBack.setOnClickListener(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_request_add_friend);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);

        mPersonalPresenter = new PersonalPresenter(getApplicationContext(), this);
        mPersonalPresenter.getInfoPersonal();
        mGetAddFriendRequestPresenter = new GetAddFriendRequestPresenter(getApplication(), getApplicationContext(), this);
        mReplyFriendRequestPresenter = new ReplyFriendRequestPresenter(getApplication(), getApplicationContext(), this);

    }

    private void initialData() {
//        mPersonalPresenter.getInfoPersonal();
    }

    public void updateUI() {
        if (mAddFriendAdapter == null) {
            mAddFriendAdapter = new AddFriendAdapter(mListAddFriend, this);
            mRecyclerView.setAdapter(mAddFriendAdapter);
        } else {
            mAddFriendAdapter.notifyDataSetChanged();
        }
    }

    public void clickBack() {
        Intent intent = new Intent(AddFriendRequestActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    //toolbar


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
//        switch (item.getItemId()) {
//            case R.id.tl_tool_bar:
//                finish();
//                return true;
//                default:
//                    return super.onOptionsItemSelected(item);
//        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lnl_back:
//                clickBack();
                finish();
                break;
        }
    }

    @Override
    public void showInfoPersonal(User user) {
        mUser = user;
        mGetAddFriendRequestPresenter.getAddFriendRequest(user.getToken());
    }

    @Override
    public void getAddFriendRequestSuccess(List<AddFriendRequest> addFriendRequests) {
        mListAddFriend = (ArrayList<AddFriendRequest>) addFriendRequests;
        if (mListAddFriend.size() > 0) {
            updateUI();
        } else {
            initialView();
        }
    }

    @Override
    public void getAddFriendRequestFail() {

    }

    @Override
    public void clickFriendAdmit(AddFriendRequest addFriendRequest, int position) {
        mIntPosition = position;
        mReplyFriendRequestPresenter.replyFriendRequest(addFriendRequest.getId(), 2, mUser.getToken());
    }

    @Override
    public void clickFriendReject(AddFriendRequest addFriendRequest, int position) {
        mIntPosition = position;
        mReplyFriendRequestPresenter.replyFriendRequest(addFriendRequest.getId(), 3, mUser.getToken());
    }

    @Override
    public void replyFriendRequestSuccess() {
        mListAddFriend.remove(mIntPosition);
        mAddFriendAdapter.notifyItemRemoved(mIntPosition);
    }

    @Override
    public void replyFriendRequestFail() {

    }
}
