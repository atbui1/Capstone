package com.example.democ.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.democ.R;
import com.example.democ.adapter.FriendAdapter;
import com.example.democ.iclick.IClickFriend;
import com.example.democ.model.FriendData;
import com.example.democ.presenters.DeleteFriendPresenter;
import com.example.democ.presenters.GetAllFriendPresenter;
import com.example.democ.presenters.PersonalPresenter;
import com.example.democ.room.entities.User;
import com.example.democ.views.DeleteFriendView;
import com.example.democ.views.GetAllFriendView;
import com.example.democ.views.PersonalView;

import java.util.List;

public class FriendActivity extends AppCompatActivity implements View.OnClickListener, IClickFriend, PersonalView, GetAllFriendView,
        DeleteFriendView {

    private RecyclerView mRecyclerView;
    private LinearLayout mLnlBack;
    private FriendAdapter mFriendAdapter;

    private PersonalPresenter mPersonalPresenter;
    private GetAllFriendPresenter mGetAllFriendPresenter;
    private DeleteFriendPresenter mDeleteFriendPresenter;
    private List<FriendData> mListFriendData;
    private User mUser;
    private int mIntPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);

        initialView();
        initialData();
    }

    private void initialView() {
        mLnlBack = (LinearLayout) findViewById(R.id.lnl_back);
        mLnlBack.setOnClickListener(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_friend);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);
    }

    private void initialData() {
        mPersonalPresenter = new PersonalPresenter(getApplicationContext(), this);
        mPersonalPresenter.getInfoPersonal();
        mGetAllFriendPresenter = new GetAllFriendPresenter(getApplication(), getApplicationContext(), this);
        mDeleteFriendPresenter = new DeleteFriendPresenter(getApplication(), getApplicationContext(), this);
    }

    private void updateUI() {
        if (mFriendAdapter == null) {
            mFriendAdapter = new FriendAdapter(mListFriendData, this);
            mRecyclerView.setAdapter(mFriendAdapter);
        } else {
            mFriendAdapter.notifyDataSetChanged();
        }
    }

    private void openFriendProfile() {
//        Intent intent = new Intent(FriendActivity.this, PosterProfileActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
//        Bundle bundle = new Bundle();
//        mStrNameOfShare = shareData.getFullName();
//        mAccountIdOfShare = shareData.getAccountId();
//        bundle.putString("ACCOUNT_ID", mAccountIdUser);
//        bundle.putString("ACCOUNT_SHARE", mAccountIdOfShare);
//        bundle.putString("NAME_SHARE", mStrNameOfShare);
//        intent.putExtras(bundle);
////            intent.putExtra("OWNER_PROFILE", posterName);
//        startActivity(intent);
    }
    /*dialog delete*/
    private void showDialogDelete(final int idRequest, String friendName) {
        final Dialog dialog = new Dialog(FriendActivity.this);
        dialog.setContentView(R.layout.dialog_delete_garden);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        TextView txtQuantity;
        Button btnClose, btnDelete;
        btnClose = (Button) dialog.findViewById(R.id.btn_delete_no);
        btnDelete = (Button) dialog.findViewById(R.id.btn_delete_yes);
        txtQuantity = (TextView) dialog.findViewById(R.id.txt_content_delete);
        txtQuantity.setText("Bạn có muốn xóa " + friendName + " Khỏi danh sách bạn bè");


        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDeleteFriendPresenter.deleteFriend(idRequest, mUser.getToken());
                dialog.dismiss();
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lnl_back:
                finish();
                break;
        }
    }

    @Override
    public void clickFriend(FriendData friendData) {
        /*show friend*/
        openFriendProfile();
    }

    @Override
    public void clickDeleteFriend(FriendData friendData, int position) {
        /*delete friend*/
        mIntPosition = position;
        showDialogDelete(friendData.getId(), friendData.getFriendName());
    }

    @Override
    public void showInfoPersonal(User user) {
        mUser = user;
        mGetAllFriendPresenter.getAllFriend(user.getToken());
    }

    @Override
    public void getAllFriendSuccess(List<FriendData> friendData) {
        mListFriendData = friendData;
        if (mListFriendData != null) {
            updateUI();
        }
    }

    @Override
    public void getAllFriendFail() {

    }

    @Override
    public void deleteFriendSuccess() {
        mListFriendData.remove(mIntPosition);
        mFriendAdapter.notifyItemRemoved(mIntPosition);
    }

    @Override
    public void deleteFriendFail() {
        Toast.makeText(this, "Không thể xóa bạn bè lúc này", Toast.LENGTH_SHORT).show();
    }
}
