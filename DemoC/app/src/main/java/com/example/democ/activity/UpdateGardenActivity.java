package com.example.democ.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.democ.R;
import com.example.democ.fragment.DistrictBottomSheetFragment;
import com.example.democ.fragment.ProvinceBottomSheetFragment;
import com.example.democ.fragment.WardBottomSheetFragment;
import com.example.democ.iclick.IClickDistrict;
import com.example.democ.iclick.IClickProvince;
import com.example.democ.iclick.IClickWard;
import com.example.democ.model.DistrictData;
import com.example.democ.model.Garden;
import com.example.democ.model.ProvinceData;
import com.example.democ.model.WardData;
import com.example.democ.presenters.DistrictPresenter;
import com.example.democ.presenters.PersonalPresenter;
import com.example.democ.presenters.ProvincePresenter;
import com.example.democ.presenters.UpdateGardenPresenter;
import com.example.democ.presenters.WardPresenter;
import com.example.democ.room.entities.User;
import com.example.democ.room.managements.UserManagement;
import com.example.democ.views.DistrictView;
import com.example.democ.views.PersonalView;
import com.example.democ.views.ProvinceView;
import com.example.democ.views.UpdateGardenView;
import com.example.democ.views.WardView;

import java.util.ArrayList;
import java.util.List;

public class UpdateGardenActivity extends AppCompatActivity implements View.OnClickListener, UpdateGardenView,
        PersonalView, ProvinceView, DistrictView, WardView {

    private EditText mEdtGardenName, mEdtGardenAddress;
    private TextView mTxtProvince, mTxtDistrict, mTxtWard;
    private Button mBtnUpdateGarden;

    private UpdateGardenPresenter mUpdateGardenPresenter;
    private User mUser;
//    private UserManagement mUserManagement;
    static int mGardenId;
    private PersonalPresenter mPersonalPresenter;
    private ProvincePresenter mProvincePresenter;
    private DistrictPresenter mDistrictPresenter;
    private WardPresenter mWardPresenter;

    //
    private ArrayList<ProvinceData> mListProvince;
    private ArrayList<DistrictData> mListDistrict;
    private ArrayList<WardData> mListWard;
    private static int mIntDistrictId = 0;
    private static int mIdWard = 0;
    private static String mStrProvince = null;
    private static String mStrDistrict = null;
    private static String mStrWard = "";
    private static String mStrAddress = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_garden);

        initialView();
        initialData();
    }


    private void initialView() {
        mListProvince = new ArrayList<>();
        mListDistrict = new ArrayList<>();
        mListWard = new ArrayList<>();
        mUser = new User();

        mEdtGardenName = (EditText) findViewById(R.id.edt_garden_update_name);
        mEdtGardenAddress = (EditText) findViewById(R.id.edt_garden_update_address);
        mTxtProvince = (TextView) findViewById(R.id.txt_province);
        mTxtDistrict = (TextView) findViewById(R.id.txt_district);
        mTxtWard = (TextView) findViewById(R.id.txt_ward);
        mBtnUpdateGarden = (Button) findViewById(R.id.btn_update_garden);
        mBtnUpdateGarden.setOnClickListener(this);
        mTxtProvince.setOnClickListener(this);
        mTxtDistrict.setOnClickListener(this);
        mTxtWard.setOnClickListener(this);

        mPersonalPresenter = new PersonalPresenter(getApplicationContext(), this);
        mProvincePresenter = new ProvincePresenter(getApplication(), getApplicationContext(), this);
        mDistrictPresenter = new DistrictPresenter(getApplication(), getApplicationContext(), this);
        mWardPresenter = new WardPresenter(getApplication(),getApplicationContext(), this);

        mPersonalPresenter.getInfoPersonal();

    }

    private void initialData() {

        mUpdateGardenPresenter = new UpdateGardenPresenter(getApplication(), this, this);
        Intent intentGardenAdapter = getIntent();
        Bundle bundleGardenAdapter = intentGardenAdapter.getExtras();
        if (bundleGardenAdapter != null) {
            String gardenName = bundleGardenAdapter.getString("GARDEN_NAME");
            String gardenAddress = bundleGardenAdapter.getString("GARDEN_ADDRESS");
            mGardenId = bundleGardenAdapter.getInt("GARDEN_ID");
            mEdtGardenName.setText(gardenName);
            mEdtGardenAddress.setText(gardenAddress);
            mStrAddress = gardenAddress;
        }
    }

    public void updateGarden() {
        String gardenName = mEdtGardenName.getText().toString();
//        String gardenAddress = mStrProvince + ", " + mStrDistrict + ", " + mStrWard + ", " + mEdtGardenAddress.getText().toString();
        mStrAddress = mStrProvince + ", " + mStrDistrict + ", " + mStrWard + ", " + mEdtGardenAddress.getText().toString();
        String token = mUser.getToken();
        System.out.println("UUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUu");
        System.out.println("gardenId: " + mGardenId);
        System.out.println("name: " + gardenName);
        System.out.println("address: " + mStrAddress);
        System.out.println("UUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUu");
        Garden garden = new Garden(mGardenId, gardenName, mStrAddress);
        mUpdateGardenPresenter.updateGarden(garden, mUser.getToken());
    }

    // get province
    private void clickOpenProvince() {
        ProvinceBottomSheetFragment provinceBottomSheetFragment = new ProvinceBottomSheetFragment(mListProvince, new IClickProvince() {
            @Override
            public void clickProvince(ProvinceData provinceData) {
                mTxtProvince.setText(provinceData.getName());
                mIntDistrictId = provinceData.getId();
                mStrProvince = provinceData.getName();
                //
                mDistrictPresenter.getDistrictById(mIntDistrictId, mUser.getToken());
            }
        });
        provinceBottomSheetFragment.show(getSupportFragmentManager(), provinceBottomSheetFragment.getTag());
        provinceBottomSheetFragment.setCancelable(false);
    }
    //get district by id
    private void clickOpenDistrict() {
        DistrictBottomSheetFragment districtBottomSheetFragment = new DistrictBottomSheetFragment(mListDistrict, new IClickDistrict() {
            @Override
            public void clickDistrict(DistrictData districtData) {
                mTxtDistrict.setText(districtData.getName());
                mIdWard = districtData.getId();
                mStrDistrict = districtData.getName();
                mWardPresenter.getWardById(mIdWard, mUser.getToken());

            }
        });
        districtBottomSheetFragment.show(getSupportFragmentManager(), districtBottomSheetFragment.getTag());
    }
    //get ward by id
    private void clickOpenWard() {
        WardBottomSheetFragment wardBottomSheetFragment = new WardBottomSheetFragment(mListWard, new IClickWard() {
            @Override
            public void clickWard(WardData wardData) {
                mTxtWard.setText(wardData.getName());
                mStrWard = wardData.getName();
            }
        });
        wardBottomSheetFragment.show(getSupportFragmentManager(), wardBottomSheetFragment.getTag());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_update_garden:
                updateGarden();
                break;
            case R.id.txt_province:
                clickOpenProvince();
                break;
            case R.id.txt_district:
                if (mStrProvince == null) {
                    Toast.makeText(getApplicationContext(), "vui long chon tinh/ thanh pho", Toast.LENGTH_SHORT).show();
                } else {
                    clickOpenDistrict();
                }
                break;
            case R.id.txt_ward:
                if (mStrProvince == null || mStrDistrict == null) {
                    Toast.makeText(getApplicationContext(),"chua chon thanh pho hoac quan huyen", Toast.LENGTH_SHORT).show();
                } else {
                    clickOpenWard();
                }
                break;
        }
    }

    @Override
    public void updateGardenSuccess(Garden garden) {
        Intent intentHome = new Intent(UpdateGardenActivity.this, MainActivity.class);
        startActivity(intentHome);
        finish();
    }

    @Override
    public void updateGardenFail() {

    }

    @Override
    public void showInfoPersonal(User user) {
        mUser = user;
        mProvincePresenter.getAllProvince(user.getToken());
    }

    @Override
    public void getProvinceSuccess(List<ProvinceData> provinceData) {
        mListProvince = (ArrayList<ProvinceData>) provinceData;
    }

    @Override
    public void getProvinceFail() {

    }

    @Override
    public void getDistrictSuccess(List<DistrictData> districtData) {
        mListDistrict = (ArrayList<DistrictData>) districtData;
    }

    @Override
    public void getDistrictFail() {

    }

    @Override
    public void getWardSuccess(List<WardData> wardData) {
        mListWard = (ArrayList<WardData>) wardData;
    }

    @Override
    public void getWardFail() {

    }
}
