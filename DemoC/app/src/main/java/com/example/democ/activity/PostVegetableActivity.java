package com.example.democ.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.democ.R;
import com.example.democ.adapter.VegetablePostAdapter;
import com.example.democ.model.VegetableData;
import com.example.democ.presenters.AllVegetableByGardenIdPresenter;
import com.example.democ.room.entities.User;
import com.example.democ.room.managements.UserManagement;
import com.example.democ.views.AllVegetableByGardenIdView;

import java.util.ArrayList;
import java.util.List;

public class PostVegetableActivity extends AppCompatActivity implements AllVegetableByGardenIdView {

    private RecyclerView mRecyclerVegetable;

    private ArrayList<VegetableData> mVegetableList;
    private VegetablePostAdapter mVegetablePostAdapter;
    private User mUser;
    private UserManagement mUserManagement;
    private AllVegetableByGardenIdPresenter mAllVegetableByGardenIdPresenter;
    private int mGardenId;
    private String mGardenName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_vegetable);

        initialView();
        initialData();
    }

    private void initialView() {
        mRecyclerVegetable = (RecyclerView) findViewById(R.id.recycler_vegetable);
        mRecyclerVegetable.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerVegetable.setLayoutManager(layoutManager);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            mGardenId = bundle.getInt("GARDEN_ID");
            mGardenName = bundle.getString("GARDEN_NAME");
        }

        mAllVegetableByGardenIdPresenter = new AllVegetableByGardenIdPresenter(getApplication(), this, this);
        mUserManagement = new UserManagement(getApplication());
        mUserManagement.getmUserInfo(new UserManagement.OnDataCallBackUser() {
            @Override
            public void onDataSuccess(User user) {
                mAllVegetableByGardenIdPresenter.getAllVegetableByGardenId(mGardenId, user.getToken());
            }

            @Override
            public void onDataFail() {

            }
        });
    }

    private void initialData() {
        mVegetableList = new ArrayList<>();
    }

    public void updateUI() {
//        if (mVegetablePostAdapter == null) {
//            mVegetablePostAdapter = new VegetablePostAdapter((ArrayList<VegetableData>) mVegetableList, getApplicationContext());
//            mRecyclerVegetable.setAdapter(mVegetablePostAdapter);
//
//            //onclick
//            mVegetablePostAdapter.getPosition(new VegetablePostAdapter.OnClickListener() {
//                @Override
//                public void onClickListener(int position) {
//                    Intent intentCreatePost = new Intent(PostVegetableActivity.this, CreatePostActivity.class);
//                    intentCreatePost.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
//                    Bundle bundle = new Bundle();
//                    bundle.putString("VEGETABLE_ID", mVegetableList.get(position).getIdName());
//                    bundle.putString("VEGETABLE_NAME", mVegetableList.get(position).getName());
//                    bundle.putString("GARDEN_NAME", mGardenName);
//                    intentCreatePost.putExtras(bundle);
//                    startActivity(intentCreatePost);
//                }
//            });
//
//        } else {
//            mVegetablePostAdapter.notifyDataSetChanged();
//        }
    }

    @Override
    public void getAllVegetableByGardenIdSuccess(List<VegetableData> vegetableData) {
        this.mVegetableList = (ArrayList<VegetableData>) vegetableData;
        if (mVegetableList.size() > 0) {
            updateUI();
        } else {
            initialView();
        }
    }

    @Override
    public void getAllVegetableByGardenIdFail() {

    }
}
