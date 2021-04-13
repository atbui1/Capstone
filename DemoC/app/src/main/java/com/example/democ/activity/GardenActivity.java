package com.example.democ.activity;

import androidx.annotation.Nullable;
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
import com.example.democ.presenters.PersonalPresenter;
import com.example.democ.presenters.UpdateGardenPresenter;
import com.example.democ.room.entities.User;
import com.example.democ.room.managements.UserManagement;
import com.example.democ.views.AllVegetableByGardenIdView;
import com.example.democ.views.DeleteGardenView;
import com.example.democ.views.PersonalView;

import java.util.ArrayList;
import java.util.List;

public class GardenActivity extends AppCompatActivity implements View.OnClickListener, DeleteGardenView, AllVegetableByGardenIdView,
        IClickVegetable, PersonalView {

    private RecyclerView mRecyclerVegetable;
    private List<Vegetable> mVegetablesList;
    private VegetableAdapter mVegetableAdapter;

    static int mGardenId;
    static String mGardenName, mGardenAddress;
    private List<VegetableData> mVegetableDataList;

    private TextView mTxtGardenName, mTxtGardenAddress, mTxtUpdateGarden, mTxtDeleteGarden;
//    private FloatingActionButton mFabAddVegetable;
    private LinearLayout mLnlAddVegetable, mLnlBack;
    private UpdateGardenPresenter mUpdateGardenPresenter;
    private User mUser;
    private UserManagement mUserManagement;
    private DeleteGardenPresenter mDeleteGardenPresenter;
    private PersonalPresenter mPersonalPresenter;
    private AllVegetableByGardenIdPresenter mAllVegetableByGardenIdPresenter;
    private static int CREATE_VEGETABLE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garden);

        initialView();
        initialData();
    }

    private void initialView() {
        mPersonalPresenter = new PersonalPresenter(getApplication(), this);
        mPersonalPresenter.getInfoPersonal();
        mDeleteGardenPresenter = new DeleteGardenPresenter(getApplication(), this, this);
        mAllVegetableByGardenIdPresenter = new AllVegetableByGardenIdPresenter(getApplication(), this, this);

//        mVegetableDataList = new ArrayList<>();

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


        mRecyclerVegetable = (RecyclerView) findViewById(R.id.recycler_vegetable);
        mRecyclerVegetable.setHasFixedSize(true);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerVegetable.setLayoutManager(layoutManager);

        mTxtGardenName = (TextView) findViewById(R.id.txt_garden_name);
        mTxtGardenAddress = (TextView) findViewById(R.id.txt_garden_address);


        Intent intent = getIntent();
//        Bundle bundleGardenAdapter = intentGardenAdapter.getExtras();
        //bundle garden
        Bundle bundleGardenAdapter = intent.getBundleExtra("infoGardenTo");
        //bundle create vegetable
        Bundle bundleCreateVegetable = intent.getBundleExtra("infoGarden");
        if (bundleGardenAdapter != null) {
            mGardenName = bundleGardenAdapter.getString("GARDEN_NAME");
            mGardenAddress = bundleGardenAdapter.getString("GARDEN_ADDRESS");
            mGardenId = bundleGardenAdapter.getInt("GARDEN_ID");
            mTxtGardenName.setText(mGardenName);
            mTxtGardenAddress.setText(mGardenAddress);
            System.out.println("chay bundleGardenAdapter ***************************************");
        }
        if (bundleCreateVegetable != null) {
            mGardenName = bundleCreateVegetable.getString("GARDEN_NAME");
            mGardenAddress = bundleCreateVegetable.getString("GARDEN_ADDRESS");
            mGardenId = bundleCreateVegetable.getInt("GARDEN_ID");
            mTxtGardenName.setText(mGardenName);
            mTxtGardenAddress.setText(mGardenAddress);
            System.out.println("chay bundleCreateVegetable **************************");
        }

        //bundle frm
//        mUserManagement = new UserManagement(getApplication());
//        mUserManagement.getmUserInfo(new UserManagement.OnDataCallBackUser() {
//            @Override
//            public void onDataSuccess(User user) {
//                mUser = user;
//                //get all vegetable
//                System.out.println("garden activity line 114");
//                mAllVegetableByGardenIdPresenter.getAllVegetableByGardenId(mGardenId, mUser.getToken());
//            }
//
//            @Override
//            public void onDataFail() {
//
//            }
//        });

        System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
        System.out.println("chay xong initialview 111");
        System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
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
        System.out.println("token delete garden: " + token);
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
        mVegetableDataList = vegetableData;
        System.out.println("gggggggggggggggggggggggggggggggggggggggggggggggg");
        System.out.println("mVegetableDataList.size truoc if else: " + mVegetableDataList.size());
        System.out.println("gggggggggggggggggggggggggggggggggggggggggggggggg");
        if (mVegetableDataList.size() > 0) {
            System.out.println("getAllVegetableByGardenIdSuccess garden activity trong if");
            System.out.println("mVegetableDataList.size: " + mVegetableDataList.size());
//            initialView();
            updateUI();
        } else {
            System.out.println("Load recycleView trong else");
            System.out.println("chay vao else getAllVegetableByGardenIdSuccess");
//            initialView();
            System.out.println("chay vao else getAllVegetableByGardenIdSuccess size: " + mVegetableDataList.size());
//            return;
        }
//        initialView();

    }

    @Override
    public void getAllVegetableByGardenIdFail() {

    }

    @Override
    public void clickVegetable(VegetableData vegetableData) {
        Intent intent = new Intent(GardenActivity.this, VegetableActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
//        Bundle bundle = new Bundle();
//        bundle.putString("VEGETABLE_ID", vegetableData.getId());
//        bundle.putString("VEGETABLE_NAME", vegetableData.getName());
//        bundle.putString("VEGETABLE_DESCRIPTION", vegetableData.getDescription());
//        bundle.putString("VEGETABLE_FEATURE", vegetableData.getFeature());
//        bundle.putInt("VEGETABLE_QUANTITY", vegetableData.getQuantity());
//        String linkUrl = "";
//
//        if (vegetableData.getImageVegetables().size() > 0) {
//            int maxSize = vegetableData.getImageVegetables().size() - 1;
//            linkUrl = vegetableData.getImageVegetables().get(maxSize).getUrl();
//        } else {
//            linkUrl = "";
//        }
//        bundle.putString("VEGETABLE_IMAGE", linkUrl);
//        bundle.putString("VEGETABLE_DES_ID", vegetableData.getIdDescription());
//        bundle.putString("VEGETABLE_NAME_SEARCH", vegetableData.getNameSearch());
//        bundle.putString("VEGETABLE_SYNONYM", vegetableData.getSynonymOfFeature());
//        bundle.putBoolean("IS_FIXED", vegetableData.isFixed());
//        bundle.putInt("GARDEN_ID", mGardenId);
//        bundle.putString("GARDEN_NAME", mGardenName);
//        bundle.putString("GARDEN_ADDRESS", mGardenAddress);
//        intent.putExtras(bundle);
//        Toast.makeText(getApplication(), "gardenId: " + mGardenId + "\n iDVeg: " + vegetableData.getId(), Toast.LENGTH_SHORT).show();
//        startActivity(intent);
//        startActivityForResult(intent, CREATE_VEGETABLE);

        /*      send new                  */
        Bundle bundle = new Bundle();
        VegetableData vegetable = vegetableData;
        bundle.putInt("GARDEN_ID", mGardenId);
        bundle.putString("GARDEN_NAME", mGardenName);
        bundle.putString("GARDEN_ADDRESS", mGardenAddress);
        bundle.putSerializable("zxc", vegetable);
        intent.putExtras(bundle);
        startActivityForResult(intent, CREATE_VEGETABLE);
    }

    @Override
    public void showInfoPersonal(User user) {
        mUser = user;
        mAllVegetableByGardenIdPresenter.getAllVegetableByGardenId(mGardenId, user.getToken());
        System.out.println("chay showInfoPersonal ***************************");
        System.out.println("garden Id: " + mGardenId);
        System.out.println("token showInfoPersonal: " + user.getToken());
        System.out.println("chay showInfoPersonal ***************************");
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (data == null) {
//            return;
//        }
//        if (requestCode == CREATE_VEGETABLE) {
//            Bundle bundle = data.getBundleExtra("infoGarden");
//            mGardenName = bundle.getString("GARDEN_NAME");
//            mGardenAddress = bundle.getString("GARDEN_ADDRESS");
//            mGardenId = bundle.getInt("GARDEN_ID");
//            mTxtGardenName.setText(mGardenName);
//            mTxtGardenAddress.setText(mGardenAddress);
//        }
//    }
}
