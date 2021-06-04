package com.example.democ.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
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
import com.example.democ.presenters.ProvincePresenter;
import com.example.democ.presenters.RegisterAccountPresenter;
import com.example.democ.presenters.WardPresenter;
import com.example.democ.views.DistrictView;
import com.example.democ.views.ProvinceView;
import com.example.democ.views.RegisterAccountView;
import com.example.democ.views.WardView;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthProvider;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class RegisterAccountActivity extends AppCompatActivity implements View.OnClickListener, RegisterAccountView,
        ProvinceView, DistrictView, WardView {

    private LinearLayout mLnlBackRegisterLogin;
    private EditText mEdtPhoneNumber, mEdtPassword, mEditPasswordConfirm, mEdtFullName, mEdtEmail;
    private Button mBtnRegisterAccount;
    private TextView mTxtSex, mTxtYOB, mTxtPhoneNumber;
    private AwesomeValidation awesomeValidation;
    private RegisterAccountPresenter mRegisterAccountPresenter;

    private static String mStrPhone = "", mStrPassword = "", mStrPasswordConfirm = "", mStrFullName = "", mStrSexTmp = "",
            mStrEmail = "", mStrYOB = "";
    private int mIntSex;
    private boolean active = true;

    /*address*/
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
        setContentView(R.layout.activity_register_account);

        initialView();
        initialData();

    }

    private void initialView() {

        mRegisterAccountPresenter = new RegisterAccountPresenter(getApplication(), this, this);
        mProvincePresenter = new ProvincePresenter(getApplication(), this, this);
        mDistrictPresenter = new DistrictPresenter(getApplication(), this, this);
        mWardPresenter = new WardPresenter(getApplication(), this, this);
        mProvincePresenter.getAllProvince();

        mLnlBackRegisterLogin = (LinearLayout) findViewById(R.id.lnl_back_register_login);
        mLnlBackRegisterLogin.setOnClickListener(RegisterAccountActivity.this);
        mTxtPhoneNumber = (TextView) findViewById(R.id.txt_phone_number);
        mEdtPhoneNumber = (EditText) findViewById(R.id.edt_phone_number);
        mEdtPassword = (EditText) findViewById(R.id.edt_password);
        mEditPasswordConfirm = (EditText) findViewById(R.id.edt_password_confirm);
        mBtnRegisterAccount = (Button) findViewById(R.id.btn_register_account);
        mBtnRegisterAccount.setOnClickListener(RegisterAccountActivity.this);
        mTxtSex = (TextView) findViewById(R.id.txt_sex);
        mTxtSex.setOnClickListener(RegisterAccountActivity.this);
        mTxtYOB = (TextView) findViewById(R.id.txt_yob);
        mTxtYOB.setOnClickListener(RegisterAccountActivity.this);
        mEdtFullName = (EditText) findViewById(R.id.edt_full_name);
        mEdtEmail = (EditText) findViewById(R.id.edt_email);

        mTxtProvince = (TextView) findViewById(R.id.txt_province);
        mTxtDistrict = (TextView) findViewById(R.id.txt_district);
        mTxtWard = (TextView) findViewById(R.id.txt_ward);
        mEdtSubAddress = (EditText) findViewById(R.id.edt_sub_address);
        mTxtProvince.setOnClickListener(this);
        mTxtDistrict.setOnClickListener(this);
        mTxtWard.setOnClickListener(this);

    }

    private void initialData() {
        getPhoneNumber();
        mEdtPhoneNumber.setText((mStrPhone.trim().length() > 0) ? mStrPhone : "");
        mEdtPassword.setText((mStrPassword.trim().length() > 0) ? mStrPassword : "");
        mEditPasswordConfirm.setText((mStrPasswordConfirm.trim().length() > 0) ? mStrPasswordConfirm : "");
        mEdtFullName.setText((mStrFullName.trim().length() > 0) ? mStrFullName : "");
        mEdtEmail.setText((mStrEmail.trim().length() > 0) ? mStrEmail : "");
        mTxtSex.setText((mStrSexTmp.trim().length() > 0) ? mStrSexTmp : "");
        mTxtYOB.setText((mStrYOB.trim().length() > 0) ? mStrYOB : "");
    }

    private void getPhoneNumber() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            mStrPhone = bundle.getString("PHONE_NUMBER_VERIFY");
            mTxtPhoneNumber.setText(mStrPhone);
        }
    }

    private void setData() {
        mStrPhone = mEdtPhoneNumber.getText().toString().trim();
        mStrPassword = mEdtPassword.getText().toString().trim();
        mStrPasswordConfirm = mEditPasswordConfirm.getText().toString().trim();
        mStrFullName = mEdtFullName.getText().toString().trim();
        mStrEmail = mEdtEmail.getText().toString().trim();
        mStrSexTmp = mTxtSex.getText().toString().trim();
        mStrYOB = mTxtYOB.getText().toString().trim();
        active = true;

        if (mStrSexTmp.equals("Nam")) {
            mIntSex = 1;
        } else if (mStrSexTmp.equals("Nữ")) {
            mIntSex = 2;
        } else if (mStrSexTmp.equals("Khác")) {
            mIntSex = 3;
        }
    }

    private void confirmPhoneNumber(String mStrPhone) {
        final Dialog dialog = new Dialog(RegisterAccountActivity.this);
        dialog.setContentView(R.layout.dialog_confirm_phone_number);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        TextView txtSendCode;
        EditText edtCode;
        LinearLayout lnlResendCode;
        edtCode = (EditText) dialog.findViewById(R.id.edt_code);
        txtSendCode = (TextView) dialog.findViewById(R.id.txt_send_confirm_code);
        lnlResendCode = (LinearLayout) dialog.findViewById(R.id.lnl_resend_code);

        String code = edtCode.getText().toString().trim();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
    /*register account*/
    private void registerAccount() {
        setData();
//        password = mEdtPassword.getText().toString();
//        passwordConfirm = mEditPasswordConfirm.getText().toString();
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this, R.id.edt_phone_number, Patterns.PHONE, R.string.phone_error);
        awesomeValidation.addValidation(this, R.id.edt_password, RegexTemplate.NOT_EMPTY, R.string.empty_error);
        awesomeValidation.addValidation(this, R.id.edt_full_name, RegexTemplate.NOT_EMPTY, R.string.empty_error);
        awesomeValidation.addValidation(this, R.id.edt_email, Patterns.EMAIL_ADDRESS, R.string.email_error);
//        awesomeValidation.addValidation(this, R.id.txt_sex, RegexTemplate.NOT_EMPTY, R.string.empty_error);
//        awesomeValidation.addValidation(this, R.id.txt_yob, RegexTemplate.NOT_EMPTY, R.string.empty_error);

        System.out.println("HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH");
        System.out.println("password " + mStrPassword);
        System.out.println("confirm pass " + mStrPasswordConfirm);
        System.out.println("HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH");

        String subAddressTmp = mEdtSubAddress.getText().toString().trim();
        mStrSubAddress = subAddressTmp.replaceAll("\\,","");
        if (mStrProvince.equals("") || mStrDistrict.equals("") || mStrWard.equals("") || mStrSubAddress.equals("")) {
            System.out.println("show dia log err distric null or ward null");
            showDialogAddressErr();
            return;
        }

        mStrAddress = mStrSubAddress + ", " + mStrWard + ", " + mStrDistrict + ", " + mStrProvince;


        if (awesomeValidation.validate()) {
            if (mStrPassword.equals(mStrPasswordConfirm)) {
                System.out.println("if pass = passconfirm 4444444444444444444444444444444444");
                AccountData accountData = new AccountData(mStrPhone, mStrPassword, mStrFullName, mStrYOB, mIntSex, mStrEmail);
                System.out.println("REGISTER 111111111111111111111111111111111111");
                System.out.println("phone: " +mStrPhone);
                System.out.println("password: " +mStrPassword);
                System.out.println("fullname: " +mStrFullName);
                System.out.println("birthday: " +mStrYOB);
                System.out.println("sex: " + mIntSex);
                System.out.println("address: " + mStrAddress);
                System.out.println("email: " + mStrEmail);
                System.out.println("active: " + active);
                System.out.println("mIntProvinceId: " + mIntProvinceId);
                System.out.println("mIntDistrictId: " + mIntDistrictId);
                System.out.println("mIntWardId: " + mIntWardId);
                System.out.println("REGISTER 22222222222222222222222222222222222222");

                RequestBody phone = RequestBody.create(MediaType.parse("text/plain"), mStrPhone);
                RequestBody pass = RequestBody.create(MediaType.parse("text/plain"), mStrPassword);
                RequestBody fullName = RequestBody.create(MediaType.parse("text/plain"), mStrFullName);
                RequestBody YOB = RequestBody.create(MediaType.parse("text/plain"), mStrYOB);
                RequestBody sex = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(mIntSex));
                RequestBody address = RequestBody.create(MediaType.parse("text/plain"), mStrAddress);
                RequestBody email = RequestBody.create(MediaType.parse("text/plain"), mStrEmail);
                RequestBody provinceId = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(mIntProvinceId));
                RequestBody districtId = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(mIntDistrictId));
                RequestBody wardId = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(mIntWardId));
                /*api register*/
//                confirmPhoneNumber(mStrPhone);

                mRegisterAccountPresenter.registerAccount(phone, pass, fullName, YOB, sex, address, email, provinceId, districtId, wardId);

            } else {
                showDialogConfirmPassErr();
                System.out.println("REGISTER ssssssssssssssssssssssssssssssssssss");
                awesomeValidation.addValidation(this, R.id.edt_password_confirm, mStrPasswordConfirm, R.string.password_confirm_error);
                System.out.println("REGISTER xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
                return;
            }
        }
    }

    // get province
    private void clickOpenProvince() {
        ProvinceBottomSheetFragment provinceBottomSheetFragment = new ProvinceBottomSheetFragment((ArrayList<ProvinceData>) mListProvince, new IClickProvince() {
            @Override
            public void clickProvince(ProvinceData provinceData) {
                mTxtProvince.setText(provinceData.getName());
                mIntDistrictId = provinceData.getId();
                mStrProvince = provinceData.getName();
                mIntProvinceId = provinceData.getId();
                //
                mTxtDistrict.setText("");
                mTxtWard.setText("");
                mEdtSubAddress.setText("");
                mStrDistrict = "";
                mStrWard = "";
                mStrSubAddress = "";
                mDistrictPresenter.getDistrictById(mIntDistrictId);
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
                mIntWardId = districtData.getId();
                mStrDistrict = districtData.getName();
                mIntDistrictId = districtData.getId();
                mWardPresenter.getWardById(mIntWardId);

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
                mIntWardId = wardData.getId();
                mIntDistrictId = wardData.getDistrictID();
            }
        });
        wardBottomSheetFragment.show(getSupportFragmentManager(), wardBottomSheetFragment.getTag());
    }
    private void showDialogAddressErr() {
        final Dialog dialog = new Dialog(RegisterAccountActivity.this);
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
    private void showDialogConfirmPassErr() {
        final Dialog dialog = new Dialog(RegisterAccountActivity.this);
        dialog.setContentView(R.layout.dialog_login_fail);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        TextView txtErr;
        Button btnClose;
        txtErr = (TextView) dialog.findViewById(R.id.txt_detail_err);
        btnClose = (Button) dialog.findViewById(R.id.btn_close);
        txtErr.setText("vui lòng nhập đúng mật khẩu");
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
    /*register success*/
    private void showDialogRegisterSuccess() {
        final Dialog dialog = new Dialog(RegisterAccountActivity.this);
        dialog.setContentView(R.layout.dialog_login_fail);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        TextView txtErr;
        Button btnClose;
        txtErr = (TextView) dialog.findViewById(R.id.txt_detail_err);
        btnClose = (Button) dialog.findViewById(R.id.btn_close);
        txtErr.setText("Đăng ký tài khoản thành công");
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Intent intentMainHome = new Intent(RegisterAccountActivity.this, LoginActivity.class);
                startActivity(intentMainHome);
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
    /*register success*/
    private void showDialogRegisterFail() {
        final Dialog dialog = new Dialog(RegisterAccountActivity.this);
        dialog.setContentView(R.layout.dialog_login_fail);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        TextView txtErr;
        Button btnClose;
        txtErr = (TextView) dialog.findViewById(R.id.txt_detail_err);
        btnClose = (Button) dialog.findViewById(R.id.btn_close);
        txtErr.setText("Đăng ký tài khoản không thành công");
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
            case R.id.lnl_back_register_login:
                Intent intentRegisterLogin = new Intent(RegisterAccountActivity.this, LoginActivity.class);
                startActivity(intentRegisterLogin);
                break;
            case R.id.btn_register_account:
                registerAccount();
//                sendPhoneNumberToFirebase();
                break;
            case R.id.txt_sex:
                showSexDialog();
                break;
            case R.id.txt_yob:
                DatePickerDialog datePickerDialog = new DatePickerDialog(RegisterAccountActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth, d,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
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
                    Toast.makeText(getApplicationContext(),"Vui lòng chọn tỉnh/thành phố, quận/huyện", Toast.LENGTH_SHORT).show();
                } else {
                    clickOpenWard();
                }
                break;
        }
    }

    private void showSexDialog() {
        final Dialog dialog = new Dialog(RegisterAccountActivity.this);
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


    @Override
    public void registerAccountSuccess() {
        showDialogRegisterSuccess();
    }

    @Override
    public void registerAccountFail() {
        showDialogRegisterFail();
    }

    @Override
    public void getProvinceSuccess(List<ProvinceData> provinceData) {
        mListProvince = provinceData;
    }

    @Override
    public void getProvinceFail() {

    }

    @Override
    public void getDistrictSuccess(List<DistrictData> districtData) {
        mListDistrict = districtData;
    }

    @Override
    public void getDistrictFail() {

    }

    @Override
    public void getWardSuccess(List<WardData> wardData) {
        mListWard = wardData;
    }

    @Override
    public void getWardFail() {

    }

    private void sendPhoneNumberToFirebase() {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+840976298669",
                60, TimeUnit.SECONDS,
                RegisterAccountActivity.this,
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        System.out.println("SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS");
                        System.out.println("SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS");
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        System.out.println("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF");
                        System.out.println("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF");
                        e.printStackTrace();
                    }
                }
        );

    }
}
