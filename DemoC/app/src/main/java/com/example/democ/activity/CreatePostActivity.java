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
import com.example.democ.fragment.VegetableNeedBottomSheetFragment;
import com.example.democ.iclick.IClickGarden;
import com.example.democ.iclick.IClickVegetable;
import com.example.democ.iclick.IClickVegetableNeed;
import com.example.democ.model.GardenResult;
import com.example.democ.model.ShareDetail;
import com.example.democ.model.ShareRequest;
import com.example.democ.model.VegetableData;
import com.example.democ.model.VegetableNeedAll;
import com.example.democ.presenters.AllGardenPresenter;
import com.example.democ.presenters.AllVegetableByGardenIdPresenter;
import com.example.democ.presenters.AllVegetableNeedPresenter;
import com.example.democ.presenters.CreateSharePresenter;
import com.example.democ.presenters.PersonalPresenter;
import com.example.democ.room.entities.User;
import com.example.democ.room.managements.UserManagement;
import com.example.democ.views.AllGardenView;
import com.example.democ.views.AllVegetableByGardenIdView;
import com.example.democ.views.AllVegetableNeedView;
import com.example.democ.views.CreateShareView;
import com.example.democ.views.PersonalView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CreatePostActivity extends AppCompatActivity implements View.OnClickListener, CreateShareView,
        AllGardenView, AllVegetableByGardenIdView, AllVegetableNeedView, PersonalView,
        VegetableNeedBottomSheetFragment.IVegetableNeedListener {


    private final static String KEY_VEGETABLE_SEND_CREATE_SHARE = "KEY_VEGETABLE_SEND_CREATE_SHARE";
    private Button mBtnCreatePost;
    private EditText mEdtPostContent, mEdtPostVegetableQuantity;
    private TextView mTxtPostVegetableName, mTxtPostGarden, mTxtBtnOption, mTxtPostVegetableNeed;
    private LinearLayout mLnlVegetableNeed, mLnlShowBtnOption;

    private CreateSharePresenter mCreateSharePresenter;
    private AllGardenPresenter mAllGardenPresenter;
    private AllVegetableByGardenIdPresenter mAllVegetableByGardenIdPresenter;
    private AllVegetableNeedPresenter mAllVegetableNeedPresenter;
    private User mUser;
    private UserManagement mUserManagement;
    private List<GardenResult> mListGarden;
    private List<VegetableData> mListVegetable;
    private List<VegetableNeedAll> mListVegetableNeed;

    String mStrBtnOption = "Tạo bài chia sẽ";
    String mStrVegetableNeedName = "";
    List<String> mStrVegetableNeedId;
    int mIntStatus = 1;
    private final static String SHARE_POST = "Tạo bài chia sẽ";
    private final static String EXCHANGE_POST = "Tạo bài trao đổi";

    //aa
    private List<VegetableNeedAll> mListAllNeed = new ArrayList<>();
    private List<String> mListVegetableNeedName;
    private List<String> mListVegetableNeedId;
    //aa
    private String mPostContent = "", mPostGardenName = "", mPostVegetableName = "", mPostVegetableId, mStrQuantity = "";
    private int mGardenId, mPostVegetableQuantity, mIntVegetableQuantity;

    private String mVegetableName, mVegetableId;
    private int mNoVegetable, mVegetableQuantity;
    private PersonalPresenter mPersonalPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        initialView();
        initialData();
    }

    private void initialView() {
        mPersonalPresenter = new PersonalPresenter(getApplicationContext(), this);
        mPersonalPresenter.getInfoPersonal();;
        //aaa
        mListAllNeed = new ArrayList<>();
        mListVegetableNeedName = new ArrayList<>();
        mListVegetableNeedId = new ArrayList<>();
        //aaa
        mListGarden = new ArrayList<>();
        mListVegetable = new ArrayList<>();
        mListVegetableNeed = new ArrayList<>();

        mBtnCreatePost = (Button) findViewById(R.id.btn_create_post);
        mBtnCreatePost.setOnClickListener((View.OnClickListener) this);
        mEdtPostContent = (EditText) findViewById(R.id.edt_post_content);
        mEdtPostVegetableQuantity = (EditText) findViewById(R.id.edt_post_vegetable_quantity);
        mTxtPostVegetableName = (TextView) findViewById(R.id.txt_post_vegetable_name);
        mTxtPostVegetableName.setOnClickListener(this);
        mTxtPostGarden = (TextView) findViewById(R.id.txt_post_garden);
        mTxtPostGarden.setOnClickListener(this);
        // new
        mTxtBtnOption = (TextView) findViewById(R.id.txt_btn_option) ;
        mLnlShowBtnOption = (LinearLayout) findViewById(R.id.lnl_show_btn_option);
        mLnlShowBtnOption.setOnClickListener(this);
        mLnlVegetableNeed = (LinearLayout) findViewById(R.id.lnl_vegetable_need);
        mTxtPostVegetableNeed = (TextView) findViewById(R.id.txt_post_vegetable_need);
        mTxtPostVegetableNeed.setOnClickListener(this);

        mAllGardenPresenter = new AllGardenPresenter(getApplication(), this, this);
        mAllVegetableByGardenIdPresenter = new AllVegetableByGardenIdPresenter(getApplication(), this, this);
        mAllVegetableNeedPresenter = new AllVegetableNeedPresenter(getApplication(), this, this);

        mCreateSharePresenter = new CreateSharePresenter(getApplication(), this, this);
//        mUserManagement = new UserManagement(getApplication());
//        mUserManagement.getmUserInfo(new UserManagement.OnDataCallBackUser() {
//            @Override
//            public void onDataSuccess(User user) {
//                mUser = user;
//                mAllGardenPresenter.getAllGarden(mUser.getToken());
//                mAllVegetableNeedPresenter.getAllVegetableNeed(mUser.getToken());
//            }
//
//            @Override
//            public void onDataFail() {
//
//            }
//        });

        mTxtBtnOption.setText(mStrBtnOption);
        mTxtPostVegetableNeed.setText(mStrVegetableNeedName);

    }

    private void initialData() {
//
//        mPostContent = mEdtPostContent.getText().toString();
//         mPostVegetableQuantity = 0;
//         try {
////             mPostVegetableQuantity = Integer.parseInt(mEdtPostVegetableQuantity.getText().toString());
//             mPostVegetableQuantity = Integer.parseInt(mStrQuantity);
//         } catch (NumberFormatException ex) {
//             Toast.makeText(getApplicationContext(), "khong the covert quantity", Toast.LENGTH_SHORT).show();
//         }
//
//        mCreateSharePresenter = new CreateSharePresenter(getApplication(), this, this);

        getDataVegetableNew();
    }

    public void getDataVegetableNew() {
        Intent intentGetData = getIntent();
        Bundle bundleGetData = intentGetData.getExtras();
        if (bundleGetData != null) {
            VegetableData vegetableData = (VegetableData) bundleGetData.getSerializable(KEY_VEGETABLE_SEND_CREATE_SHARE);
            mVegetableId = vegetableData.getId();
            mVegetableName = vegetableData.getName();

            mVegetableQuantity = vegetableData.getQuantity();
            mGardenId = bundleGetData.getInt("GARDEN_ID");
            mPostGardenName = bundleGetData.getString("GARDEN_NAME");


            mIntVegetableQuantity = mVegetableQuantity;
            mPostVegetableQuantity = mVegetableQuantity;
            mPostVegetableId = mVegetableId;
            mPostVegetableName = mVegetableName;
            mEdtPostVegetableQuantity.setText(String.valueOf(mVegetableQuantity));
            mTxtPostGarden.setText(mPostGardenName);
            mTxtPostVegetableName.setText(mVegetableName);

//            mListVegetable = new ArrayList<>();
//            mAllVegetableByGardenIdPresenter.getAllVegetableByGardenId(mGardenId, mUser.getToken());

            System.out.println("TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT");
            System.out.println("TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT");
            System.out.println("ten vuon rau: " + mPostGardenName);
            System.out.println("id vuon rau: " + mGardenId);
            System.out.println("TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT");
            System.out.println("TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT");
        }
    }
    public void createPost() {
        mPostContent = mEdtPostContent.getText().toString();
//        int status = 0;
        mPostVegetableQuantity = 0;
        mStrQuantity = mEdtPostVegetableQuantity.getText().toString();
        try {
            mPostVegetableQuantity = Integer.parseInt(mStrQuantity);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
        System.out.println("KKKKKKKKKKKKKKKKKKKkk");
        System.out.println("content: " + mPostContent);
        System.out.println("quantity: " + mStrQuantity);
        System.out.println("vegename: " + mPostVegetableName);
        System.out.println("so luong: " + mPostVegetableQuantity);
        System.out.println("KKKKKKKKKKKKKKKKKKKkk");
        if (mPostContent == "" || mStrQuantity == "" || mPostVegetableName == "") {

            showDialogInputInfo();
            return;
        } else if (mPostVegetableQuantity > mIntVegetableQuantity) {
            showDialogQuantityErr();
            return;
        }
//        final ShareRequest shareRequest = new ShareRequest(mPostContent, mPostVegetableQuantity, status, mPostVegetableId);
//        ShareRequest shareRequest = new ShareRequest(mPostContent, mPostVegetableQuantity, mIntStatus, mPostVegetableId, mStrVegetableNeedId, mStrVegetableNeedName);
        System.out.println("content: " + mPostContent);
        System.out.println("quantity: " + mPostVegetableQuantity);
        System.out.println("status: " + mIntStatus);
        System.out.println("vegetableId: " + mPostVegetableId);
        System.out.println("vegetable need id: " + mStrVegetableNeedId);
        System.out.println("vegetable need name: " + mStrVegetableNeedName);
        if (mStrBtnOption == SHARE_POST) {

            System.out.println("PPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPP");
            System.out.println(SHARE_POST + SHARE_POST+SHARE_POST + SHARE_POST);
            mStrVegetableNeedId = null;
            mStrVegetableNeedName = "";
            System.out.println("vegetable need id: " + mStrVegetableNeedId);
            System.out.println("vegetable need name: " + mStrVegetableNeedName);
            ShareRequest shareRequest = new ShareRequest(mPostContent, mPostVegetableQuantity, mIntStatus,
                    mPostVegetableId, mStrVegetableNeedId);
            System.out.println("PPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPP");
            /** tao bai share */
            mCreateSharePresenter.createShare(shareRequest, mUser.getToken());
        } else if (mStrBtnOption == EXCHANGE_POST) {

            System.out.println("PPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPP");
            System.out.println("mStrVegetableNeedName: " + mStrVegetableNeedName);
            System.out.println(EXCHANGE_POST + EXCHANGE_POST + EXCHANGE_POST + EXCHANGE_POST);
            ShareRequest shareRequest = new ShareRequest(mPostContent, mPostVegetableQuantity, mIntStatus,
                    mPostVegetableId, mListVegetableNeedId);
            System.out.println("mListVegetableNeedId size: " + mListVegetableNeedId.size());
            System.out.println("in ra : " + mListVegetableNeedId);
            System.out.println("PPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPP");
            /** tao bai exchange */
            mCreateSharePresenter.createShare(shareRequest, mUser.getToken());
        }

//        mCreateSharePresenter.createShare(shareRequest, mUser.getToken());
    }

    //show dialog input info
    private void showDialogInputInfo() {
        final Dialog dialog = new Dialog(CreatePostActivity.this);
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

    //show dialog check quantity post > quantity vegetable
    private void showDialogQuantityErr() {
        final Dialog dialog = new Dialog(CreatePostActivity.this);
        dialog.setContentView(R.layout.dialog_login_fail);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        TextView txtDetailErr;
        Button btnClose;
        txtDetailErr = (TextView) dialog.findViewById(R.id.txt_detail_err);
        btnClose = (Button) dialog.findViewById(R.id.btn_close);
        txtDetailErr.setText("Số lượng không lớn hơn: " + mIntVegetableQuantity);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
    //select option post
    private void showOptionBtn() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_show_btn_option_create_post);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        TextView txtOptionShare, txtOptionExchange;
        txtOptionShare = (TextView) dialog.findViewById(R.id.txt_option_share);
        txtOptionExchange = (TextView) dialog.findViewById(R.id.txt_option_exchange);

        txtOptionShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mStrBtnOption = SHARE_POST;
                mTxtBtnOption.setText(mStrBtnOption);
                mLnlVegetableNeed.setVisibility(View.GONE);
                mTxtPostVegetableNeed.setText("");
                mIntStatus = 1;
                dialog.dismiss();
            }
        });

        txtOptionExchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mStrBtnOption = EXCHANGE_POST;
                mIntStatus = 2;
                mTxtBtnOption.setText(mStrBtnOption);
                if (mStrBtnOption.equals("Tạo bài trao đổi") && mLnlVegetableNeed.getVisibility() == View.GONE) {
                    mLnlVegetableNeed.setVisibility(View.VISIBLE);
                }
                dialog.dismiss();
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_create_post:
                createPost();
                break;
            case R.id.txt_post_garden:
                clickOpenGardenBottomSheet();
                break;
            case R.id.txt_post_vegetable_name:
                System.out.println("+++++++++++++++");
                System.out.println(mPostGardenName);
                System.out.println("++++++++++++++");
                if (mTxtPostGarden.getText().toString().equals("")) {
                    Toast.makeText(this, "Vui lòng chọn vườn rau trước", Toast.LENGTH_SHORT).show();
                } else {
                    clickOpenVegetableBottomSheet();
                }
                break;
            case R.id.lnl_show_btn_option:
                showOptionBtn();
                break;
            case R.id.txt_post_vegetable_need:
                clickOpenVegetableNeed();
                break;
        }

    }

//    get all garden
    private void clickOpenGardenBottomSheet() {
        mTxtPostVegetableName.setText("");
        GardenBottomSheetFragment gardenBottomSheetFragment = new GardenBottomSheetFragment(mListGarden, new IClickGarden() {
            @Override
            public void clickGarden(GardenResult gardenResult) {

                mTxtPostGarden.setText(gardenResult.getName());
                mGardenId = gardenResult.getId();
                System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
                System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
                System.out.println(mGardenId);
                System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
                System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");

                mListVegetable = new ArrayList<>();
                mAllVegetableByGardenIdPresenter.getAllVegetableByGardenId(mGardenId, mUser.getToken());

            }
        });
        gardenBottomSheetFragment.show(getSupportFragmentManager(), gardenBottomSheetFragment.getTag());
//        gardenBottomSheetFragment.setCancelable(false);
    }
//    get all vegetable by id
    private void clickOpenVegetableBottomSheet() {
        VegetableBottomSheetFragment vegetableBottomSheetFragment = new VegetableBottomSheetFragment(mListVegetable, new IClickVegetable() {
            @Override
            public void clickVegetable(VegetableData vegetableData) {

                mPostVegetableName = vegetableData.getName();
                mTxtPostVegetableName.setText(mPostVegetableName);
                mPostVegetableId = vegetableData.getId();
                mIntVegetableQuantity = vegetableData.getQuantity();
                System.out.println("CCCCCCCCCCCCCCCCCCCCCCCCCCCC");
                System.out.println("CCCCCCCCCCCCCCCCCCCCCCCCCCCC");
                System.out.println("mPostVegetableId: " + mPostVegetableId);
                System.out.println("mIntVegetableQuantity: " + mIntVegetableQuantity);
                System.out.println("CCCCCCCCCCCCCCCCCCCCCCCCCCCC");
                System.out.println("CCCCCCCCCCCCCCCCCCCCCCCCCCCC");
            }
        });
        vegetableBottomSheetFragment.show(getSupportFragmentManager(), vegetableBottomSheetFragment.getTag());
//        vegetableBottomSheetFragment.setCancelable(false);
    }

    private void clickOpenVegetableNeed() {
        VegetableNeedBottomSheetFragment vegetableNeedBottomSheetFragment = new VegetableNeedBottomSheetFragment(mListVegetableNeed);
        vegetableNeedBottomSheetFragment.show(getSupportFragmentManager(), vegetableNeedBottomSheetFragment.getTag());

//        vegetableNeedBottomSheetFragment.setCancelable(false);
    }

    @Override
    public void createShareViewSuccess(ShareDetail shareDetail) {
        Intent intent = new Intent(CreatePostActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void createShareViewFail() {

    }

    @Override
    public void getAllGardenSuccess(List<GardenResult> listAllGarden) {
        this.mListGarden = listAllGarden;
    }

    @Override
    public void getAllGardenFail() {

    }

    @Override
    public void getAllVegetableByGardenIdSuccess(List<VegetableData> vegetableData) {

        mListVegetable = new ArrayList<>();
        this.mListVegetable = vegetableData;
        if (mListVegetable == null || mListVegetable.size() == 0) {

        }
    }

    @Override
    public void getAllVegetableByGardenIdFail() {
        Toast.makeText(getApplicationContext(), "Vườn rau chưa có rau", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void allVegetableNeedSuccess(List<VegetableNeedAll> vegetableNeedAlls) {
        this.mListVegetableNeed = vegetableNeedAlls;
    }

    @Override
    public void getVegetableNeed(List<VegetableNeedAll> vegetableNeedAll) {
        mListAllNeed = vegetableNeedAll;
        mListVegetableNeedName = new ArrayList<>();
        mListVegetableNeedId = new ArrayList<>();
        if (mListAllNeed != null) {
            System.out.println("TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT");
            System.out.println("ttt size mListAllNeed: " + mListAllNeed.size());
            String vegetableNeedName, vegetableNeedId;
            List<String> mListTmp = new ArrayList<>();
            for (int i = 0; i < mListAllNeed.size(); i++) {
                vegetableNeedName = mListAllNeed.get(i).getText();
                vegetableNeedId = mListAllNeed.get(i).getId();
                mListVegetableNeedName.add(vegetableNeedName);
                mListVegetableNeedId.add(vegetableNeedId);
                mListTmp.add(vegetableNeedName);
            }
            System.out.println("tttttttttttttttttttttttttttttt");
            System.out.println("ttt size name: " + mListVegetableNeedName.size());
            System.out.println("tt size id: " + mListVegetableNeedId.size());

            mTxtPostVegetableNeed.setText(String.valueOf(mListTmp));
//            mListVegetableNeedName.clear();
            mListTmp.clear();
            System.out.println("tttttttttttttttttttttttttttttt");
            System.out.println("TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT");
        } else {
            System.out.println("YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY");
            System.out.println("mListAllNeed null");
            System.out.println("YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY");
        }

    }

    @Override
    public void showInfoPersonal(User user) {
        mUser = user;
        mAllGardenPresenter.getAllGarden(mUser.getToken());
        mAllVegetableNeedPresenter.getAllVegetableNeed(mUser.getToken());
        if (String.valueOf(mGardenId).equals("")) {
            System.out.println("AAAAAAAAAAAAAAAAAaa");
            System.out.println("khong co id garden");
            System.out.println("AAAAAAAAAAAAAAAAAaa");
        } else {
            System.out.println("vao else showInfoPersonal");
            mAllVegetableByGardenIdPresenter.getAllVegetableByGardenId(mGardenId, mUser.getToken());
        }
    }
}
