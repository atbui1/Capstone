package com.example.democ.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.democ.R;
import com.example.democ.model.PostData;
import com.example.democ.model.ShareDetail;
import com.example.democ.model.ShareRequest;
import com.example.democ.presenters.PersonalPresenter;
import com.example.democ.presenters.UpdatePostPresenter;
import com.example.democ.room.entities.User;
import com.example.democ.views.PersonalView;
import com.example.democ.views.UpdatePostView;
import com.squareup.picasso.Picasso;

public class UpdatePostActivity extends AppCompatActivity implements View.OnClickListener, UpdatePostView, PersonalView {

    private final String KEY_UPDATE_POST = "KEY_UPDATE_POST";

    private LinearLayout mLnlBack;
    TextView mTxtNameVegetable;
    private Button mBtnUpdatePost;
    private EditText mEdtContent, mEdtQuantity;
    private ImageView mImgPost;

    private String mStrPostContent = "", mStrUrl = "", mStrNameVegetable = "";
    private int mIntPostQuantity = 0, mIntCheckStatus = 1, mIntQuantityOfVeg = 0, mIntQuantityDonate = 0;
    private PostData mPostData;

    private User mUser;
    private PersonalPresenter mPersonalPresenter;
    private UpdatePostPresenter mUpdatePostPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_post);

        initialView();
        initialData();
    }

    private void initialView() {
        mPersonalPresenter = new PersonalPresenter(getApplicationContext(), this);
        mPersonalPresenter.getInfoPersonal();

        mLnlBack = (LinearLayout) findViewById(R.id.lnl_back);
        mLnlBack.setOnClickListener(this);
        mBtnUpdatePost = (Button) findViewById(R.id.btn_update_post);
        mBtnUpdatePost.setOnClickListener(this);
        mEdtContent = (EditText) findViewById(R.id.edt_post_content);
        mEdtQuantity = (EditText) findViewById(R.id.edt_post_quantity);
        mImgPost = (ImageView) findViewById(R.id.img_post_content);
        mTxtNameVegetable = (TextView) findViewById(R.id.txt_name_vegetable);


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            mPostData = (PostData) bundle.getSerializable(KEY_UPDATE_POST);
            if (mPostData != null) {

                mStrPostContent = mPostData.getContent().trim();
                mIntPostQuantity = mPostData.getQuantity();
                mIntCheckStatus = mPostData.getStatius();
                mIntQuantityOfVeg = mPostData.getQuantityVeg();
                mStrNameVegetable = mPostData.getVegName();
                if (mPostData.getImageVegetablesList() == null || mPostData.getImageVegetablesList().size() == 0) {
                    mStrUrl = "";
                } else {
                    int maxSize = mPostData.getImageVegetablesList().size() - 1;
                    mStrUrl = mPostData.getImageVegetablesList().get(maxSize).getUrl().trim();
                }
            }

            bundle.clear();
        }

        mEdtContent.setText(mStrPostContent);
        mEdtQuantity.setText(String.valueOf(mIntPostQuantity));
        mTxtNameVegetable.setText("Tên: " + mStrNameVegetable);
        if (mStrUrl.equals("")) {
            mImgPost.setImageResource(R.mipmap.addimage64);
        } else {
            Picasso.with(getApplicationContext()).load(mStrUrl).into(mImgPost);
        }
    }

    private void initialData() {

        mUpdatePostPresenter = new UpdatePostPresenter(getApplication(), getApplicationContext(), this);
    }

//    private void updatePost(PostData postData) {
//        if (postData.getStatius() == 1) {
//            /*update post share*/
//            updatePostShare(postData);
//        } else if (postData.getStatius() == 2) {
//            /*update post exchange*/
//        }
//    }
    private void updatePostShare(PostData postData) {
        mStrPostContent = mEdtContent.getText().toString().trim();
        String quantityEmp = mEdtQuantity.getText().toString().trim();

        if (quantityEmp.equals("")) {
            /*show err zero*/
            showDialogInputDonateZero();
            return;
        }
        mIntQuantityDonate = Integer.parseInt(quantityEmp);
        if (mIntQuantityDonate > postData.getQuantityVeg()) {
            /*show err quantity donate*/
            showDialogQuantityDonate(postData.getQuantityVeg());
            return;
        }

        System.out.println("SSSSSSSSSSSSS       khong loi SSSSSSSSSSSSSSSSSSSSSS");
        ShareRequest shareRequest = new ShareRequest(postData.getId(), mStrPostContent, mIntQuantityDonate,
                postData.getStatius(), postData.getVegetableId());
        mUpdatePostPresenter.updatePost(shareRequest, mUser.getToken());
    }
    /*quantity donate zero*/
    private void showDialogInputDonateZero() {
        final Dialog dialog = new Dialog(UpdatePostActivity.this);
        dialog.setContentView(R.layout.dialog_exchange_quantity_err);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        TextView txtQuantity;
        Button btnClose;
        btnClose = (Button) dialog.findViewById(R.id.btn_close);
        txtQuantity = (TextView) dialog.findViewById(R.id.txt_exchange_quantity);
        txtQuantity.setText("Vui lòng nhập số lượng cho");

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
    /*quantity donate > quantity ò vegetable*/
    private void showDialogQuantityDonate(int quantityOfVeg) {
        final Dialog dialog = new Dialog(UpdatePostActivity.this);
        dialog.setContentView(R.layout.dialog_exchange_quantity_err);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        TextView txtQuantity;
        Button btnClose;
        btnClose = (Button) dialog.findViewById(R.id.btn_close);
        txtQuantity = (TextView) dialog.findViewById(R.id.txt_exchange_quantity);
        txtQuantity.setText("Số lượng cho không lớn hơn " + quantityOfVeg + " cây");

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
    /*update post success*/
    private void showDialogUpdateSuccess() {
        final Dialog dialog = new Dialog(UpdatePostActivity.this);
        dialog.setContentView(R.layout.dialog_exchange_quantity_err);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        TextView txtQuantity;
        Button btnClose;
        btnClose = (Button) dialog.findViewById(R.id.btn_close);
        txtQuantity = (TextView) dialog.findViewById(R.id.txt_exchange_quantity);
        txtQuantity.setText("Cập nhật bài viết thành công");

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UpdatePostActivity.this, MainActivity.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
    /*update post success*/
    private void showDialogUpdateFail() {
        final Dialog dialog = new Dialog(UpdatePostActivity.this);
        dialog.setContentView(R.layout.dialog_exchange_quantity_err);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        TextView txtQuantity;
        Button btnClose;
        btnClose = (Button) dialog.findViewById(R.id.btn_close);
        txtQuantity = (TextView) dialog.findViewById(R.id.txt_exchange_quantity);
        txtQuantity.setText("Bạn không thể cập nhật bài viết này");

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UpdatePostActivity.this, MainActivity.class);
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
            case R.id.btn_update_post:
                updatePostShare(mPostData);
                break;
        }
    }

    @Override
    public void updatePostSuccess(ShareDetail shareDetail) {
        showDialogUpdateSuccess();
    }

    @Override
    public void updatePostFail() {
        showDialogUpdateFail();
    }

    @Override
    public void showInfoPersonal(User user) {
        mUser = user;
    }
}
