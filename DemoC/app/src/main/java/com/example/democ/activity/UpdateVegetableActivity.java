package com.example.democ.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.democ.R;
import com.squareup.picasso.Picasso;

public class UpdateVegetableActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtnUpdateVegetable;
    private ImageView mImgVegetable;
    private EditText mEdtVegetableName, mEdtVegetableDescription, mEdtVegetableFeature;
    private String mVegetableImg, mVegetableName, mVegetableDescription, mVegetableFeature;
    private int mNoVegetable, mGardenId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_vegetable);

        initialView();
        initialData();
    }

    private void initialView() {
        mBtnUpdateVegetable = (Button) findViewById(R.id.btn_update_vegetable);
        mImgVegetable = (ImageView) findViewById(R.id.img_vegetable);
        mEdtVegetableName = (EditText) findViewById(R.id.edt_vegetable_name);
        mEdtVegetableDescription = (EditText) findViewById(R.id.edt_vegetable_description);
        mEdtVegetableFeature = (EditText) findViewById(R.id.edt_vegetable_feature);
        mBtnUpdateVegetable.setOnClickListener(this);
    }

    private void initialData() {
        getDataVegetable();
        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        System.out.println(mVegetableImg);
        System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
    }

    public void getDataVegetable() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            mVegetableName = bundle.getString("VEGETABLE_NAME");
            mVegetableDescription = bundle.getString("VEGETABLE_DESCRIPTION");
            mVegetableFeature = bundle.getString("VEGETABLE_FEATURE");
            mNoVegetable = bundle.getInt("VEGETABLE_STT");
            mVegetableImg = bundle.getString("VEGETABLE_IMAGE");
            mGardenId = bundle.getInt("GARDEN_ID");

            mEdtVegetableName.setText(mVegetableName);
            mEdtVegetableDescription.setText(mVegetableDescription);
            mEdtVegetableFeature.setText(mVegetableFeature);
            Picasso.with(this).load(mVegetableImg)
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.caybacha)
                    .into(mImgVegetable);
        }


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_update_vegetable:
                Toast.makeText(getApplicationContext(), "btn update vegetable", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
