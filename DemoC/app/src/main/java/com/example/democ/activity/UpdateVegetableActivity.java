package com.example.democ.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.democ.R;
import com.example.democ.model.UpdateVegetableResponse;
import com.example.democ.model.VegetableData;
import com.example.democ.presenters.PersonalPresenter;
import com.example.democ.presenters.UpdateVegetablePresenter;
import com.example.democ.room.entities.User;
import com.example.democ.views.PersonalView;
import com.example.democ.views.UpdateVegetableView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class UpdateVegetableActivity extends AppCompatActivity implements View.OnClickListener, UpdateVegetableView, PersonalView {

    private static final int REQUEST_CAMERA = 1;
    private static final int REQUEST_STORAGE = 2;
    private final static String KEY_VEGETABLE_UPDATE = "KEY_VEGETABLE_UPDATE";
    private final static String KEY_VEGETABLE_SEND_UPDATE = "KEY_VEGETABLE_SEND_UPDATE";
    private final String URL_TELEPHONE = "URL_TELEPHONE";
    private final String URL_LINK = "URL_LINK";


    private String mMediaPath = "", mStrCheckLink = "";
    private List<MultipartBody.Part> mListImagePart;
    private MultipartBody.Part mRequestImage = null;

    private Button mBtnUpdateVegetable;
    private ImageView mImgVegetable;
    private LinearLayout mLnlBack;
    private EditText mEdtVegetableName, mEdtVegetableDescription, mEdtVegetableFeature, mEdtVegetableQuantity;
    private String mVegetableImg, mVegetableName, mVegetableDescription, mVegetableFeature, mVegetableId, mGardenName, mGardenAddress;
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
        checkPermission();

        mPersonalPresenter = new PersonalPresenter(getApplicationContext(), this);
        mPersonalPresenter.getInfoPersonal();

        mBtnUpdateVegetable = (Button) findViewById(R.id.btn_update_vegetable);
        mImgVegetable = (ImageView) findViewById(R.id.img_vegetable);
        mEdtVegetableName = (EditText) findViewById(R.id.edt_vegetable_name);
        mEdtVegetableDescription = (EditText) findViewById(R.id.edt_vegetable_description);
        mEdtVegetableFeature = (EditText) findViewById(R.id.edt_vegetable_feature);
        mEdtVegetableQuantity = (EditText) findViewById(R.id.edt_vegetable_quantity);
        mLnlBack = (LinearLayout) findViewById(R.id.lnl_back);
        mLnlBack.setOnClickListener(this);
        mImgVegetable.setOnClickListener(this);
        mBtnUpdateVegetable.setOnClickListener(this);
    }

    private void initialData() {
        mUpdateVegetablePresenter = new UpdateVegetablePresenter(getApplication(), this, this);
        /* getting info */
        getDataVegetableNew();
    }

    public void getDataVegetableNew() {
        Intent intentGetData = getIntent();
        Bundle bundleGetData = intentGetData.getExtras();
        VegetableData vegetableData = (VegetableData) bundleGetData.getSerializable(KEY_VEGETABLE_SEND_UPDATE);
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
            mGardenName = bundleGetData.getString("GARDEN_NAME");
            mGardenAddress = bundleGetData.getString("GARDEN_ADDRESS");

            mEdtVegetableName.setText(mVegetableName);
            mEdtVegetableDescription.setText(mVegetableDescription);
            mEdtVegetableFeature.setText(mVegetableFeature);
            mEdtVegetableQuantity.setText(String.valueOf(mVegetableQuantity));

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
    /*incomplete information*/
    private  void showDialogInputInfoyErr() {
        final Dialog dialog = new Dialog(UpdateVegetableActivity.this);
        dialog.setContentView(R.layout.dialog_login_fail);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);

        TextView txtDetail;
        Button btnOk;
        txtDetail = (TextView) dialog.findViewById(R.id.txt_detail_err);
        btnOk = (Button) dialog.findViewById(R.id.btn_close);
        txtDetail.setText("Vui lòng điền đủ thông tin");
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    /*update vegetable*/
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
        } else if (mVegetableName.equals("") || mVegetableDescription.equals("") || mVegetableFeature.equals("")) {
            showDialogInputInfoyErr();
            return;
        }
//        else {
//            mVegetableQuantity = quantityTmp;
//        }

        RequestBody requestLinkImage = null;
        if (mStrCheckLink.equals(URL_TELEPHONE)) {
            mListImagePart = new ArrayList<>();
            File file = new File(mMediaPath);
            String file_path = file.getAbsolutePath();
            String[] file_path_arr = file_path.split("\\.");
            file_path = file_path_arr[0] + System.currentTimeMillis() + "." + file_path_arr[1];
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            mRequestImage = MultipartBody.Part.createFormData("newImages", file_path, requestBody);
            mListImagePart.add(mRequestImage);
            mVegetableImg = "";
            requestLinkImage = RequestBody.create(MediaType.parse("text/plain"), mVegetableImg);
            System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
            System.out.println("chay vao if URL_CAMERA");
            System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        } else {
            mRequestImage = null;
            requestLinkImage = RequestBody.create(MediaType.parse("text/plain"), mVegetableImg);
            System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
            System.out.println("mRequestImage: " + mRequestImage);
            System.out.println("chay vao else");
            System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
        }
        RequestBody requestId = RequestBody.create(MediaType.parse("text/plain"), mVegetableId);
        RequestBody requestTitle = RequestBody.create(MediaType.parse("text/plain"), mVegetableName);
        RequestBody requestDescription = RequestBody.create(MediaType.parse("text/plain"), mVegetableDescription);
        RequestBody requestFeature = RequestBody.create(MediaType.parse("text/plain"), mVegetableFeature);
        RequestBody requestQuantity = (RequestBody) RequestBody.create(MediaType.parse("text/plain"), String.valueOf(quantityTmp));
        RequestBody requestGardenId = (RequestBody) RequestBody.create(MediaType.parse("text/plain"), String.valueOf(mGardenId));


        System.out.println("goi api ***************** ************************");
        System.out.println("access token: " + mAccessToken);
        System.out.println("access token muser: " + mUser.getToken());
        System.out.println("mVegetableId: " + mVegetableId);
        System.out.println("mVegetableName: " + mVegetableName);
        System.out.println("mVegetableDescription: " + mVegetableDescription);
        System.out.println("mVegetableFeature: " + mVegetableFeature);
        System.out.println("mVegetableQuantity: " + mVegetableQuantity);
        System.out.println("media path: " + mMediaPath);
        System.out.println("link image: " + mVegetableImg);
        System.out.println("mGardenId: " + mGardenId);

        mUpdateVegetablePresenter.updateVegetable(requestId, requestTitle, requestDescription, requestFeature, requestQuantity,
                requestGardenId, requestLinkImage, mRequestImage, mAccessToken);
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
            case R.id.lnl_back:
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            mStrCheckLink = URL_TELEPHONE;
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

            } else if (requestCode == REQUEST_CAMERA && resultCode == RESULT_OK &&data != null) {
                try {
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bytes);


                    File path = Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_PICTURES);
                    File file = new File(path, ".jpg");
                    FileOutputStream fo;
                    try {
                        path.mkdirs();
                        fo = new FileOutputStream(file);
                        fo.write(bytes.toByteArray());
                        fo.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    mMediaPath = file.getAbsolutePath();

                    System.out.println("----------- **************** ---------------------");
                    System.out.println("link tu camera");
                    System.out.println("media path: " + mMediaPath);
                    System.out.println("----------- **************** ---------------------");
                    mImgVegetable.setImageBitmap(bitmap);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception ex) {
            Toast.makeText(this, "upload image fail", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void updateVegetableSuccess(UpdateVegetableResponse updateVegetableResponse) {
        Intent intent = new Intent(UpdateVegetableActivity.this, GardenActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("GARDEN_ID", mGardenId);
        bundle.putString("GARDEN_NAME", mGardenName);
        bundle.putString("GARDEN_ADDRESS", mGardenAddress);

        intent.putExtra(KEY_VEGETABLE_UPDATE, bundle);
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

    /*check permission*/
    private void checkPermission() {
        Dexter.withContext(UpdateVegetableActivity.this)
                .withPermissions(Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                        if (multiplePermissionsReport.areAllPermissionsGranted()) {

                        } else {
//                            showDialogCheckPermissionAgain();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {

                    }
                }).onSameThread().check();
    }
}
