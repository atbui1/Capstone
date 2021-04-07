package com.example.democ.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.democ.R;
import com.squareup.picasso.Picasso;

public class UpdatePostActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout mLnlBack;
    private Button mBtnUpdatePost;
    private EditText mEdtContent, mEdtQuantity;
    private ImageView mImgPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_post);

        initialView();
        initialData();
    }

    private void initialView() {
        mLnlBack = (LinearLayout) findViewById(R.id.lnl_back);
        mLnlBack.setOnClickListener(this);
        mBtnUpdatePost = (Button) findViewById(R.id.btn_update_post);
        mBtnUpdatePost.setOnClickListener(this);
        mEdtContent = (EditText) findViewById(R.id.edt_post_content);
        mEdtQuantity = (EditText) findViewById(R.id.edt_post_quantity);
        mImgPost = (ImageView) findViewById(R.id.img_post_content);

        String content = "";
        String urlImage = "";
        int quantity = 0;
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            content = bundle.getString("POST_CONTENT");
            urlImage = bundle.getString("POST_IMAGE");
            quantity = bundle.getInt("POST_QUANTITY");
        } else {
            content = "";
            urlImage = "";
            quantity = 0;
        }

        mEdtContent.setText(content);
        mEdtQuantity.setText(quantity + "");
        if (urlImage.equals("")) {
            mImgPost.setImageResource(R.mipmap.addimage64);
        } else {
            Picasso.with(getApplicationContext()).load(urlImage).into(mImgPost);
        }
    }

    private void initialData() {
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lnl_back:
                finish();
                break;
            case R.id.btn_update_post:
                System.out.println("TTTTTTTTTTTTTTTTTTTTTTTTTTTT");
                System.out.println("btn update post");
                System.out.println("TTTTTTTTTTTTTTTTTTTTTTTTTTTT");
                break;
        }
    }
}
