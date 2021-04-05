package com.example.democ.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.democ.R;
import com.example.democ.adapter.GardenPostAdapter;
import com.example.democ.model.GardenResult;
import com.example.democ.presenters.AllGardenPresenter;
import com.example.democ.room.entities.User;
import com.example.democ.room.managements.UserManagement;
import com.example.democ.views.AllGardenView;

import java.util.ArrayList;
import java.util.List;

public class PostGardenActivity extends AppCompatActivity implements AllGardenView {

    private RecyclerView mRecyclerGarden;

    private ArrayList<GardenResult> mGardenList;
    private GardenPostAdapter mGardenAdapter;

    private AllGardenPresenter mAllGardenPresenter;
    private User mUser;
    private UserManagement mUserManagement;
    private String mPostContent, mPostQuantity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_garden);

        initialView();
        initialData();
    }

    private void initialView() {
        mRecyclerGarden = (RecyclerView) findViewById(R.id.recycler_garden);
        mRecyclerGarden.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        mRecyclerGarden.setLayoutManager(layoutManager);

        mAllGardenPresenter = new AllGardenPresenter(getApplication(), this, this);
        mUserManagement = new UserManagement(getApplication());
        mUserManagement.getmUserInfo(new UserManagement.OnDataCallBackUser() {
            @Override
            public void onDataSuccess(User user) {
                mAllGardenPresenter.getAllGarden(user.getToken());
            }

            @Override
            public void onDataFail() {

            }
        });

        //take of post-content, post-quantity from createPostActivity
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            mPostContent = bundle.getString("POST_CONTENT");
            mPostQuantity = bundle.getString("POST_QUANTITY");
        }

    }

    private void initialData() {
        mGardenList = new ArrayList<>();
//        updateUI();
    }

    public void updateUI() {
//        if (mGardenAdapter == null) {
//            mGardenAdapter = new GardenPostAdapter((ArrayList<GardenResult>) mGardenList,
//                    getApplicationContext());
//            mRecyclerGarden.setAdapter(mGardenAdapter);
//            mGardenAdapter.getPosition(new GardenPostAdapter.OnClickListener() {
//                @Override
//                public void onClickListener(int position) {
//                    Intent intent = new Intent(PostGardenActivity.this, CreatePostActivity.class);
////                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
//                    Bundle bundle = new Bundle();
//                    bundle.putString("GARDEN_NAME", mGardenList.get(position).getName());
//                    bundle.putInt("GARDEN_ID", mGardenList.get(position).getId());
//                    bundle.putString("POST_CONTENT", mPostContent);
//                    bundle.putString("POST_QUANTITY", mPostQuantity);
//                    System.out.println("----------------------------------------");
//                    System.out.println(mGardenList.get(position).getName());
//                    System.out.println(mGardenList.get(position).getId());
//                    System.out.println("tttttttttttttt");
//                    System.out.println(mPostContent);
//                    System.out.println("uuuuuuuuuuuuuu");
//                    System.out.println(mPostQuantity);
//                    System.out.println("----------------------------------------");
//                    intent.putExtras(bundle);
//                    startActivity(intent);
//                }
//            });
//        } else {
//            mGardenAdapter.notifyDataSetChanged();
//        }

    }

    @Override
    public void getAllGardenSuccess(List<GardenResult> listAllGarden) {
        this.mGardenList = (ArrayList<GardenResult>) listAllGarden;
        if (mGardenList.size() > 0) {
            updateUI();
        } else {
            initialView();
        }

    }

    @Override
    public void getAllGardenFail() {

    }
}
