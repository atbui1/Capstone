package com.example.democ.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.democ.R;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout mLnlConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        initialView();
        initialData();
    }

    private void initialView() {
        mLnlConfirmPassword = (LinearLayout) findViewById(R.id.lnl_back_forgot_pass_login);
        mLnlConfirmPassword.setOnClickListener(ForgotPasswordActivity.this);
    }

    private void initialData() {
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lnl_back_forgot_pass_login:
                Intent intentForgotPassLogin = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                startActivity(intentForgotPassLogin);
                break;
        }
    }
}
