package com.example.democ.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.democ.R;
import com.example.democ.presenters.PersonalPresenter;
import com.example.democ.presenters.UploadAvatarPresenter;
import com.example.democ.room.entities.User;
import com.example.democ.views.PersonalView;
import com.example.democ.views.UploadAvatarView;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import gun0912.tedbottompicker.TedBottomPicker;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


public class UploadAvatarActivity extends AppCompatActivity implements View.OnClickListener, PersonalView, UploadAvatarView {

    private LinearLayout mLnlBack, mLnlAvatar;
    private ImageView mImgAvatar;
    private Button mBtnUpload;

    private User mUser;
    private PersonalPresenter mPersonalPresenter;
    private UploadAvatarPresenter mUploadAvatarPresenter;
    private MultipartBody.Part mRequestImage = null;
    private String mMediaPath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_avatar);

        initialView();
        initialData();
    }

    private void initialView() {

        checkPermission();
        mPersonalPresenter = new PersonalPresenter(getApplicationContext(), this);
        mPersonalPresenter.getInfoPersonal();
        mUploadAvatarPresenter = new UploadAvatarPresenter(getApplication(), getApplicationContext(), this);

        mLnlBack = (LinearLayout) findViewById(R.id.lnl_back);
        mLnlBack.setOnClickListener(this);
        mImgAvatar = (ImageView) findViewById(R.id.img_upload_avatar);
        mBtnUpload = (Button) findViewById(R.id.btn_upload_avatar);
        mBtnUpload.setOnClickListener(this);
        mLnlAvatar = (LinearLayout) findViewById(R.id.lnl_avatar);
        mLnlAvatar.setOnClickListener(this);
    }
    private void initialData() {
    }

    private void uploadAvatar() {
        File file = new File(mMediaPath);
        String file_path = file.getAbsolutePath();
        String[] file_path_arr = file_path.split("\\.");
        file_path = file_path_arr[0] + System.currentTimeMillis() + "." + file_path_arr[1];
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        mRequestImage = MultipartBody.Part.createFormData("newItem", file_path, requestBody);

        mUploadAvatarPresenter.uploadImage(mRequestImage, mUser.getToken());
    }
    /*upload avatar success*/
    private void showDialogUploadAvatarSuccess() {
        final Dialog dialog = new Dialog(UploadAvatarActivity.this);
        dialog.setContentView(R.layout.dialog_exchange_quantity_err);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        TextView txtQuantity;
        Button btnClose;
        btnClose = (Button) dialog.findViewById(R.id.btn_close);
        txtQuantity = (TextView) dialog.findViewById(R.id.txt_exchange_quantity);
        txtQuantity.setText("Cập nhật ảnh đại diện thành công");

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UploadAvatarActivity.this, MainActivity.class);
                startActivity(intent);
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
            case R.id.lnl_avatar:
//                openImagePicker();
                checkPermission();
                break;
            case R.id.btn_upload_avatar:
                /*goi api upload avatar*/
                uploadAvatar();
                break;
        }
    }

    public void checkPermission() {
//        PermissionListener permissionListener = new PermissionListener() {
//            @Override
//            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
//
//            }
//
//            @Override
//            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
//
//            }
//
//            @Override
//            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
//
//            }
//        };
        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                openImagePicker();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(getApplicationContext(), "Bạn chưa cấp quyền cho ứng dụng", Toast.LENGTH_SHORT).show();
            }
        };

        TedPermission.with(UploadAvatarActivity.this)
                .setPermissionListener(permissionListener)
                .setDeniedMessage("Vui lòng cấp quyền để sử dụng chức năng này\n\n vui lòng cấp phép quyền ứng dụng [Cài đặt] > [Quyền truy cập]")
                .setPermissions(Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();
    }

    private void openImagePicker() {
        TedBottomPicker.OnImageSelectedListener listener = new TedBottomPicker.OnImageSelectedListener() {
            @Override
            public void onImageSelected(Uri uri) {
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    mImgAvatar.setImageBitmap(bitmap);

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
//
                    System.out.println("----------- **************** ---------------------");
                    System.out.println("link tu camera");
//                    System.out.println("bitmap: " + bitmap);
                    System.out.println("media path: " + mMediaPath);
                    System.out.println("----------- **************** ---------------------");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        };


        TedBottomPicker tedBottomPicker = new TedBottomPicker.Builder(UploadAvatarActivity.this)
                .setOnImageSelectedListener(listener)
                .create();
        tedBottomPicker.show(getSupportFragmentManager());

    }

    @Override
    public void showInfoPersonal(User user) {
        mUser = user;
    }

    @Override
    public void uploadAvatarSuccess() {
        showDialogUploadAvatarSuccess();
    }

    @Override
    public void uploadAvatarFail() {

    }
}
