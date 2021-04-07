package com.example.democ.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.democ.R;
import com.example.democ.adapter.VegetableAdapter;
import com.example.democ.adapter.VegetablePostAdapter;
import com.example.democ.iclick.IClickVegetable;
import com.example.democ.model.Garden;
import com.example.democ.model.GardenResult;
import com.example.democ.model.Vegetable;
import com.example.democ.model.VegetableData;
import com.example.democ.presenters.AllVegetableByGardenIdPresenter;
import com.example.democ.presenters.DeleteGardenPresenter;
import com.example.democ.presenters.UpdateGardenPresenter;
import com.example.democ.room.entities.User;
import com.example.democ.room.managements.UserManagement;
import com.example.democ.views.AllVegetableByGardenIdView;
import com.example.democ.views.DeleteGardenView;

import java.util.ArrayList;
import java.util.List;

public class GardenActivity extends AppCompatActivity implements View.OnClickListener, DeleteGardenView, AllVegetableByGardenIdView,
        IClickVegetable {

    private RecyclerView mRecyclerVegetable;
    private List<Vegetable> mVegetablesList;
    private VegetableAdapter mVegetableAdapter;

    static int mGardenId;
    static String mGardenName, mGardenAddress;
    static List<VegetableData> mVegetableDataList;

    private TextView mTxtGardenName, mTxtGardenAddress, mTxtUpdateGarden, mTxtDeleteGarden;
//    private FloatingActionButton mFabAddVegetable;
    private LinearLayout mLnlAddVegetable, mLnlBack;
    private UpdateGardenPresenter mUpdateGardenPresenter;
    private User mUser;
    private UserManagement mUserManagement;
    private DeleteGardenPresenter mDeleteGardenPresenter;
    private AllVegetableByGardenIdPresenter mAllVegetableByGardenIdPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garden);

        initialView();
        initialData();
    }

    private void initialView() {

        mTxtUpdateGarden = (TextView) findViewById(R.id.txt_update_garden);
        mTxtUpdateGarden.setOnClickListener(this);
        mTxtDeleteGarden = (TextView) findViewById(R.id.txt_delete_garden);
        mTxtDeleteGarden.setOnClickListener(this);

        mLnlAddVegetable = (LinearLayout) findViewById(R.id.lnl_add_vegetable);
        mLnlAddVegetable.setOnClickListener(this);
        mLnlBack = (LinearLayout) findViewById(R.id.lnl_back);
        mLnlBack.setOnClickListener(this);

    }

    private void initialData() {
        mDeleteGardenPresenter = new DeleteGardenPresenter(getApplication(), this, this);
        mAllVegetableByGardenIdPresenter = new AllVegetableByGardenIdPresenter(getApplication(), this, this);

        mRecyclerVegetable = (RecyclerView) findViewById(R.id.recycler_vegetable);
        mRecyclerVegetable.setHasFixedSize(true);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerVegetable.setLayoutManager(layoutManager);

        mTxtGardenName = (TextView) findViewById(R.id.txt_garden_name);
        mTxtGardenAddress = (TextView) findViewById(R.id.txt_garden_address);


        Intent intentGardenAdapter = getIntent();
        Bundle bundleGardenAdapter = intentGardenAdapter.getExtras();
        if (bundleGardenAdapter != null) {
            mGardenName = bundleGardenAdapter.getString("GARDEN_NAME");
            mGardenAddress = bundleGardenAdapter.getString("GARDEN_ADDRESS");
            mGardenId = bundleGardenAdapter.getInt("GARDEN_ID");
            mTxtGardenName.setText(mGardenName);
            mTxtGardenAddress.setText(mGardenAddress);
        }

        //bundle frm
        mUserManagement = new UserManagement(getApplication());
        mUserManagement.getmUserInfo(new UserManagement.OnDataCallBackUser() {
            @Override
            public void onDataSuccess(User user) {
                mUser = user;
                //get all vegetable
                System.out.println("garden activity line 114");
                mAllVegetableByGardenIdPresenter.getAllVegetableByGardenId(mGardenId, mUser.getToken());
            }

            @Override
            public void onDataFail() {

            }
        });

        mVegetableDataList = new ArrayList<>();

//        updateUI();
    }

    public void updateUI() {
        if (mVegetableAdapter == null) {
            mVegetableAdapter = new VegetableAdapter((ArrayList<VegetableData>) mVegetableDataList, getApplicationContext(), this);
            mRecyclerVegetable.setAdapter(mVegetableAdapter);
        } else {
            mVegetableAdapter.notifyDataSetChanged();
        }
    }

    public void deleteGarden() {
        String token = mUser.getToken();
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        System.out.println(token);
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        mDeleteGardenPresenter.deleteGarden(mGardenId, token);
    }

    public void showDialogDeleteGarden() {
        final Dialog dialog = new Dialog(GardenActivity.this);
        dialog.setContentView(R.layout.dialog_delete_garden);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        Button mBtnYes, mBtnNo;
        mBtnYes = (Button) dialog.findViewById(R.id.btn_delete_yes);
        mBtnNo = (Button) dialog.findViewById(R.id.btn_delete_no);
        mBtnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        mBtnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteGarden();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void createVegetable() {
        Intent intentCreateVegetable = new Intent(GardenActivity.this, CreateVegetableActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("GARDEN_ID", mGardenId);
        bundle.putString("GARDEN_NAME", mGardenName);
        bundle.putString("GARDEN_ADDRESS", mGardenAddress);
        Toast.makeText(this, "GardenId: " + mGardenId, Toast.LENGTH_SHORT).show();
        intentCreateVegetable.putExtras(bundle);
        startActivity(intentCreateVegetable);
    }

//    public void getAllVegetableByGardenId() {
//        String token = mUser.getToken();
//        mAllVegetableByGardenIdPresenter.getAllVegetableByGardenId(mGardenId, token);
//    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.fab_add_vegetable:
//                break;
            case R.id.lnl_add_vegetable:
//                Intent intentCreateVegetable = new Intent(GardenActivity.this, CreateVegetableActivity.class);
//                startActivity(intentCreateVegetable);
                createVegetable();
                break;
            case R.id.lnl_back:
                Intent intentHome = new Intent(GardenActivity.this, MainActivity.class);
                startActivity(intentHome);
                finish();
                break;
            case R.id.txt_update_garden:
                Toast.makeText(this, "update garden: " + mGardenId, Toast.LENGTH_SHORT).show();
                Intent intentUpdateGarden = new Intent(GardenActivity.this, UpdateGardenActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("GARDEN_NAME", mGardenName);
                bundle.putString("GARDEN_ADDRESS", mGardenAddress);
                bundle.putInt("GARDEN_ID", mGardenId);
                intentUpdateGarden.putExtras(bundle);
                startActivity(intentUpdateGarden);
                break;
            case R.id.txt_delete_garden:
                Toast.makeText(this, "delete garden: " + mGardenId, Toast.LENGTH_SHORT).show();
                showDialogDeleteGarden();
                break;
        }
    }

    @Override
    public void deleteGardenSuccess() {
        Intent intentHome = new Intent(GardenActivity.this, MainActivity.class);
        System.out.println("deleteGardenSuccess in gardenActivity");
        startActivity(intentHome);
    }

    @Override
    public void deleteGardenFail() {

    }

    @Override
    public void getAllVegetableByGardenIdSuccess(List<VegetableData> vegetableData) {
        this.mVegetableDataList = vegetableData;
        System.out.println("gggggggggggggggggggggggggggggggggggggggggggggggg");
        if (mVegetableDataList.size() > 0) {
            System.out.println("getAllVegetableByGardenIdSuccess garden activity");
            initialView();
            updateUI();
        } else {
            System.out.println("Load recycleView trong");
        }

    }

    @Override
    public void getAllVegetableByGardenIdFail() {

    }

    @Override
    public void clickVegetable(VegetableData vegetableData) {
        Intent intent = new Intent(GardenActivity.this, VegetableActivity.class);
        Bundle bundle = new Bundle();
//                    bundle.putString("VEGETABLE_IMAGE", );
        bundle.putString("VEGETABLE_NAME", vegetableData.getName());
        bundle.putString("VEGETABLE_DESCRIPTION", vegetableData.getDescription());
        bundle.putString("VEGETABLE_FEATURE", vegetableData.getFeature());
        bundle.putInt("VEGETABLE_STT", vegetableData.getStt());
        String linkUrl = "";

        if (vegetableData.getImageVegetables().size() > 0) {
            int maxSize = vegetableData.getImageVegetables().size() - 1;
            linkUrl = vegetableData.getImageVegetables().get(maxSize).getUrl();
        } else {
            linkUrl = "";
        }
        bundle.putString("VEGETABLE_IMAGE", linkUrl);
        bundle.putInt("GARDEN_ID", mGardenId);
        bundle.putString("GARDEN_NAME", mGardenName);
        bundle.putString("GARDEN_ADDRESS", mGardenAddress);
        intent.putExtras(bundle);
        Toast.makeText(getApplication(), "gardenId: " + mGardenId + "\n noVeg: " + vegetableData.getStt(), Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }
}
