package com.example.democ.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.democ.R;
import com.example.democ.presenters.ChangePassPresenter;
import com.example.democ.presenters.PersonalPresenter;
import com.example.democ.room.entities.User;
import com.example.democ.views.ChangePassView;
import com.example.democ.views.PersonalView;

public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener, PersonalView, ChangePassView {


    private LinearLayout mLnlBack;
    private EditText mEdtPass, mEdtPassNew, mEdtPassNewConfirm;
    private TextView mTxtCancel, mTxtSaveChange;

    private String mStrPass = "", mStrPassNew = "", mStrPassNewConfirm = "";

    private User mUser;
    private PersonalPresenter mPersonalPresenter;
    private ChangePassPresenter mChangePassPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        initialView();
        initialData();
    }

    private void initialView() {
        mPersonalPresenter = new PersonalPresenter(getApplicationContext(), this);
        mPersonalPresenter.getInfoPersonal();
        mChangePassPresenter = new ChangePassPresenter(getApplication(), getApplicationContext(), this);

        mLnlBack = (LinearLayout) findViewById(R.id.lnl_back);
        mLnlBack.setOnClickListener(this);
        mEdtPass = (EditText) findViewById(R.id.edt_pass);
        mEdtPassNew = (EditText) findViewById(R.id.edt_pass_new);
        mEdtPassNewConfirm = (EditText) findViewById(R.id.edt_pass_new_confirm);
        mTxtCancel = (TextView) findViewById(R.id.txt_cancel);
        mTxtCancel.setOnClickListener(this);
        mTxtSaveChange = (TextView) findViewById(R.id.txt_save_change);
        mTxtSaveChange.setOnClickListener(this);
    }
    private void initialData() {
    }

    private void saveChangePass() {
        mStrPass = mEdtPass.getText().toString().trim();
        mStrPassNew = mEdtPassNew.getText().toString().trim();
        mStrPassNewConfirm = mEdtPassNewConfirm.getText().toString().trim();
        if (mStrPass.equals("") || mStrPassNew.equals("") || mStrPassNewConfirm.equals("")) {
            showDialogInputErr();
            return;
        } else if (mStrPassNew.length() < 6 || mStrPass.length() < 6) {
            showDialogPassLengthErr();
            return;
        } else if (!mStrPassNewConfirm.equals(mStrPassNew)) {
            showDialogConfirmPassErr();
            return;
        }
        mChangePassPresenter.changePass(mStrPass, mStrPassNew, mUser.getToken());
    }
    /*Input err*/
    private void showDialogInputErr() {
        final Dialog dialog = new Dialog(ChangePasswordActivity.this);
        dialog.setContentView(R.layout.dialog_login_fail);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        TextView txtErr;
        Button btnClose;
        txtErr = (TextView) dialog.findViewById(R.id.txt_detail_err);
        btnClose = (Button) dialog.findViewById(R.id.btn_close);
        txtErr.setText("vui l??ng nh???p ????? th??ng tin");
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
    /*pass length err*/
    private void showDialogPassLengthErr() {
        final Dialog dialog = new Dialog(ChangePasswordActivity.this);
        dialog.setContentView(R.layout.dialog_login_fail);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        TextView txtErr;
        Button btnClose;
        txtErr = (TextView) dialog.findViewById(R.id.txt_detail_err);
        btnClose = (Button) dialog.findViewById(R.id.btn_close);
        txtErr.setText("M???t kh???u c?? ??t nh???t 6 k?? t???");
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
    /*confirm err*/
    private void showDialogConfirmPassErr() {
        final Dialog dialog = new Dialog(ChangePasswordActivity.this);
        dialog.setContentView(R.layout.dialog_login_fail);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        TextView txtErr;
        Button btnClose;
        txtErr = (TextView) dialog.findViewById(R.id.txt_detail_err);
        btnClose = (Button) dialog.findViewById(R.id.btn_close);
        txtErr.setText("vui l??ng nh???p ????ng m???t kh???u m???i");
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
    /*change pass success*/
    private void showDialogChangePassSuccess() {
        final Dialog dialog = new Dialog(ChangePasswordActivity.this);
        dialog.setContentView(R.layout.dialog_login_fail);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        TextView txtErr;
        Button btnClose;
        txtErr = (TextView) dialog.findViewById(R.id.txt_detail_err);
        btnClose = (Button) dialog.findViewById(R.id.btn_close);
        txtErr.setText("?????i m???t kh???u th??nh c??ng");
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Intent intent = new Intent(ChangePasswordActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
    /*Old pass err*/
    private void showDialogOldPassErr() {
        final Dialog dialog = new Dialog(ChangePasswordActivity.this);
        dialog.setContentView(R.layout.dialog_login_fail);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        TextView txtErr;
        Button btnClose;
        txtErr = (TextView) dialog.findViewById(R.id.txt_detail_err);
        btnClose = (Button) dialog.findViewById(R.id.btn_close);
        txtErr.setText("M???t kh???u c?? kh??ng ????ng");
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
            case R.id.lnl_back:
                finish();
                break;
            case R.id.txt_cancel:
                finish();
                break;
            case R.id.txt_save_change:
                saveChangePass();
                break;
        }
    }

    @Override
    public void showInfoPersonal(User user) {
        mUser = user;
    }

    @Override
    public void changePassSuccess() {
        showDialogChangePassSuccess();
    }

    @Override
    public void changePassFail() {
        showDialogOldPassErr();
    }
}
