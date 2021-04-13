package com.example.democ.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.democ.R;
import com.example.democ.model.UpdateVegetableRequest;
import com.example.democ.model.VegetableData;
import com.example.democ.presenters.PersonalPresenter;
import com.example.democ.presenters.UpdateVegetablePresenter;
import com.example.democ.room.entities.User;
import com.example.democ.views.PersonalView;
import com.example.democ.views.UpdateVegetableView;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class UpdateVegetableActivity extends AppCompatActivity implements View.OnClickListener, UpdateVegetableView, PersonalView {

    private static final int REQUEST_CAMERA = 1;
    private static final int REQUEST_STORAGE = 2;
    private String mMediaPath = "";
    private List<MultipartBody.Part> mListImagePart;
    private MultipartBody.Part mRequestImage = null;

    private Button mBtnUpdateVegetable;
    private ImageView mImgVegetable;
    private EditText mEdtVegetableName, mEdtVegetableDescription, mEdtVegetableFeature, mEdtVegetableQuantity;
    private String mVegetableImg, mVegetableName, mVegetableDescription, mVegetableFeature, mVegetableId;
    private int mNoVegetable, mGardenId, mVegetableQuantity;

    private UpdateVegetablePresenter mUpdateVegetablePresenter;
    private User mUser;
    private PersonalPresenter mPersonalPresenter;
    private String mAccessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_vegetable);

        initialView();
        initialData();
    }

    private void initialView() {
        mPersonalPresenter = new PersonalPresenter(getApplicationContext(), this);
        mPersonalPresenter.getInfoPersonal();

        mBtnUpdateVegetable = (Button) findViewById(R.id.btn_update_vegetable);
        mImgVegetable = (ImageView) findViewById(R.id.img_vegetable);
        mEdtVegetableName = (EditText) findViewById(R.id.edt_vegetable_name);
        mEdtVegetableDescription = (EditText) findViewById(R.id.edt_vegetable_description);
        mEdtVegetableFeature = (EditText) findViewById(R.id.edt_vegetable_feature);
        mEdtVegetableQuantity = (EditText) findViewById(R.id.edt_vegetable_quantity);
        mImgVegetable.setOnClickListener(this);
        mBtnUpdateVegetable.setOnClickListener(this);
    }

    private void initialData() {
        mUpdateVegetablePresenter = new UpdateVegetablePresenter(getApplication(), this, this);
        /* getting info */
        getDataVegetableNew();
    }

    //getting data vegetable
    public void getDataVegetable() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            mVegetableName = bundle.getString("VEGETABLE_NAME");
            mVegetableDescription = bundle.getString("VEGETABLE_DESCRIPTION");
            mVegetableFeature = bundle.getString("VEGETABLE_FEATURE");
            mNoVegetable = bundle.getInt("VEGETABLE_STT");
            mVegetableImg = bundle.getString("VEGETABLE_IMAGE");
            mVegetableQuantity = bundle.getInt("VEGETABLE_QUANTITY");
            if (mVegetableImg.equals("")) {
                mImgVegetable.setImageResource(R.mipmap.addimage64);
            } else {
                Picasso.with(this).load(mVegetableImg)
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.caybacha)
                        .into(mImgVegetable);
            }
            mGardenId = bundle.getInt("GARDEN_ID");

            mEdtVegetableName.setText(mVegetableName);
            mEdtVegetableDescription.setText(mVegetableDescription);
            mEdtVegetableFeature.setText(mVegetableFeature);
            mEdtVegetableQuantity.setText(String.valueOf(mVegetableQuantity));
//            Picasso.with(this).load(mVegetableImg)
//                    .placeholder(R.drawable.ic_launcher_background)
//                    .error(R.drawable.caybacha)
//                    .into(mImgVegetable);
        }


    }
    public void getDataVegetableNew() {
        Intent intentGetData = getIntent();
        Bundle bundleGetData = intentGetData.getExtras();
        VegetableData vegetableData = (VegetableData) bundleGetData.getSerializable("qaz");
        if (bundleGetData != null) {
            mVegetableId = vegetableData.getId();
            mVegetableName = vegetableData.getName();
            mVegetableDescription = vegetableData.getDescription();
            mVegetableFeature = vegetableData.getFeature();
            int maxSize = vegetableData.getImageVegetables().size() - 1;
            if (vegetableData.getImageVegetables(). size() == 0 || vegetableData.getImageVegetables() == null) {
                mVegetableImg = "";
            } else {
                mVegetableImg = vegetableData.getImageVegetables().get(maxSize).getUrl();
            }
            if (mVegetableImg.equals("")) {
                mImgVegetable.setImageResource(R.mipmap.addimage64);
            } else {
                Picasso.with(this).load(mVegetableImg)
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.caybacha)
                        .into(mImgVegetable);
            }
            mVegetableQuantity = vegetableData.getQuantity();
            mGardenId = bundleGetData.getInt("GARDEN_ID");

            mEdtVegetableName.setText(mVegetableName);
            mEdtVegetableDescription.setText(mVegetableDescription);
            mEdtVegetableFeature.setText(mVegetableFeature);
            mEdtVegetableQuantity.setText(String.valueOf(mVegetableQuantity));

            System.out.println("TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT");
            System.out.println("TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT");
            System.out.println("1   veg id: " + vegetableData.getId());
            System.out.println("2   name-title: " + vegetableData.getName());
            System.out.println("3   description: " + vegetableData.getDescription());
            System.out.println("4   feature: " + vegetableData.getFeature());
            System.out.println("5   garden Id: " + mGardenId);
            System.out.println("6   image" + mVegetableImg);
            System.out.println("7   quantity: " + vegetableData.getQuantity());
            System.out.println("8   des Id: " + vegetableData.getIdDescription());
            System.out.println("9   isfix: " + vegetableData.isFixed());
            System.out.println("10   namesearch: " + vegetableData.getNameSearch());
            System.out.println("11  synonym: " + vegetableData.getSynonymOfFeature());
            System.out.println("TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT");
            System.out.println("TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT");
        }
    }

    //dialog quantity err
    private  void showDialogQuantityErr() {
        final Dialog dialog = new Dialog(UpdateVegetableActivity.this);
        dialog.setContentView(R.layout.dialog_login_fail);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);

        TextView txtDetail;
        Button btnOk;
        txtDetail = (TextView) dialog.findViewById(R.id.txt_detail_err);
        btnOk = (Button) dialog.findViewById(R.id.btn_close);
        txtDetail.setText("Vui lòng nhập số lượng");
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    //update vegetable
    public void updateVegetable() {
        mVegetableName = mEdtVegetableName.getText().toString();
        mVegetableDescription = mEdtVegetableDescription.getText().toString();
        mVegetableFeature = mEdtVegetableFeature.getText().toString();
        int quantityTmp = 0;
        try {
            quantityTmp = Integer.parseInt(mEdtVegetableQuantity.getText().toString());
        } catch (NumberFormatException ex) {
            Toast.makeText(getApplicationContext(), "khong covert dc quantity", Toast.LENGTH_SHORT).show();
        }

        if (quantityTmp == 0) {
            showDialogQuantityErr();
            return;
        } else {
            mVegetableQuantity = quantityTmp;
        }

        if (mMediaPath.equals("")) {
            mListImagePart = new ArrayList<>();
            File file = new File(mMediaPath);
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            mRequestImage = MultipartBody.Part.createFormData("newImages", mVegetableImg, requestBody);
            mListImagePart.add(mRequestImage);
            System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
            System.out.println("chay vao if");
            System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        } else {
            mListImagePart = new ArrayList<>();
            File file = new File(mMediaPath);
            String file_path = file.getAbsolutePath();
            String[] file_path_arr = file_path.split("\\.");
            file_path = file_path_arr[0] + System.currentTimeMillis() + "." + file_path_arr[1];
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            mRequestImage = MultipartBody.Part.createFormData("newImages", file_path, requestBody);
            mListImagePart.add(mRequestImage);
            System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
            System.out.println("mRequestImage: " + mRequestImage);
            System.out.println("chay vao else");
            System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
        }
        System.out.println("goi api ***************** ************************");
        System.out.println("access token: " + mAccessToken);
        System.out.println("access token muser: " + mUser.getToken());
        System.out.println("mVegetableId: " + mVegetableId);
        System.out.println("mVegetableName: " + mVegetableName);
        System.out.println("mVegetableDescription: " + mVegetableDescription);
        System.out.println("mVegetableFeature: " + mVegetableFeature);
        System.out.println("mVegetableQuantity: " + mVegetableQuantity);
        System.out.println("mGardenId: " + mGardenId);
        System.out.println("mListImagePart: " + mListImagePart);
//        mUpdateVegetablePresenter.updateVegetable(mVegetableId, mVegetableName, mVegetableDescription, mVegetableFeature,
//                mVegetableQuantity, mGardenId, mListImagePart, mAccessToken);
        UpdateVegetableRequest updateVegetableRequest = new UpdateVegetableRequest(mVegetableId, mVegetableName, mVegetableDescription,
                mVegetableFeature, mVegetableQuantity, mGardenId, mListImagePart);
        mUpdateVegetablePresenter.updateVegetable(updateVegetableRequest, mAccessToken);
        System.out.println("goi api ***************** ************************");

    }
    //take image
    private void showDialogTakeOfImage() {
        final Dialog dialog = new Dialog(UpdateVegetableActivity.this);
        dialog.setContentView(R.layout.dialog_take_of_image);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);

        TextView mTxtImgCamera, mTxtImgStorage;
        Button mBtnClose;

        mTxtImgCamera = (TextView) dialog.findViewById(R.id.txt_img_camera);
        mTxtImgStorage = (TextView) dialog.findViewById(R.id.txt_imh_storage);
        mBtnClose = (Button) dialog.findViewById(R.id.btn_close);

        mTxtImgCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intentCamera, REQUEST_CAMERA);
                dialog.dismiss();
            }
        });
        mTxtImgStorage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentStorage = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intentStorage, REQUEST_STORAGE);
                dialog.dismiss();
            }
        });
        mBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_update_vegetable:
                Toast.makeText(getApplicationContext(), "btn update vegetable", Toast.LENGTH_SHORT).show();
                updateVegetable();
                break;
            case R.id.img_vegetable:
                showDialogTakeOfImage();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        mStrUrl = URL_TELEPHONE;

        try {
            if (requestCode == REQUEST_STORAGE && resultCode == RESULT_OK && data != null){
                Uri selectImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectImage, filePathColumn, null, null, null);
                assert  cursor != null;
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                mMediaPath = cursor.getString(columnIndex);

                System.out.println("----------- **************** ---------------------");
                System.out.println("link tu bo nho");
                System.out.println("media path: " + mMediaPath);
                System.out.println("----------- **************** ---------------------");

                mImgVegetable.setImageBitmap(BitmapFactory.decodeFile(mMediaPath));
                cursor.close();

            }
        } catch (Exception ex) {
            Toast.makeText(this, "upload image fail", Toast.LENGTH_SHORT).show();
        }

        //cach 2
        try {

            if (requestCode == REQUEST_CAMERA && resultCode == RESULT_OK &&data != null) {
//                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
//                mImgCreateVegetable.setImageBitmap(bitmap);

                try {
                    Uri selectedImage = data.getData();
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bytes);


                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
                    File destination = new File(Environment.getExternalStorageDirectory() + "/" +
                            getString(R.string.app_name), "IMG_" + timeStamp + ".jpg");
                    FileOutputStream fo;
                    try {
                        destination.createNewFile();
                        fo = new FileOutputStream(destination);
                        fo.write(bytes.toByteArray());
                        fo.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    mMediaPath = destination.getAbsolutePath();
                    System.out.println("KKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK");
                    System.out.println("link came ra");
                    System.out.println("*************");
                    System.out.println("medipath: " + mMediaPath);
                    System.out.println("*************");
                    System.out.println("KKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK");
                    mImgVegetable.setImageBitmap(bitmap);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception ex) {
            Toast.makeText(this, "upload image 22222222222", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void updateVegetableSuccess(VegetableData vegetableData) {
        Intent intent = new Intent(UpdateVegetableActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void updateVegetableFail() {

    }

    @Override
    public void showInfoPersonal(User user) {
        mUser = user;
        mAccessToken = mUser.getToken();
    }
}
