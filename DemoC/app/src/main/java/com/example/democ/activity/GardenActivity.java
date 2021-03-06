package com.example.democ.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
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

    private final static String KEY_VEGETABLE = "KEY_VEGETABLE";
    private final static String KEY_VEGETABLE_DELETE = "KEY_VEGETABLE_DELETE";
    private final static String KEY_VEGETABLE_CREATE = "KEY_VEGETABLE_CREATE";
    private final static String KEY_VEGETABLE_UPDATE = "KEY_VEGETABLE_UPDATE";
    private final static String KEY_GARDEN_INFO = "KEY_GARDEN_INFO";
    private RecyclerView mRecyclerVegetable;
    private List<Vegetable> mVegetablesList;
    private VegetableAdapter mVegetableAdapter;

    private int mIntGardenID, mIntProvinceId, mIntDistrictId, mIntWardId;
    private String mStrGardenName = "", mStrGardenAddress = "";
    private List<VegetableData> mVegetableDataList;

    private TextView mTxtTitle;
//    private FloatingActionButton mFabAddVegetable;
    private LinearLayout mLnlAddVegetable, mLnlBack;
    private UpdateGardenPresenter mUpdateGardenPresenter;
    private User mUser;
    private UserManagement mUserManagement;
    private DeleteGardenPresenter mDeleteGardenPresenter;
    private PersonalPresenter mPersonalPresenter;
    private AllVegetableByGardenIdPresenter mAllVegetableByGardenIdPresenter;
    private static int CREATE_VEGETABLE = 2;
    GardenResult mGardenResult = null;

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

        mTxtTitle = (TextView) findViewById(R.id.txt_title);

        mLnlAddVegetable = (LinearLayout) findViewById(R.id.lnl_add_vegetable);
        mLnlAddVegetable.setOnClickListener(this);
        mLnlBack = (LinearLayout) findViewById(R.id.lnl_back);
        mLnlBack.setOnClickListener(this);

    }

    private void initialData() {


        mRecyclerVegetable = (RecyclerView) findViewById(R.id.recycler_vegetable);
        mRecyclerVegetable.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerVegetable.setLayoutManager(layoutManager);

        getDataGardenList();
        getDataGardenCreateVegetable();
        getGardenDeleteVegetable();
        getGardenUpdateVegetable();
        getData();
        mTxtTitle.setText(mStrGardenName);
    }

    private void getDataGardenList() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            GardenResult gardenResult = (GardenResult) bundle.getSerializable(KEY_GARDEN_INFO);
            if (gardenResult != null) {
                mGardenResult = gardenResult;
                mStrGardenName = mGardenResult.getName();
                mStrGardenAddress = mGardenResult.getAddress();
                mIntGardenID = mGardenResult.getId();
                mIntProvinceId = mGardenResult.getProvinceId();
                mIntDistrictId = mGardenResult.getDistrictId();
                mIntWardId = mGardenResult.getWardId();
                System.out.println("chay bundleGardenAdapter ***************************************");
            }
        }
    }
    private void getDataGardenCreateVegetable() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            GardenResult gardenResult = (GardenResult) bundle.getSerializable(KEY_VEGETABLE_CREATE);
            if (gardenResult != null) {
                mGardenResult = gardenResult;
                mStrGardenName = mGardenResult.getName();
                mStrGardenAddress = mGardenResult.getAddress();
                mIntGardenID = mGardenResult.getId();
                mIntProvinceId = mGardenResult.getProvinceId();
                mIntDistrictId = mGardenResult.getDistrictId();
                mIntWardId = mGardenResult.getWardId();
                System.out.println("chay bundleGarden create Vegetable ***************************************");
            }
        }
    }
    private void getGardenDeleteVegetable() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            GardenResult gardenResult = (GardenResult) bundle.getSerializable(KEY_VEGETABLE_DELETE);
            if (gardenResult != null) {
                mGardenResult = gardenResult;
                mStrGardenName = mGardenResult.getName();
                mStrGardenAddress = mGardenResult.getAddress();
                mIntGardenID = mGardenResult.getId();
                mIntProvinceId = mGardenResult.getProvinceId();
                mIntDistrictId = mGardenResult.getDistrictId();
                mIntWardId = mGardenResult.getWardId();
                System.out.println("chay bundleGarden create Vegetable ***************************************");
            }
        }
    }
    private void getGardenUpdateVegetable() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            GardenResult gardenResult = (GardenResult) bundle.getSerializable(KEY_VEGETABLE_UPDATE);
            if (gardenResult != null) {
                mGardenResult = gardenResult;
                mStrGardenName = mGardenResult.getName();
                mStrGardenAddress = mGardenResult.getAddress();
                mIntGardenID = mGardenResult.getId();
                mIntProvinceId = mGardenResult.getProvinceId();
                mIntDistrictId = mGardenResult.getDistrictId();
                mIntWardId = mGardenResult.getWardId();
                System.out.println("chay bundleGarden create Vegetable ***************************************");
            }
        }
    }
    private void getData() {
        Intent intent = getIntent();

        /*bundle list garden*/
//        Bundle bundleGardenAdapter = intent.getBundleExtra(KEY_GARDEN_INFO);
//        Bundle bundleGardenAdapter = intent.getExtras();
        /*bundle create vegetable*/
//        Bundle bundleCreateVegetable = intent.getBundleExtra(KEY_VEGETABLE_CREATE);
        /*bundle delete vegetable*/
//        Bundle bundleDeleteVegetable = intent.getBundleExtra(KEY_VEGETABLE_DELETE);
        /*bundle update vegetable*/
//        Bundle bundleUpdateVegetable = intent.getBundleExtra(KEY_VEGETABLE_UPDATE);
//        if (bundleGardenAdapter != null) {
//            mGardenResult = (GardenResult) bundleGardenAdapter.getSerializable(KEY_GARDEN_INFO);
//            mStrGardenName = mGardenResult.getName();
//            mStrGardenAddress = mGardenResult.getAddress();
//            mIntGardenID = mGardenResult.getId();
//            mIntProvinceId = mGardenResult.getProvinceId();
//            mIntDistrictId = mGardenResult.getDistrictId();
//            mIntWardId = mGardenResult.getWardId();
//            System.out.println("chay bundleGardenAdapter ***************************************");
//        }
//        if (bundleCreateVegetable != null) {
//            mStrGardenName = bundleCreateVegetable.getString("GARDEN_NAME");
//            mStrGardenAddress = bundleCreateVegetable.getString("GARDEN_ADDRESS");
//            mIntGardenID = bundleCreateVegetable.getInt("GARDEN_ID");
//            System.out.println("chay bundleCreateVegetable **************************");
//        }
//        if (bundleDeleteVegetable != null) {
//            mStrGardenName = bundleDeleteVegetable.getString("GARDEN_NAME");
//            mStrGardenAddress = bundleDeleteVegetable.getString("GARDEN_ADDRESS");
//            mIntGardenID = bundleDeleteVegetable.getInt("GARDEN_ID");
//            System.out.println("chay bundleDeleteVegetable **************************");
//        }
//        if (bundleUpdateVegetable != null) {
//            mStrGardenName = bundleUpdateVegetable.getString("GARDEN_NAME");
//            mStrGardenAddress = bundleUpdateVegetable.getString("GARDEN_ADDRESS");
//            mIntGardenID = bundleUpdateVegetable.getInt("GARDEN_ID");
//            System.out.println("chay bundleGardenAdapter ***************************************");
//        }
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
        mDeleteGardenPresenter.deleteGarden(mIntGardenID, token);
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

    public void createVegetable(GardenResult gardenResult) {
        Intent intent = new Intent(GardenActivity.this, CreateVegetableActivity.class);
//        intentCreateVegetable.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
//        intentCreateVegetable.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_GARDEN_INFO, gardenResult);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void showDialogDeleteGardenErr() {
        final Dialog dialog = new Dialog(GardenActivity.this);
        dialog.setContentView(R.layout.dialog_exchange_quantity_err);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        TextView txtQuantity;
        Button btnClose;
        btnClose = (Button) dialog.findViewById(R.id.btn_close);
        txtQuantity = (TextView) dialog.findViewById(R.id.txt_exchange_quantity);
        txtQuantity.setText("Vui l??ng x??a c??y hi???n c?? trong v?????n tr?????c");

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lnl_add_vegetable:
                createVegetable(mGardenResult);
                break;
            case R.id.lnl_back:
                finish();
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
        showDialogDeleteGardenErr();
    }

    @Override
    public void getAllVegetableByGardenIdSuccess(List<VegetableData> vegetableData) {
        mVegetableDataList = vegetableData;
        if (mVegetableDataList.size() > 0) {
            System.out.println("mVegetableDataList.size: " + mVegetableDataList.size());
            updateUI();
        } else {

        }


        updateUI();
    }

    @Override
    public void getAllVegetableByGardenIdFail() {
        System.out.println("22222222222222  getAllVegetableByGardenIdFail   22222222222222222222222222");
    }

    @Override
    public void clickVegetable(VegetableData vegetableData) {
        Intent intent = new Intent(GardenActivity.this, VegetableActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        /*      send new                  */
        Bundle bundle = new Bundle();
        VegetableData vegetable = vegetableData;
//        bundle.putInt("GARDEN_ID", mIntGardenID);
//        bundle.putString("GARDEN_NAME", mStrGardenName);
//        bundle.putString("GARDEN_ADDRESS", mStrGardenAddress);
        bundle.putSerializable(KEY_VEGETABLE, vegetable);
        bundle.putSerializable(KEY_GARDEN_INFO, mGardenResult);
        intent.putExtras(bundle);
        startActivityForResult(intent, CREATE_VEGETABLE);
    }

    @Override
    public void showInfoPersonal(User user) {
        mUser = user;
        mAllVegetableByGardenIdPresenter.getAllVegetableByGardenId(mIntGardenID, user.getToken());

    }

}
