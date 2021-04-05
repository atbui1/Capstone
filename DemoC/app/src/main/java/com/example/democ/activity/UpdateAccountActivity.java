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
import com.example.democ.model.Account;
import com.example.democ.presenters.GetInfoAccountPresenter;
import com.example.democ.presenters.PersonalPresenter;
import com.example.democ.presenters.UpdateAccountPresenter;
import com.example.democ.room.entities.User;
import com.example.democ.room.managements.UserManagement;
import com.example.democ.views.GetInfoAccountView;
import com.example.democ.views.PersonalView;
import com.example.democ.views.UpdateAccountView;

import java.util.Calendar;

public class UpdateAccountActivity extends AppCompatActivity implements View.OnClickListener, PersonalView,
        GetInfoAccountView, UpdateAccountView {

    private EditText mEdtPassword, mEdtPasswordConfirm, mEdtFullName, mEdtEmail;
    private TextView mTxtYOB, mTxtSex;
    private Button mBtnUpdateAccount;
    private LinearLayout mLnlBack;
    private static String phone = "", password = "", passwordConfirm = "",
            fullName = "", sexTmp = "", email = "", yob = "", accountId = "", mAccessToken = "";
    private int sex;

    private PersonalPresenter mPersonalPresenter;
    private GetInfoAccountPresenter mGetInfoAccountPresenter;
    private UpdateAccountPresenter mUpdateAccountPresenter;


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
        mEdtPassword = (EditText) findViewById(R.id.edt_password);
        mEdtPasswordConfirm = (EditText) findViewById(R.id.edt_password_confirm);
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

    }

    private void initialData() {
        password = mEdtPassword.getText().toString().trim();
        passwordConfirm = mEdtPasswordConfirm.getText().toString().trim();
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
        password = mEdtPassword.getText().toString().trim();
        passwordConfirm = mEdtPasswordConfirm.getText().toString().trim();
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
        System.out.println("lllllllllllllllllllllllllllll");
        System.out.println(password);
        System.out.println(passwordConfirm);
        System.out.println(fullName);
        System.out.println(email);
        System.out.println(yob);
        System.out.println(sex);
        if (!password.equals(passwordConfirm)) {
            Toast.makeText(getApplicationContext(), "vui long xac nhan mk", Toast.LENGTH_SHORT).show();
        } else {
            //chay update
            Account account = new Account(accountId,phone, password, fullName, yob, sex, email);
            mUpdateAccountPresenter.updateAccount(account, mAccessToken);
        }
        System.out.println("lllllllllllllllllllllllllllllllll");
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
                clickBack();
                break;
        }
    }

    @Override
    public void showInfoPersonal(User user) {
        mAccessToken = user.getToken();
        System.out.println("TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT");
        System.out.println(mAccessToken);
        System.out.println("TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT");
        mGetInfoAccountPresenter.getInfoAccount(mAccessToken);
    }

    @Override
    public void getInfoAccountSuccess(Account account) {
        phone = account.getPhone();
        password = account.getPassword();
        fullName = account.getFullName();
        yob = account.getYOB();
        sex = account.getSex();
        email = account.getEmail();
        accountId = account.getId();


        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        System.out.println(phone);
        System.out.println(password);
        System.out.println(fullName);
        System.out.println(yob);
        System.out.println(sex);
        System.out.println(email);
        System.out.println(accountId);
        System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");

        mEdtPassword.setText(password);
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
    public void updateAccountSuccess(Account account) {
        Intent intent = new Intent(UpdateAccountActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void updateAccountFail() {

    }
}
