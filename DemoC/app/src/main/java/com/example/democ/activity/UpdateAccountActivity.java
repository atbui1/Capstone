package com.example.democ.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.democ.R;
import com.example.democ.fragment.DistrictBottomSheetFragment;
import com.example.democ.fragment.ProvinceBottomSheetFragment;
import com.example.democ.fragment.WardBottomSheetFragment;
import com.example.democ.iclick.IClickDistrict;
import com.example.democ.iclick.IClickProvince;
import com.example.democ.iclick.IClickWard;
import com.example.democ.model.AccountData;
import com.example.democ.model.DistrictData;
import com.example.democ.model.ProvinceData;
import com.example.democ.model.WardData;
import com.example.democ.presenters.DistrictPresenter;
import com.example.democ.presenters.GetInfoAccountPresenter;
import com.example.democ.presenters.PersonalPresenter;
import com.example.democ.presenters.ProvincePresenter;
import com.example.democ.presenters.UpdateAccountPresenter;
import com.example.democ.presenters.WardPresenter;
import com.example.democ.room.entities.User;
import com.example.democ.views.DistrictView;
import com.example.democ.views.GetInfoAccountView;
import com.example.democ.views.PersonalView;
import com.example.democ.views.ProvinceView;
import com.example.democ.views.UpdateAccountView;
import com.example.democ.views.WardView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class UpdateAccountActivity extends AppCompatActivity implements View.OnClickListener, PersonalView,
        GetInfoAccountView, UpdateAccountView, ProvinceView, DistrictView, WardView {

    private EditText mEdtFullName, mEdtEmail;
    private TextView mTxtYOB, mTxtSex;
    private Button mBtnUpdateAccount;
    private LinearLayout mLnlBack;
    private static String phone = "", password = "", passwordConfirm = "",
            fullName = "", sexTmp = "", email = "", yob = "", accountId = "", mAccessToken = "";
    private int sex;

    private User mUser;
    private PersonalPresenter mPersonalPresenter;
    private GetInfoAccountPresenter mGetInfoAccountPresenter;
    private UpdateAccountPresenter mUpdateAccountPresenter;

    private ProvincePresenter mProvincePresenter;
    private DistrictPresenter mDistrictPresenter;
    private WardPresenter mWardPresenter;
    private TextView mTxtProvince, mTxtDistrict, mTxtWard;
    private EditText mEdtSubAddress;
    private  int mIntProvinceId = 0, mIntDistrictId = 0, mIntWardId = 0;
    private String mStrProvince = "", mStrDistrict = "", mStrWard = "", mStrSubAddress = "", mStrAddress = "";
    private List<ProvinceData> mListProvince;
    private List<DistrictData> mListDistrict;
    private List<WardData> mListWard;


    Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            monthOfYear = monthOfYear + 1;
            String date = year + "-" + monthOfYear + "-" + dayOfMonth;
            mTxtYOB.setText(date);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_account);

        initialView();
        initialData();
    }

    private void initialView() {
        mEdtFullName = (EditText) findViewById(R.id.edt_full_name);
        mEdtEmail = (EditText) findViewById(R.id.edt_email);
        mTxtYOB = (TextView) findViewById(R.id.txt_yob);
        mTxtSex = (TextView) findViewById(R.id.txt_sex);
        mBtnUpdateAccount = (Button) findViewById(R.id.btn_update_account);
        mLnlBack = (LinearLayout) findViewById(R.id.lnl_back);
        mLnlBack.setOnClickListener(this);

        mBtnUpdateAccount.setOnClickListener(this);
        mTxtYOB.setOnClickListener(this);
        mTxtSex.setOnClickListener(this);

        mPersonalPresenter = new PersonalPresenter(getApplicationContext(), this);
        mPersonalPresenter.getInfoPersonal();
        mGetInfoAccountPresenter = new GetInfoAccountPresenter(getApplication(), this, this);
        mUpdateAccountPresenter = new UpdateAccountPresenter(getApplication(), this, this);

        mProvincePresenter = new ProvincePresenter(getApplication(), this, this);
        mDistrictPresenter = new DistrictPresenter(getApplication(), this, this);
        mWardPresenter = new WardPresenter(getApplication(), this, this);
        mProvincePresenter.getAllProvince();

        mTxtProvince = (TextView) findViewById(R.id.txt_province);
        mTxtDistrict = (TextView) findViewById(R.id.txt_district);
        mTxtWard = (TextView) findViewById(R.id.txt_ward);
        mEdtSubAddress = (EditText) findViewById(R.id.edt_sub_address);
        mTxtProvince.setOnClickListener(this);
        mTxtDistrict.setOnClickListener(this);
        mTxtWard.setOnClickListener(this);

    }

    private void initialData() {

        fullName = mEdtFullName.getText().toString().trim();
        email = mEdtEmail.getText().toString().trim();
        yob = mTxtYOB.getText().toString().trim();
        sexTmp = mTxtSex.getText().toString().trim();
        if (sexTmp.equals("Nam")) {
            sex = 1;
        } else if (sexTmp.equals("Nữ")) {
            sex = 2;
        } else if (sexTmp.equals("Khác")) {
            sex = 3;
        }
    }

    private void updateAccount() {

        fullName = mEdtFullName.getText().toString().trim();
        email = mEdtEmail.getText().toString().trim();
        yob = mTxtYOB.getText().toString().trim();
        sexTmp = mTxtSex.getText().toString().trim();
        if (sexTmp.equals("Nam")) {
            sex = 1;
        } else if (sexTmp.equals("Nữ")) {
            sex = 2;
        } else if (sexTmp.equals("Khác")) {
            sex = 3;
        }

        String subAddressTmp = mEdtSubAddress.getText().toString().trim();
        mStrSubAddress = subAddressTmp.replaceAll("\\,","");
        if (mStrProvince.equals("") || mStrDistrict.equals("") || mStrWard.equals("") || mStrSubAddress.equals("")
                || fullName.equals("")) {
            System.out.println("show dia log err district null or ward null");
            showDialogAddressErr();
            return;
        }
        mStrAddress = mStrSubAddress + ", " + mStrWard + ", " + mStrDistrict + ", " + mStrProvince;

        System.out.println("lllllllllllllllllllllllllllll");
        System.out.println(accountId);
        System.out.println(fullName);
        System.out.println(email);
        System.out.println(yob);
        System.out.println(sex);
        System.out.println(mStrAddress);
            //chay update
        AccountData accountData = new AccountData(accountId, fullName, yob, sex, mIntProvinceId,
                mIntDistrictId, mIntWardId, email, mStrAddress);
        mUpdateAccountPresenter.updateAccount(accountData, mAccessToken);
    }

    private void showSexDialog() {
        final Dialog dialog = new Dialog(UpdateAccountActivity.this);
        dialog.setContentView(R.layout.dialog_sex);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        TextView mTxtNam, mTxtNu, mTxtKhac;
        mTxtNam = (TextView) dialog.findViewById(R.id.txt_nam);
        mTxtNu = (TextView) dialog.findViewById(R.id.txt_nu);
        mTxtKhac = (TextView) dialog.findViewById(R.id.txt_khac);

        mTxtNam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTxtSex.setText("Nam");
                dialog.dismiss();
            }
        });
        mTxtNu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTxtSex.setText("Nữ");
                dialog.dismiss();
            }
        });
        mTxtKhac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTxtSex.setText("Khác");
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void clickBack() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    private void showDialogAddressErr() {
        final Dialog dialog = new Dialog(UpdateAccountActivity.this);
        dialog.setContentView(R.layout.dialog_login_fail);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        TextView txtErr;
        Button btnClose;
        txtErr = (TextView) dialog.findViewById(R.id.txt_detail_err);
        btnClose = (Button) dialog.findViewById(R.id.btn_close);
        txtErr.setText("vui lòng nhập đầy đủ thông tin");
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
    /*update faul*/
    private void showDialogUpdateErr() {
        final Dialog dialog = new Dialog(UpdateAccountActivity.this);
        dialog.setContentView(R.layout.dialog_login_fail);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        TextView txtErr;
        Button btnClose;
        txtErr = (TextView) dialog.findViewById(R.id.txt_detail_err);
        btnClose = (Button) dialog.findViewById(R.id.btn_close);
        txtErr.setText("Vui lòng kiểm tra lại");
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
    /*update success*/
    private void showDialogUpdateSuccess() {
        final Dialog dialog = new Dialog(UpdateAccountActivity.this);
        dialog.setContentView(R.layout.dialog_login_fail);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        TextView txtErr;
        Button btnClose;
        txtErr = (TextView) dialog.findViewById(R.id.txt_detail_err);
        btnClose = (Button) dialog.findViewById(R.id.btn_close);
        txtErr.setText("cập nhật thông tin thành công");
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Intent intent = new Intent(UpdateAccountActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    // get province
    private void clickOpenProvince() {
        ProvinceBottomSheetFragment provinceBottomSheetFragment = new ProvinceBottomSheetFragment((ArrayList<ProvinceData>) mListProvince, new IClickProvince() {
            @Override
            public void clickProvince(ProvinceData provinceData) {
                mTxtProvince.setText(provinceData.getName());
//                mIntDistrictId = provinceData.getId();
                mIntProvinceId = provinceData.getId();
                mStrProvince = provinceData.getName();
                //
                mTxtDistrict.setText("");
                mTxtWard.setText("");
                mEdtSubAddress.setText("");
                mStrDistrict = "";
                mStrWard = "";
                mStrSubAddress = "";
                mDistrictPresenter.getDistrictById(mIntProvinceId);
            }
        });
        provinceBottomSheetFragment.show(getSupportFragmentManager(), provinceBottomSheetFragment.getTag());
        provinceBottomSheetFragment.setCancelable(false);
    }
    //get district by id
    private void clickOpenDistrict() {
        DistrictBottomSheetFragment districtBottomSheetFragment = new DistrictBottomSheetFragment((ArrayList<DistrictData>) mListDistrict, new IClickDistrict() {
            @Override
            public void clickDistrict(DistrictData districtData) {
                mTxtDistrict.setText(districtData.getName());
//                mIntWardId = districtData.getId();
                mIntDistrictId = districtData.getId();
                mStrDistrict = districtData.getName();
                mStrWard = "";
                mStrSubAddress = "";
                mEdtSubAddress.setText("");
                mTxtWard.setText("");
                mWardPresenter.getWardById(mIntDistrictId);

            }
        });
        districtBottomSheetFragment.show(getSupportFragmentManager(), districtBottomSheetFragment.getTag());
    }
    //get ward by id
    private void clickOpenWard() {
        WardBottomSheetFragment wardBottomSheetFragment = new WardBottomSheetFragment((ArrayList<WardData>) mListWard, new IClickWard() {
            @Override
            public void clickWard(WardData wardData) {
                mTxtWard.setText(wardData.getName());
                mStrWard = wardData.getName();
                mStrSubAddress = "";
                mEdtSubAddress.setText("");
            }
        });
        wardBottomSheetFragment.show(getSupportFragmentManager(), wardBottomSheetFragment.getTag());
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_yob:
                DatePickerDialog datePickerDialog = new DatePickerDialog(UpdateAccountActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth, d,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
                break;
            case R.id.txt_sex:
                showSexDialog();
                break;
            case R.id.btn_update_account:
                updateAccount();
                break;
            case R.id.lnl_back:
//                clickBack();
                finish();
                break;
            case R.id.txt_province:
                clickOpenProvince();
                break;
            case R.id.txt_district:
                if (mStrProvince == null || mStrProvince.equals("")) {
                    Toast.makeText(getApplicationContext(), "Vui lòng chọn tỉnh/thành phố", Toast.LENGTH_SHORT).show();
                } else {
                    clickOpenDistrict();
                }
                break;
            case R.id.txt_ward:
                if (mStrProvince == null || mStrDistrict == null || mStrProvince.equals("") || mStrDistrict.equals("")) {
                    Toast.makeText(getApplicationContext(),"Vui lòng chọn tỉnh/thành phố, quận/huyện", Toast.LENGTH_SHORT).show();
                } else {
                    clickOpenWard();
                }
                break;
        }
    }

    @Override
    public void showInfoPersonal(User user) {
        mAccessToken = user.getToken();
        mUser = user;
        System.out.println("TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT");
        System.out.println(mAccessToken);
        System.out.println("TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT");
        mGetInfoAccountPresenter.getInfoAccount(user.getAccountId(), mAccessToken);
    }

    @Override
    public void getInfoAccountSuccess(AccountData accountData) {
        phone = accountData.getPhone();
        password = accountData.getPassword();
        fullName = accountData.getFullName();
        yob = accountData.getYOB().substring(0,10);
        sex = accountData.getSex();
        email = accountData.getEmail();
        accountId = accountData.getId();
        /*new address*/
        mIntProvinceId = accountData.getProvinceId();
        mIntDistrictId = accountData.getDistrictId();
        mIntWardId = accountData.getWardId();
        mStrAddress = accountData.getAddress();
        String[] addressTmp = mStrAddress.split(",");
        if (addressTmp != null) {
            mStrSubAddress = addressTmp[0].trim();
            mStrWard = addressTmp[1].trim();
            mStrDistrict = addressTmp[2].trim();
            mStrProvince = addressTmp[3].trim();
            mEdtSubAddress.setText(mStrSubAddress);
            mTxtWard.setText(mStrWard);
            mTxtDistrict.setText(mStrDistrict);
            mTxtProvince.setText(mStrProvince);
        }

        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        System.out.println(phone);
        System.out.println(password);
        System.out.println(fullName);
        System.out.println(yob);
        System.out.println(sex);
        System.out.println(email);
        System.out.println(accountId);
        System.out.println("mIntProvinceId: " + mIntProvinceId);
        System.out.println("mIntDistrictId: " + mIntDistrictId);
        System.out.println("mIntWardId: " + mIntWardId);
        System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");

        mDistrictPresenter.getDistrictById(mIntProvinceId);
        mWardPresenter.getWardById(mIntDistrictId);

        mEdtFullName.setText(fullName);
        if (sex == 1) {
            mTxtSex.setText("Nam");
        } else if(sex == 2) {
            mTxtSex.setText("Nữ");
        } else if(sex == 3) {
            mTxtSex.setText("Khác");
        }
        mTxtYOB.setText(yob);
        mEdtEmail.setText(email);

    }

    @Override
    public void getInfoAccountFail() {

    }

    @Override
    public void updateAccountSuccess(AccountData accountData) {
        showDialogUpdateSuccess();
    }

    @Override
    public void updateAccountFail() {
        showDialogUpdateErr();
    }

    @Override
    public void getProvinceSuccess(List<ProvinceData> provinceData) {
        System.out.println("11111111111111111111111111111111111111111111111111111");

        mListProvince = provinceData;

        System.out.println("mListProvince: " + mListProvince.size());
    }

    @Override
    public void getProvinceFail() {

    }

    @Override
    public void getDistrictSuccess(List<DistrictData> districtData) {
        System.out.println("222222222222222222222222222222222222222222222222222222");

        mListDistrict = districtData;

        System.out.println("mListDistrict: " + mListDistrict.size());
    }

    @Override
    public void getDistrictFail() {

    }

    @Override
    public void getWardSuccess(List<WardData> wardData) {
        System.out.println("3333333333333333333333333333333333333333333333333333333333333333");

        mListWard = wardData;

        System.out.println("mListWard: " + mListWard.size());
    }

    @Override
    public void getWardFail() {

    }
}
