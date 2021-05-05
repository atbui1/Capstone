package com.example.democ.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.app.Dialog;
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
import com.example.democ.model.GardenResult;
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

    private final static String KEY_GARDEN_SEND_UPDATE = "KEY_GARDEN_SEND_UPDATE";

    private EditText mEdtGardenName, mEdtSubAddress;
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
    private  int mIntDistrictId = 0, mIdWard = 0;
    private  String mStrProvince = "", mStrDistrict = "", mStrWard = "", mStrAddress = "", mStrSubAddress;


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
        mEdtSubAddress = (EditText) findViewById(R.id.edt_garden_update_address);
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
        getDataGarden();
        mUpdateGardenPresenter = new UpdateGardenPresenter(getApplication(), this, this);

    }
    private void getDataGarden() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            GardenResult gardenResult = (GardenResult) bundle.getSerializable(KEY_GARDEN_SEND_UPDATE);
            String gardenName = gardenResult.getName();
            String gardenAddress = gardenResult.getAddress();
            mGardenId = gardenResult.getId();

            String[] addressTmp = gardenAddress.split(",");
            mStrSubAddress = addressTmp[0];
            mStrWard = addressTmp[1];
            mStrDistrict = addressTmp[2];
            mStrProvince = addressTmp[3];

            if (mStrSubAddress == null) {
                mStrSubAddress = "";
            }
            if (mStrWard == null) {
                mStrWard = "";
            }
            if (mStrDistrict == null) {
                mStrDistrict = "";
            }
            if (mStrProvince == null) {
                mStrProvince = "";
            }

            mEdtGardenName.setText(gardenName);
            mTxtProvince.setText(mStrProvince);
            mTxtDistrict.setText(mStrDistrict);
            mTxtWard.setText(mStrWard);
            mEdtSubAddress.setText(mStrSubAddress);

            mStrAddress = gardenAddress;


        }
    }

    /*name err*/
    private void showDialogNameErr() {
        final Dialog dialog = new Dialog(UpdateGardenActivity.this);
        dialog.setContentView(R.layout.dialog_login_fail);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        TextView txtErr;
        Button btnClose;
        txtErr = (TextView) dialog.findViewById(R.id.txt_detail_err);
        btnClose = (Button) dialog.findViewById(R.id.btn_close);
        txtErr.setText("vui lòng nhập tên vườn rau");
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
    /*address err*/
    private void showDialogAddressErr() {
        final Dialog dialog = new Dialog(UpdateGardenActivity.this);
        dialog.setContentView(R.layout.dialog_login_fail);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        TextView txtErr;
        Button btnClose;
        txtErr = (TextView) dialog.findViewById(R.id.txt_detail_err);
        btnClose = (Button) dialog.findViewById(R.id.btn_close);
        txtErr.setText("vui lòng nhập đầy địa chỉ");
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
    public void updateGarden() {
        String gardenName = mEdtGardenName.getText().toString().trim();
        String subAddressTmp = mEdtSubAddress.getText().toString().trim();

        mStrSubAddress = subAddressTmp.replaceAll("\\,","");

        mStrAddress = mStrSubAddress + ", " + mStrWard + ", " + mStrDistrict + ", " + mStrProvince;

        if (gardenName.equals("")) {
            showDialogNameErr();
            return;
        } else if (mStrSubAddress.equals("") || mStrWard.equals("") || mStrDistrict.equals("") || mStrProvince.equals("")) {
            showDialogAddressErr();
            return;
        }
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

                mStrWard = "";
                mStrDistrict = "";
                mTxtWard.setText(mStrWard);
                mTxtDistrict.setText(mStrDistrict);
                //
                mDistrictPresenter.getDistrictById(mIntDistrictId);
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

                mStrWard = "";
                mTxtWard.setText(mStrWard);

                mWardPresenter.getWardById(mIdWard);

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
                    Toast.makeText(getApplicationContext(), "Vui lòng chọn tỉnh/thành phố", Toast.LENGTH_SHORT).show();
                } else {
                    clickOpenDistrict();
                }
                break;
            case R.id.txt_ward:
                if (mStrProvince == null || mStrDistrict == null) {
                    Toast.makeText(getApplicationContext(),"Vui lòng chọn tỉnh/thành phố hoặc quận/huyện", Toast.LENGTH_SHORT).show();
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
        mProvincePresenter.getAllProvince();
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
