package com.example.democ;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AddImagePostActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout mLnlBackAddImagePost;
    private TextView mTxtTakeCamera, mTxtTakeGallery;
//    Integer REQUEST_CAMERA = 1, SELECT_FILE = 0;
    private static final int CAMERA_REQUEST = 998;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_image_post);

        initialView();
        initialData();
    }

    private void initialView() {
        mLnlBackAddImagePost = (LinearLayout) findViewById(R.id.lnl_back_add_image_post);
        mLnlBackAddImagePost.setOnClickListener(AddImagePostActivity.this);

        //take camera
        mTxtTakeCamera = (TextView) findViewById(R.id.txt_take_camera);
        mTxtTakeCamera.setOnClickListener(AddImagePostActivity.this);

        //Gallery
        mTxtTakeGallery = (TextView) findViewById(R.id.txt_take_gallery);
        mTxtTakeGallery.setOnClickListener(AddImagePostActivity.this);
    }

    private void initialData() {
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lnl_back_add_image_post:
                Intent intent = new Intent(AddImagePostActivity.this, CreatePostFragment.class);
//                startActivity(intent);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
//                finish();
                break;
            case R.id.txt_take_camera:
                Intent intentC = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intentC, CAMERA_REQUEST);
//                startActivity(cameraIntent);
                break;
            case R.id.txt_take_gallery:
                Intent intentGa = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intentGa, 2);
//                startActivity(intentGa);
                break;
        }
    }
}
