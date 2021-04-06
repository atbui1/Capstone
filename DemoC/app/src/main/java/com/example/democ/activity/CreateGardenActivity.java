package com.example.democ.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
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
import com.example.democ.model.GardenResult;
import com.example.democ.model.ProvinceData;
import com.example.democ.model.WardData;
import com.example.democ.presenters.CreateGardenPresenter;
import com.example.democ.presenters.DistrictPresenter;
import com.example.democ.presenters.PersonalPresenter;
import com.example.democ.presenters.ProvincePresenter;
import com.example.democ.presenters.WardPresenter;
import com.example.democ.room.entities.User;
import com.example.democ.room.managements.UserManagement;
import com.example.democ.views.CreateGardenView;
import com.example.democ.views.DistrictView;
import com.example.democ.views.PersonalView;
import com.example.democ.views.ProvinceView;
import com.example.democ.views.WardView;

import java.util.ArrayList;
import java.util.List;

public class CreateGardenActivity extends AppCompatActivity implements View.OnClickListener, CreateGardenView,
        ProvinceView, DistrictView, WardView, PersonalView {

    private User mUser;

    private EditText mEdtGardenName, mEdtGardenAddress;
    private Button mBtnCreateGarden;
    private TextView mTxtProvince, mTxtDistrict, mTxtWard;

    private PersonalPresenter mPersonalPresenter;
    private CreateGardenPresenter mCreateGardenPresenter;
    private ProvincePresenter mProvincePresenter;
    private DistrictPresenter mDistrictPresenter;
    private WardPresenter mWardPresenter;
    private ArrayList<ProvinceData> mListProvince;
    private ArrayList<DistrictData> mListDistrict;
    private ArrayList<WardData> mListWard;
    private static int mIntDistrictId = 0;
    private static int mIdWard = 0;
    private static String mStrProvince = null;
    private static String mStrDistrict = null;
    private static String mStrWard = "";
    private static String mStrSubAddress;
    private static String mStrAddress = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_garden);

        initialView();
        initialData();
    }

    private void initialView() {

        mPersonalPresenter = new PersonalPresenter(getApplicationContext(), this);

        mPersonalPresenter.getInfoPersonal();

        mEdtGardenName = (EditText) findViewById(R.id.edt_garden_name);
        mEdtGardenAddress = (EditText) findViewById(R.id.edt_garden_address);

        mBtnCreateGarden = (Button) findViewById(R.id.btn_create_garden);
        mBtnCreateGarden.setOnClickListener(this);

        mTxtProvince = (TextView) findViewById(R.id.txt_province);
        mTxtDistrict = (TextView) findViewById(R.id.txt_district);
        mTxtWard = (TextView) findViewById(R.id.txt_ward);
        mTxtProvince.setOnClickListener(this);
        mTxtDistrict.setOnClickListener(this);
        mTxtWard.setOnClickListener(this);

        mTxtProvince.setText("thanh pho 1");
        mTxtDistrict.setText("quan 1");
        mTxtWard.setText("phuong 1");
        mEdtGardenAddress.setText("chi tiet 1");

        mStrProvince = mTxtProvince.getText().toString();
        mStrDistrict = mTxtDistrict.getText().toString();
        mStrWard = mTxtWard.getText().toString();
        mStrSubAddress = mEdtGardenAddress.getText().toString();

        System.out.println("AAAAAAAAAAAAAA");
        System.out.println(mStrProvince);
        System.out.println(mStrDistrict);
        System.out.println(mStrWard);
        System.out.println(mStrSubAddress);
        System.out.println("AAAAAAAAAAAAAA");

        mProvincePresenter = new ProvincePresenter(getApplication(), getApplicationContext(), this);
        mDistrictPresenter = new DistrictPresenter(getApplication(), getApplicationContext(), this);
        mWardPresenter = new WardPresenter(getApplication(),getApplicationContext(), this);
    }

    private void initialData() {
        mCreateGardenPresenter = new CreateGardenPresenter(getApplication(), this, this);
    }

    public void createGarden() {
        String gardenName = mEdtGardenName.getText().toString();
        mStrSubAddress = mEdtGardenAddress.getText().toString();
        String token = mUser.getToken();

        mStrAddress = mStrProvince + ", " + mStrDistrict + ", " + mStrWard + ", " + mStrSubAddress;

        Garden garden = new Garden(gardenName, mStrAddress);

        System.out.println("cretea garden 114");
        System.out.println("garden name: " + gardenName);
        System.out.println("province: " + mStrProvince);
        System.out.println("district: " + mStrDistrict);
        System.out.println("ward: " + mStrWard);
        System.out.println("subAddress: " + mStrSubAddress);
        System.out.println("token: " + token);
        System.out.println(mStrAddress);
        System.out.println("cretea garden 114");

        if (mStrDistrict == "" || mStrWard == "" || mStrSubAddress == "") {
            System.out.println("show dia log err distric null or ward null");
            showDialogAddressErr();
        } else {
            System.out.println("goi request api");

//            send request to api
//            mCreateGardenPresenter.createGarden(garden, token);
        }
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
                mTxtDistrict.setText("");
                mTxtWard.setText("");
                mEdtGardenAddress.setText("");
                mStrDistrict = "";
                mStrWard = "";
                mStrSubAddress = "";
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

    private void showDialogAddressErr() {
        final Dialog dialog = new Dialog(CreateGardenActivity.this);
        dialog.setContentView(R.layout.dialog_login_fail);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        TextView txtErr;
        Button btnClose;
        txtErr = (TextView) dialog.findViewById(R.id.txt_detail_err);
        btnClose = (Button) dialog.findViewById(R.id.btn_close);
        txtErr.setText("vui lòng điền đầy đủ thông tin");
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
            case R.id.btn_create_garden:
                createGarden();
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

    @Override
    public void createGardenSuccess(GardenResult gardenResult) {
        Intent intentHome = new Intent(CreateGardenActivity.this, MainActivity.class);
        startActivity(intentHome);
        finish();
    }

    @Override
    public void createGardenFail() {

    }
}
