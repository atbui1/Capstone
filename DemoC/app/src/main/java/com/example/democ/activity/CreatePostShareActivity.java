package com.example.democ.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.democ.R;
import com.example.democ.fragment.GardenBottomSheetFragment;
import com.example.democ.fragment.VegetableBottomSheetFragment;
import com.example.democ.iclick.IClickGarden;
import com.example.democ.iclick.IClickVegetable;
import com.example.democ.model.GardenResult;
import com.example.democ.model.ShareDetail;
import com.example.democ.model.ShareRequest;
import com.example.democ.model.VegetableData;
import com.example.democ.presenters.AllGardenPresenter;
import com.example.democ.presenters.AllVegetableByGardenIdPresenter;
import com.example.democ.presenters.CreateSharePresenter;
import com.example.democ.presenters.PersonalPresenter;
import com.example.democ.room.entities.User;
import com.example.democ.views.AllGardenView;
import com.example.democ.views.AllVegetableByGardenIdView;
import com.example.democ.views.CreateShareView;
import com.example.democ.views.PersonalView;

import java.util.ArrayList;
import java.util.List;

public class CreatePostShareActivity extends AppCompatActivity implements View.OnClickListener, PersonalView, AllGardenView,
        AllVegetableByGardenIdView, CreateShareView {

    private final static String KEY_VEGETABLE_SEND_CREATE_SHARE = "KEY_VEGETABLE_SEND_CREATE_SHARE";
    private LinearLayout mLnlBack;
    private Button mBtnCreatePost;
    private EditText mEdtPostContent, mEdtPostVegetableQuantity;
    private TextView mTxtPostVegetableName, mTxtPostGarden;

    private PersonalPresenter mPersonalPresenter;
    private AllGardenPresenter mAllGardenPresenter;
    private AllVegetableByGardenIdPresenter mAllVegetableByGardenIdPresenter;
    private CreateSharePresenter mCreateSharePresenter;

    private String mStrGardenName = "", mStrVegetableName = "", mStrVegetableId = "", mStrPostContent = "", mStrQuantity = "";
    private int mIntGardenId = 0, mIntQuantityVegetable = 0, mIntQuantityDonate = 0;
    private String mAccessToken = "";
    private List<GardenResult> mListGarden;
    private List<VegetableData> mListVegetable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post_share);

        initialView();
        initialData();
    }

    private void initialView() {
        mLnlBack = (LinearLayout) findViewById(R.id.lnl_back);
        mLnlBack.setOnClickListener(this);
        mBtnCreatePost = (Button) findViewById(R.id.btn_create_post);
        mBtnCreatePost.setOnClickListener((View.OnClickListener) this);
        mEdtPostContent = (EditText) findViewById(R.id.edt_post_content);
        mEdtPostVegetableQuantity = (EditText) findViewById(R.id.edt_post_vegetable_quantity);
        mTxtPostGarden = (TextView) findViewById(R.id.txt_post_garden);
        mTxtPostGarden.setOnClickListener(this);
        mTxtPostVegetableName = (TextView) findViewById(R.id.txt_post_vegetable_name);
        mTxtPostVegetableName.setOnClickListener(this);

        mListGarden = new ArrayList<>();
        mListVegetable = new ArrayList<>();
    }

    private void initialData() {
        mPersonalPresenter = new PersonalPresenter(getApplicationContext(), this);
        mPersonalPresenter.getInfoPersonal();
        mAllGardenPresenter = new AllGardenPresenter(getApplication(), this, this);
        mAllVegetableByGardenIdPresenter = new AllVegetableByGardenIdPresenter(getApplication(), this, this);
        mCreateSharePresenter = new CreateSharePresenter(getApplication(), this, this);


        getDataVegetable();
    }

    /*getting data from vegetable*/
    public void getDataVegetable() {
        Intent intentGetData = getIntent();
        Bundle bundleGetData = intentGetData.getExtras();
        if (bundleGetData != null) {
            VegetableData vegetableData = (VegetableData) bundleGetData.getSerializable(KEY_VEGETABLE_SEND_CREATE_SHARE);
            mStrVegetableId = vegetableData.getId();
            mStrVegetableName = vegetableData.getName();

            mIntQuantityVegetable = vegetableData.getQuantity();
            mIntGardenId = bundleGetData.getInt("GARDEN_ID");
            mStrGardenName = bundleGetData.getString("GARDEN_NAME");

            mEdtPostVegetableQuantity.setText(String.valueOf(mIntQuantityVegetable));
            mTxtPostGarden.setText(mStrGardenName);
            mTxtPostVegetableName.setText(mStrVegetableName);

        }
    }

    /*get all garden*/
    private void clickOpenGardenBottomSheet() {
        mTxtPostVegetableName.setText("");
        GardenBottomSheetFragment gardenBottomSheetFragment = new GardenBottomSheetFragment(mListGarden, new IClickGarden() {
            @Override
            public void clickGarden(GardenResult gardenResult) {
                Toast.makeText(CreatePostShareActivity.this, gardenResult.getName(), Toast.LENGTH_SHORT).show();
                mTxtPostGarden.setText(gardenResult.getName());
                mIntGardenId = gardenResult.getId();


                mListVegetable = new ArrayList<>();
                mAllVegetableByGardenIdPresenter.getAllVegetableByGardenId(mIntGardenId, mAccessToken);

            }
        });
        gardenBottomSheetFragment.show(getSupportFragmentManager(), gardenBottomSheetFragment.getTag());
//        gardenBottomSheetFragment.setCancelable(false);
    }
    /*get all vegetable by id*/
    private void clickOpenVegetableBottomSheet() {
        VegetableBottomSheetFragment vegetableBottomSheetFragment = new VegetableBottomSheetFragment(mListVegetable, new IClickVegetable() {
            @Override
            public void clickVegetable(VegetableData vegetableData) {
                Toast.makeText(CreatePostShareActivity.this, vegetableData.getName(), Toast.LENGTH_SHORT).show();
                mStrVegetableName = vegetableData.getName();
                mTxtPostVegetableName.setText(mStrVegetableName);
                mStrVegetableId = vegetableData.getId();
                mIntQuantityVegetable = vegetableData.getQuantity();
                mEdtPostVegetableQuantity.setText("");

            }
        });
        vegetableBottomSheetFragment.show(getSupportFragmentManager(), vegetableBottomSheetFragment.getTag());
//        vegetableBottomSheetFragment.setCancelable(false);
    }
    /*createPostShare*/
    private void createPostShare() {
        mStrPostContent = mEdtPostContent.getText().toString();
        try {
            mIntQuantityDonate = Integer.parseInt(mEdtPostVegetableQuantity.getText().toString().trim());
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
        if (mStrPostContent.equals("") || mStrVegetableName.equals("") || mStrGardenName.equals("")) {
            //show dialog
            showDialogInputInfo();
            return;
        } else if (mIntQuantityDonate > mIntQuantityVegetable) {
            //show dialog
            showDialogQuantityDonateErr();
            return;
        } else if (mIntQuantityDonate < 1) {
            //show dialog
            showDialogQuantityDonateZero();
            return;
        }

        ShareRequest shareRequest = new ShareRequest(mStrPostContent, mIntQuantityDonate, 1, mStrVegetableId);
        mCreateSharePresenter.createShare(shareRequest, mAccessToken);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lnl_back:
                finish();
                break;
            case R.id.btn_create_post:
                createPostShare();
                break;
            case R.id.txt_post_garden:
                clickOpenGardenBottomSheet();
                break;
            case R.id.txt_post_vegetable_name:
                clickOpenVegetableBottomSheet();
                break;
        }
    }
    /*show dialog input info err*/
    private void showDialogInputInfo() {
        final Dialog dialog = new Dialog(CreatePostShareActivity.this);
        dialog.setContentView(R.layout.dialog_login_fail);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        TextView txtDetailErr;
        Button btnClose;
        txtDetailErr = (TextView) dialog.findViewById(R.id.txt_detail_err);
        btnClose = (Button) dialog.findViewById(R.id.btn_close);
        txtDetailErr.setText("Vui lòng nhập đủ thông tin");
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
    /*show dialog check quantity post > quantity vegetable*/
    private void showDialogQuantityDonateErr() {
        final Dialog dialog = new Dialog(CreatePostShareActivity.this);
        dialog.setContentView(R.layout.dialog_login_fail);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        TextView txtDetailErr;
        Button btnClose;
        txtDetailErr = (TextView) dialog.findViewById(R.id.txt_detail_err);
        btnClose = (Button) dialog.findViewById(R.id.btn_close);
        txtDetailErr.setText("Số lượng cho không được lớn hơn: " + mIntQuantityVegetable );
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
    /*show dialog check quantity post zero*/
    private void showDialogQuantityDonateZero() {
        final Dialog dialog = new Dialog(CreatePostShareActivity.this);
        dialog.setContentView(R.layout.dialog_login_fail);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        TextView txtDetailErr;
        Button btnClose;
        txtDetailErr = (TextView) dialog.findViewById(R.id.txt_detail_err);
        btnClose = (Button) dialog.findViewById(R.id.btn_close);
        txtDetailErr.setText("Số lượng cho phải lớn hơn: 0");
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    @Override
    public void showInfoPersonal(User user) {
        mAccessToken = user.getToken();
        mAllGardenPresenter.getAllGarden(user.getToken());
        mAllVegetableByGardenIdPresenter.getAllVegetableByGardenId(mIntGardenId, mAccessToken);
    }

    @Override
    public void getAllGardenSuccess(List<GardenResult> listAllGarden) {
        mListGarden = listAllGarden;
    }

    @Override
    public void getAllGardenFail() {

    }

    @Override
    public void getAllVegetableByGardenIdSuccess(List<VegetableData> vegetableData) {
        mListVegetable = vegetableData;
    }

    @Override
    public void getAllVegetableByGardenIdFail() {

    }

    @Override
    public void createShareViewSuccess(ShareDetail shareDetail) {
        Intent intent = new Intent(CreatePostShareActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void createShareViewFail() {

    }
}