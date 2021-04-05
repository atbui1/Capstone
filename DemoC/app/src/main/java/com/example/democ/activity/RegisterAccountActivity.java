package com.example.democ.activity;

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

import java.util.Calendar;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.example.democ.R;
import com.example.democ.model.Account;
import com.example.democ.presenters.RegisterAccountPresenter;
import com.example.democ.views.RegisterAccountView;

public class RegisterAccountActivity extends AppCompatActivity implements View.OnClickListener, RegisterAccountView {

    private LinearLayout mLnlBackRegisterLogin;
    private EditText mEdtPhoneNumber, mEdtPassword, mEditPasswordConfirm, mEdtFullName, mEdtEmail;
    private Button mBtnRegisterAccount;
    private TextView mTxtSex, mTxtYOB;
    private AwesomeValidation awesomeValidation;
    private RegisterAccountPresenter mRegisterAccountPresenter;

    private static String phone = "", password = "", passwordConfirm = "", fullName = "", sexTmp = "", email = "", yob = "";
    private int sex;
    private boolean active = true;

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

        mLnlBackRegisterLogin = (LinearLayout) findViewById(R.id.lnl_back_register_login);
        mLnlBackRegisterLogin.setOnClickListener(RegisterAccountActivity.this);
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
    }

    private void initialData() {
        mEdtPhoneNumber.setText((phone.trim().length() > 0) ? phone : "");
        mEdtPassword.setText((password.trim().length() > 0) ? password : "");
        mEditPasswordConfirm.setText((passwordConfirm.trim().length() > 0) ? passwordConfirm : "");
        mEdtFullName.setText((fullName.trim().length() > 0) ? fullName : "");
        mEdtEmail.setText((email.trim().length() > 0) ? email : "");
        mTxtSex.setText((sexTmp.trim().length() > 0) ? sexTmp : "");
        mTxtYOB.setText((yob.trim().length() > 0) ? yob : "");
    }

    private void setData() {
        phone = mEdtPhoneNumber.getText().toString();
        password = mEdtPassword.getText().toString();
        passwordConfirm = mEditPasswordConfirm.getText().toString();
        fullName = mEdtFullName.getText().toString();
        email = mEdtEmail.getText().toString();
        sexTmp = mTxtSex.getText().toString();
        yob = mTxtYOB.getText().toString();
        active = true;

        if (sexTmp.equals("Nam")) {
            sex = 1;
        } else if (sexTmp.equals("Nữ")) {
            sex = 2;
        } else if (sexTmp.equals("Khác")) {
            sex = 3;
        }
    }

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
        System.out.println("password " + password);
        System.out.println("confirm pass " + passwordConfirm);
        System.out.println("HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH");
        if (awesomeValidation.validate()) {
            if (password.equals(passwordConfirm)) {
                System.out.println("if pass = passconfirm 4444444444444444444444444444444444");
                Account account = new Account(phone, password, fullName, yob, sex, email);
                System.out.println("REGISTER 111111111111111111111111111111111111");
                System.out.println("phone: " +phone);
                System.out.println("password: " +password);
                System.out.println("fullname: " +fullName);
                System.out.println("birthday: " +yob);
                System.out.println("sex: " + sex);
                System.out.println("email: " + email);
                System.out.println("active: " + active);
                System.out.println("REGISTER 22222222222222222222222222222222222222");
                mRegisterAccountPresenter.registerAccount(account);

            } else {
                System.out.println("REGISTER ssssssssssssssssssssssssssssssssssss");
                awesomeValidation.addValidation(this, R.id.edt_password_confirm, passwordConfirm, R.string.password_confirm_error);
                System.out.println("REGISTER xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
            }
        }
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
//                setData();
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
        Toast.makeText(this, "register Successfully", Toast.LENGTH_SHORT).show();
        Intent intentMainHome = new Intent(RegisterAccountActivity.this, LoginActivity.class);
        startActivity(intentMainHome);
        finish();
    }

    @Override
    public void registerAccountFail() {
        Toast.makeText(this, "register fail", Toast.LENGTH_SHORT).show();
    }
}
