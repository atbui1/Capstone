package com.example.democ.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.democ.R;
import com.example.democ.model.Vegetable;
import com.example.democ.model.VegetableData;
import com.example.democ.presenters.DeleteVegetablePresenter;
import com.example.democ.presenters.PersonalPresenter;
import com.example.democ.room.entities.User;
import com.example.democ.room.managements.UserManagement;
import com.example.democ.views.DeleteVegetableView;
import com.example.democ.views.PersonalView;
import com.squareup.picasso.Picasso;

public class VegetableActivity extends AppCompatActivity implements View.OnClickListener, DeleteVegetableView, PersonalView {

    private final static String KEY_VEGETABLE = "KEY_VEGETABLE";
    private final static String KEY_VEGETABLE_SEND = "qaz";
    private final static String KEY_VEGETABLE_DELETE = "KEY_VEGETABLE_DELETE";
    private ImageView mImgVegetable;
    private TextView mTxtVegetableName, mTxtVegetableDescription, mTxtVegetableFeature,
            mTxtVegetableUpdate, mTxtVegetableDelete, mTxtVegetableQuantity, mTxtCreatePost;
    private LinearLayout mLnlBack;

    private DeleteVegetablePresenter mDeleteVegetablePresenter;
    private UserManagement mUserManagement;
    private String mVegetableImg, mVegetableName, mVegetableDescription, mVegetableFeature, mGardenName, mGardenAddress, mVegetableId = "";
    private int mNoVegetable, mGardenId, mVegetableQuantity;

    private VegetableData mVegetableData;
    private User mUser;
    private PersonalPresenter mPersonalPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vegetable);
        
        initialView();
        initialData();
    }

    private void initialView() {
        mPersonalPresenter = new PersonalPresenter(getApplicationContext(), this);
        mPersonalPresenter.getInfoPersonal();

        mImgVegetable = (ImageView) findViewById(R.id.img_vegetable);
        mTxtVegetableName = (TextView) findViewById(R.id.txt_vegetable_name);
        mTxtVegetableDescription = (TextView) findViewById(R.id.txt_vegetable_description);
        mTxtVegetableFeature = (TextView) findViewById(R.id.txt_vegetable_feature);
        mTxtVegetableQuantity = (TextView) findViewById(R.id.txt_vegetable_quantity);
        mTxtVegetableDelete = (TextView) findViewById(R.id.txt_vegetable_delete);
        mTxtVegetableDelete.setOnClickListener(this);
        mTxtVegetableUpdate = (TextView) findViewById(R.id.txt_vegetable_update);
        mTxtVegetableUpdate.setOnClickListener(this);
        mTxtCreatePost = (TextView) findViewById(R.id.txt_create_post);
        mTxtCreatePost.setOnClickListener(this);
        mLnlBack = (LinearLayout) findViewById(R.id.lnl_back);
        mLnlBack.setOnClickListener(this);

        mDeleteVegetablePresenter = new DeleteVegetablePresenter(getApplication(), this, this);
        mUserManagement = new UserManagement(getApplication());
    }

    private void initialData() {
        getDataVegetableNew();
    }

    /* getting info from bundle */
    public void getDataVegetable() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            mVegetableName = bundle.getString("VEGETABLE_NAME");
            mVegetableDescription = bundle.getString("VEGETABLE_DESCRIPTION");
            mVegetableFeature = bundle.getString("VEGETABLE_FEATURE");
            mNoVegetable = bundle.getInt("VEGETABLE_STT");
            mVegetableQuantity = bundle.getInt("VEGETABLE_QUANTITY");
            mVegetableImg = bundle.getString("VEGETABLE_IMAGE");
            if (mVegetableImg.equals("")) {
                mVegetableImg = "";
                mImgVegetable.setImageResource(R.mipmap.addimage64);
            } else {
                Picasso.with(this).load(mVegetableImg)
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.caybacha)
                        .into(mImgVegetable);
            }
            mGardenId = bundle.getInt("GARDEN_ID");
            mGardenName = bundle.getString("GARDEN_NAME");
            mGardenAddress = bundle.getString("GARDEN_ADDRESS");
            /* getting info */
            mTxtVegetableName.setText(mVegetableName);
            mTxtVegetableQuantity.setText("Số lượng: " + String.valueOf(mVegetableQuantity));
            mTxtVegetableDescription.setText(mVegetableDescription);
            mTxtVegetableFeature.setText(mVegetableFeature);

        }
    }
    /* getting new info from bundle */
    public void getDataVegetableNew() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
//        VegetableData vegetableData = (VegetableData) bundle.getSerializable("zxc");
        mVegetableData = (VegetableData) bundle.getSerializable(KEY_VEGETABLE);
        VegetableData vegetableData = mVegetableData;

        mVegetableName = vegetableData.getName();
        mVegetableId = vegetableData.getId();
        mVegetableQuantity = vegetableData.getQuantity();
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

        mGardenId = bundle.getInt("GARDEN_ID");
        mGardenName = bundle.getString("GARDEN_NAME");
        mGardenAddress = bundle.getString("GARDEN_ADDRESS");

        /* getting info to edittext */
        mTxtVegetableName.setText(mVegetableName);
        mTxtVegetableQuantity.setText("Số lượng: " + String.valueOf(mVegetableQuantity));
        mTxtVegetableDescription.setText(mVegetableDescription);
        mTxtVegetableFeature.setText(mVegetableFeature);
    }

    private void deleteVegetable() {
//        mUserManagement.getmUserInfo(new UserManagement.OnDataCallBackUser() {
//            @Override
//            public void onDataSuccess(User user) {
//                System.out.println("chay delete vegetable trong vegetable activity");
//                System.out.println("gardenId: " + mGardenId);
//                System.out.println("noVegetable: " + mNoVegetable);
//                System.out.println("vegetable id: " + mVegetableId);
//                mDeleteVegetablePresenter.deleteVegetable(mVegetableId, user.getToken());
//            }
//
//            @Override
//            public void onDataFail() {
//
//            }
//        });
        showDialogDelete();
    }

    public void sendDataToUpdateVegetableActivity() {
//        Intent intent = new Intent(VegetableActivity.this, UpdateVegetableActivity.class);
//        Bundle bundle = new Bundle();
//        bundle.putString("VEGETABLE_NAME", mVegetableName);
//        bundle.putString("VEGETABLE_DESCRIPTION", mVegetableDescription);
//        bundle.putString("VEGETABLE_FEATURE", mVegetableFeature);
//        bundle.putInt("VEGETABLE_STT", mNoVegetable);
//        bundle.putInt("VEGETABLE_QUANTITY", mVegetableQuantity);
//        bundle.putString("VEGETABLE_IMAGE", mVegetableImg);
//        bundle.putInt("GARDEN_ID", mGardenId);
//        intent.putExtras(bundle);
//        startActivity(intent);

        /* getting info bundle */
        Intent intentGetData = getIntent();
        Bundle bundleGetData = intentGetData.getExtras();
        VegetableData vegetableData = (VegetableData) bundleGetData.getSerializable(KEY_VEGETABLE);
        /* getting send info bundle */
        Intent intentSendData = new Intent(VegetableActivity.this, UpdateVegetableActivity.class);
        Bundle bundleSendData = new Bundle();
        bundleSendData.putInt("GARDEN_ID", mGardenId);
        bundleSendData.putInt("GARDEN_NAME", mGardenId);
        bundleSendData.putSerializable(KEY_VEGETABLE_SEND, vegetableData);
        intentSendData.putExtras(bundleSendData);
        startActivity(intentSendData);
    }
    public void sendDataToGardenActivity() {
        Intent intent = new Intent(VegetableActivity.this, GardenActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("GARDEN_NAME", mGardenName);
        bundle.putString("GARDEN_ADDRESS", mGardenAddress);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void clickOpenCreatePost() {
        /* getting info bundle */
        Intent intentGetData = getIntent();
        Bundle bundleGetData = intentGetData.getExtras();
        VegetableData vegetableData = (VegetableData) bundleGetData.getSerializable(KEY_VEGETABLE);
        /* getting send info bundle */
//        Intent intentSendData = new Intent(VegetableActivity.this, UpdateVegetableActivity.class);
        Intent intentSendData = new Intent(VegetableActivity.this, CreatePostActivity.class);
        intentSendData.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        Bundle bundleSendData = new Bundle();
        bundleSendData.putInt("GARDEN_ID", mGardenId);
        bundleSendData.putString("GARDEN_NAME", mGardenName);
        bundleSendData.putSerializable(KEY_VEGETABLE_SEND, vegetableData);
        intentSendData.putExtras(bundleSendData);
        startActivity(intentSendData);
    }

    /* dialog delete vegetable*/
    private void showDialogDelete() {
        final Dialog dialog = new Dialog(VegetableActivity.this);
        dialog.setContentView(R.layout.dialog_delete_garden);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        TextView txtQuantity;
        Button btnClose, btnDelete;
        btnClose = (Button) dialog.findViewById(R.id.btn_delete_no);
        btnDelete = (Button) dialog.findViewById(R.id.btn_delete_yes);
        txtQuantity = (TextView) dialog.findViewById(R.id.txt_content_delete);
        txtQuantity.setText("Bạn có muốn xóa rau: " + mVegetableName + " không?");


        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDeleteVegetablePresenter.deleteVegetable(mVegetableId, mUser.getToken());
                dialog.dismiss();
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_vegetable_delete:
                deleteVegetable();
                break;
            case R.id.txt_vegetable_update:
                Toast.makeText(getApplicationContext(), mVegetableName + mVegetableFeature , Toast.LENGTH_SHORT).show();
                sendDataToUpdateVegetableActivity();
                break;
            case R.id.txt_create_post:
                clickOpenCreatePost();
                break;
            case R.id.lnl_back:
                finish();
                break;
        }
    }

    @Override
    public void DeleteVegetableSuccess() {
        System.out.println("interface Delete Vegetable Success");
        Intent intent = new Intent(VegetableActivity.this, MainActivity.class);
//        Bundle bundle = new Bundle();
//        bundle.putInt("GARDEN_ID", mGardenId);
//        bundle.putString("GARDEN_NAME", mGardenName);
//        bundle.putString("GARDEN_ADDRESS", mGardenAddress);
//        bundle.putBundle(KEY_VEGETABLE_DELETE,bundle);
//        intent.putExtras(bundle);
        startActivity(intent);
//        sendDataToGardenActivity();
    }

    @Override
    public void DeleteVegetableFail() {

    }

    @Override
    public void showInfoPersonal(User user) {
        mUser = user;
    }
}
