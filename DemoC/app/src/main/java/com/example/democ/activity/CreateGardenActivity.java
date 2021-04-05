package com.example.democ.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.democ.R;
import com.example.democ.model.Garden;
import com.example.democ.model.GardenResult;
import com.example.democ.presenters.CreateGardenPresenter;
import com.example.democ.room.entities.User;
import com.example.democ.room.managements.UserManagement;
import com.example.democ.views.CreateGardenView;

public class CreateGardenActivity extends AppCompatActivity implements View.OnClickListener, CreateGardenView {

    private User mUser;
    private UserManagement mUserManagement;
    private EditText mEdtGardenName, mEdtGardenAddress;
    private Button mBtnCreateGarden;

    private CreateGardenPresenter mCreateGardenPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_garden);

        initialView();
        initialData();
    }

    private void initialView() {
        mEdtGardenName = (EditText) findViewById(R.id.edt_garden_name);
        mEdtGardenAddress = (EditText) findViewById(R.id.edt_garden_address);
        mBtnCreateGarden = (Button) findViewById(R.id.btn_create_garden);
        mBtnCreateGarden.setOnClickListener(this);
    }

    private void initialData() {
        mUserManagement = new UserManagement(getApplication());
        mUserManagement.getmUserInfo(new UserManagement.OnDataCallBackUser() {
            @Override
            public void onDataSuccess(User user) {
                mUser = user;
            }

            @Override
            public void onDataFail() {

            }
        });
        mCreateGardenPresenter = new CreateGardenPresenter(getApplication(), this, this);
    }

    public void createGarden() {
        String name = mEdtGardenName.getText().toString();
        String address = mEdtGardenAddress.getText().toString();
        String token = mUser.getToken();
        System.out.println(name);
        System.out.println(token);
        System.out.println(address);
        Garden garden = new Garden(name, address);
        mCreateGardenPresenter.createGarden(garden, token);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_create_garden:
                createGarden();
                break;
        }
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
