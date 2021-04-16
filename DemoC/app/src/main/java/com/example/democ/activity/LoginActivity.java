package com.example.democ.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.democ.R;
import com.example.democ.presenters.LoginPresenter;
import com.example.democ.room.entities.User;
import com.example.democ.views.LoginView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, LoginView {

    private Button mBtnLogin;
    private LinearLayout mLnlRegisterAccount, mLnlForgotPassword;
    private EditText mEdtUsername, mEdtPassword;

    private LoginPresenter mLoginPresenter;


    //token device
    private static String DEVICE_TOKEN = "";
    //token device
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initialView();
        initialData();
    }

    private void initialView() {

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();
                        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
                        System.out.println("MyFirebaseMessagingService");
                        System.out.println("token device: " + token);
                        System.out.println("MyFirebaseMessagingService");
                        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
                        DEVICE_TOKEN = token;
                        System.out.println("DDDD    DDDD    DDDDĐ   DDDDDDDDDDD");
                        System.out.println("dd: " + DEVICE_TOKEN);
                        System.out.println("DDDD    DDDD    DDDDĐ   DDDDDDDDDDD");


                    }
                });



        System.out.println("TTTTTTTTTT  TTTTTTTT        TTTTTTTTTTT     TTTTTTTTTTTTTTTTTt      TTTTTTTTTTTTTTTTT");
        System.out.println("TTTTTTTTTT  TTTTTTTT        TTTTTTTTTTT     TTTTTTTTTTTTTTTTTt      TTTTTTTTTTTTTTTTT");
        System.out.println("Device token: " + DEVICE_TOKEN);
        System.out.println("TTTTTTTTTT  TTTTTTTT        TTTTTTTTTTT     TTTTTTTTTTTTTTTTTt      TTTTTTTTTTTTTTTTT");
        System.out.println("TTTTTTTTTT  TTTTTTTT        TTTTTTTTTTT     TTTTTTTTTTTTTTTTTt      TTTTTTTTTTTTTTTTT");

        mBtnLogin = (Button) findViewById(R.id.btn_login);
        mBtnLogin.setOnClickListener(LoginActivity.this);
        mLnlRegisterAccount = (LinearLayout) findViewById(R.id.lnl_register_account);
        mLnlRegisterAccount.setOnClickListener(LoginActivity.this);
        mLnlForgotPassword = (LinearLayout) findViewById(R.id.lnl_forgot_password);
        mLnlForgotPassword.setOnClickListener(LoginActivity.this);
        mEdtUsername = (EditText) findViewById(R.id.edt_username);
        mEdtPassword = (EditText) findViewById(R.id.edt_password);
    }

    private void initialData() {
        mLoginPresenter = new LoginPresenter(getApplication(), this, this);
    }

    private void checkLogin() {
        String user = mEdtUsername.getText().toString();
        String pass = mEdtPassword.getText().toString();

//        mLoginPresenter.login(user, pass);
        System.out.println("LLLLLLL     LLLLLLLLLL  LLLLLLLLLLL");
        System.out.println("LLLL token: " + DEVICE_TOKEN);
        System.out.println("LLLLLLL     LLLLLLLLLL  LLLLLLLLLLL");
        mLoginPresenter.login(user, pass, DEVICE_TOKEN);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
//                Intent intentMain = new Intent(LoginActivity.this, MainActivity.class);
//                startActivity(intentMain);
                checkLogin();
                break;
            case R.id.lnl_register_account:
                Intent intentRegisterAccount = new Intent(LoginActivity.this, RegisterAccountActivity.class);
                startActivity(intentRegisterAccount);
                break;
            case R.id.lnl_forgot_password:
                Intent intentForgotPassword = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intentForgotPassword);
                break;
        }
    }

    @Override
    public void loginSuccess(User user) {
        Toast.makeText(this, "Login thanh cong (LoginActivity)", Toast.LENGTH_SHORT).show();
        System.out.println("2222222222222222222222  LOGIN SUCCESS   222222222222222222222222222222");
        Intent intentMain = new Intent(LoginActivity.this, MainActivity.class);
//        Bundle bundle = new Bundle();
//        bundle.putString("STRING_TOKEN", user.getToken());
//        System.out.println(user.getToken());
//        intentMain.putExtras(bundle);
        startActivity(intentMain);
//        finish();
    }

    @Override
    public void loginFail() {
        Toast.makeText(this, "Login fail (LoginActivity)", Toast.LENGTH_SHORT).show();
        System.out.println("33333333333333333333333333  LOGIN FAIL 3333333333333333333333333333333333");
    }
}
