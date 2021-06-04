package com.example.democ.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.democ.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VerifyPhoneNumberActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout mLnlBack;
    private TextView mTxtSubmitPhone, mTxtCancelPhone;
    private EditText mEdtSubmitPhoneNumber;

    private String mStrPhoneNumber = "", mStrVerificationId;
    private String mStrCodeOTP = "";

    private FirebaseAuth mAuth;
    private PhoneAuthProvider.ForceResendingToken mResendToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone_number);

        initialView();
        initialData();
    }

    private void initialView() {
        mLnlBack = (LinearLayout) findViewById(R.id.lnl_back);
        mLnlBack.setOnClickListener(this);
        mTxtSubmitPhone = (TextView) findViewById(R.id.txt_submit_phone);
        mTxtSubmitPhone.setOnClickListener(this);
        mTxtCancelPhone = (TextView) findViewById(R.id.txt_cancel_phone);
        mTxtCancelPhone.setOnClickListener(this);
        mEdtSubmitPhoneNumber = (EditText) findViewById(R.id.edt_submit_phone_number);
    }
    private void initialData() {
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lnl_back:
                finish();
                break;
            case R.id.txt_submit_phone:
                sendOTP();
                break;
            case R.id.txt_cancel_phone:
                finish();
                break;
        }
    }

    private void sendOTP() {
        mStrPhoneNumber = mEdtSubmitPhoneNumber.getText().toString().trim();
        if (mStrPhoneNumber.equals("") || mStrPhoneNumber.length() < 10 || mStrPhoneNumber.length() > 10) {
            /*show dialog input err*/
            showDialogInputErr();
            return;
        }

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+84" + mStrPhoneNumber,
                60, TimeUnit.SECONDS,
                VerifyPhoneNumberActivity.this,
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        System.out.println("AAAAAAAAAAAAA   AAAAAAAAA   AAAAAAAAAAAAAAAAA");
                        System.out.println("chay onVerificationCompleted");
                        System.out.println("AAAAAAAAAAAAA   AAAAAAAAA   AAAAAAAAAAAAAAAAA");

                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        System.out.println("BBBBBBBBBBB   BBBBBBBBBBB   BBBBBBBBBBB");
                        System.out.println("chay onVerificationFailed");
                        System.out.println("BBBBBBBBBBB   BBBBBBBBBBB   BBBBBBBBBBB");
                    }

                    @Override
                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(s, forceResendingToken);

                        System.out.println("CCCCCCCCCCCCC   CCCCCCCCCCCCC   CCCCCCCCCCCCCCCCCC");
                        System.out.println("chay onCodeSent");
                        System.out.println("CCCCCCCCCCCCC   CCCCCCCCCCCCC   CCCCCCCCCCCCCCCCCC");
                        mStrVerificationId = s;
                        mResendToken = forceResendingToken;
                        confirmPhoneNumber();
                    }
                }
        );
        System.out.println("TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT");
        System.out.println("phone number: " + mStrPhoneNumber);
        System.out.println("TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT");
    }
    /*submit code OTP*/
    private void confirmPhoneNumber() {
        final Dialog dialog = new Dialog(VerifyPhoneNumberActivity.this);
        dialog.setContentView(R.layout.dialog_confirm_phone_number);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        TextView txtSendCode;
        final EditText edtCode;
        LinearLayout lnlResendCode;
        edtCode = (EditText) dialog.findViewById(R.id.edt_code);
        txtSendCode = (TextView) dialog.findViewById(R.id.txt_send_confirm_code);
        lnlResendCode = (LinearLayout) dialog.findViewById(R.id.lnl_resend_code);

//        mStrCodeOTP = edtCode.getText().toString().trim();
//        final PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mStrVerificationId, mStrCodeOTP);

//        String code = edtCode.getText().toString().trim();
        txtSendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    System.out.println("1111111111111111111111111111111111111111111111111111");
                    mStrCodeOTP = edtCode.getText().toString().trim();
                    System.out.println("code otp: " + mStrCodeOTP);
                    System.out.println("2222222222222222222222222222222222222222222222222222");
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mStrVerificationId, mStrCodeOTP);
                    signInWithPhoneAuthCredential(credential);
                } catch (Exception ex) {
                    System.out.println("4444444444444444444444444444444444444444444444444444");
                    System.out.println("chat catch");
                    System.out.println("4444444444444444444444444444444444444444444444444444");
                    ex.printStackTrace();
                }
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
    /*confirm code*/
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential){
        mAuth.signInWithCredential(credential).addOnCompleteListener(VerifyPhoneNumberActivity.this,
                new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //
                if (task.isSuccessful()) {
                    Intent intent = new Intent(VerifyPhoneNumberActivity.this, RegisterAccountActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("PHONE_NUMBER_VERIFY", mStrPhoneNumber);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else {
                    showDialogConfirmCodeErr();
                }
            }
        });
    }
    /*showDialogInputErr*/
    private void showDialogInputErr() {
        final Dialog dialog = new Dialog(VerifyPhoneNumberActivity.this);
        dialog.setContentView(R.layout.dialog_login_fail);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        Button mBtnClose;
        TextView txtDetailErr;
        txtDetailErr = (TextView) dialog.findViewById(R.id.txt_detail_err);
        txtDetailErr.setText("Vui lòng nhập đúng số điện thoại");
        mBtnClose = (Button) dialog.findViewById(R.id.btn_close);
        mBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    /*showDialogConfirmCodeErr*/
    private void showDialogConfirmCodeErr() {
        final Dialog dialog = new Dialog(VerifyPhoneNumberActivity.this);
        dialog.setContentView(R.layout.dialog_login_fail);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        Button mBtnClose;
        TextView txtDetailErr;
        txtDetailErr = (TextView) dialog.findViewById(R.id.txt_detail_err);
        txtDetailErr.setText("Mã xác nhận không đúng");
        mBtnClose = (Button) dialog.findViewById(R.id.btn_close);
        mBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
