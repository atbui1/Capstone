package com.example.democ.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.democ.R;
import com.example.democ.fragment.AccountEditPostBottomSheetFragment;
import com.example.democ.fragment.ReportPostBottomSheetFragment;
import com.example.democ.model.ExchangeData;
import com.example.democ.model.ExchangeRequest;
import com.example.democ.model.GardenResult;
import com.example.democ.model.PostData;
import com.example.democ.model.PostSearchDescription;
import com.example.democ.model.PostSearchKeyword;
import com.example.democ.model.PostSearchName;
import com.example.democ.model.VegetableData;
import com.example.democ.model.VegetableShare;
import com.example.democ.presenters.CheckVegetableOfAccountPresenter;
import com.example.democ.presenters.CreateExchangePresenter;
import com.example.democ.presenters.PersonalPresenter;
import com.example.democ.room.entities.User;
import com.example.democ.views.CheckVegetableOfAccountView;
import com.example.democ.views.CreateExchangeView;
import com.example.democ.views.PersonalView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PostDetailActivity extends AppCompatActivity implements PersonalView, View.OnClickListener, CreateExchangeView,
        CheckVegetableOfAccountView {

    private final static String KEY_BUNDLE_HOME = "KEY_BUNDLE_HOME";
    private final static String KEY_BUNDLE_DESCRIPTION = "KEY_BUNDLE_DESCRIPTION";
    private final static String KEY_BUNDLE_KEYWORD = "KEY_BUNDLE_KEYWORD";
    private final static String KEY_BUNDLE_NAME = "KEY_BUNDLE_NAME";
    private static String POST_SHARE = "Nhận rau";
    private static String POST_EXCHANGE = "Đổi rau";

    private TextView mTxtPostUser, mTxtPostTime, mTxtPostContent, mTxtPostQuantity;
    private ImageView mImgPostImage;
    private LinearLayout mLnlBtnExchange, mLnlLeftMenu;
    private Button mBtnExchange;

    private PersonalPresenter mPersonalPresenter;

    private String mStrPostUser = "", mStrPostTime = "", mStrPostContent = "", mStrPostImage = "",
            mStrAccountIdOfPost = "", mStrAccountId = "", mShareIdOfShare = "", mAccessTokenAAA = "",
            mStrVegetableNeedId = "", mStrVegetableNeedName = "";
    private int mIntPostQuantity = 0;
    private int mIntCheck = 0, mIntStatus = 0, mIntExchangeQuantityDonate = 0, mIntExchangeQuantityReceive = 0,
            mIntQuantityOfShare = 0, mIntSizeListVegetableShare = 0, mIntQuantityOfAccount = 0;
    private User mUser;
    private PostData mPostData;
    private PostSearchDescription mPostSearchDescription;
    private PostSearchName mPostSearchName;
    private PostSearchKeyword mPostSearchKeyword;

    private CreateExchangePresenter mCreateExchangePresenter;
    private CheckVegetableOfAccountPresenter mCheckVegetableOfAccountPresenter;
    private Spinner mSpVegetableNedd;
    private List<VegetableShare> mListVegetableNeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        initialView();
        initialData();
    }

    private void initialView() {
        mPersonalPresenter = new PersonalPresenter(getApplicationContext(), this);
        mPersonalPresenter.getInfoPersonal();
        mCreateExchangePresenter = new CreateExchangePresenter(getApplication(), getApplicationContext(), this);
        mCheckVegetableOfAccountPresenter = new CheckVegetableOfAccountPresenter(getApplication(), getApplicationContext(), this);

        mListVegetableNeed = new ArrayList<>();

        getDataHome();
        getDataSearchDescription();
        getDataSearchKeyword();
        getDataSearchName();

        mTxtPostUser = (TextView) findViewById(R.id.txt_post_username);
        mTxtPostTime = (TextView) findViewById(R.id.txt_post_time);
        mTxtPostContent = (TextView) findViewById(R.id.txt_post_content);
        mTxtPostQuantity = (TextView) findViewById(R.id.txt_post_vegetable_quantity);
        mImgPostImage = (ImageView) findViewById(R.id.img_post_content);
        mLnlBtnExchange = (LinearLayout) findViewById(R.id.lnl_btn_exchange);
        mBtnExchange = (Button) findViewById(R.id.btn_exchange);
        mBtnExchange.setOnClickListener(this);
        mLnlLeftMenu = (LinearLayout) findViewById(R.id.lnl_left_menu);
        mLnlLeftMenu.setOnClickListener(this);

        mTxtPostUser.setText(mStrPostUser);
        mTxtPostTime.setText(mStrPostTime);
        mTxtPostContent.setText(mStrPostContent);
        mTxtPostQuantity.setText("Số lượng: " + String.valueOf(mIntPostQuantity));
        if (mStrPostImage.equals("")) {
            mImgPostImage.setImageResource(R.mipmap.addimage64);
        } else {
            System.out.println("pppppppppppppppppppmStrPostImagepppppppppppppppppppppp");
            System.out.println(mStrPostImage);
            System.out.println("pppppppppppppppppppppmStrPostImagepppppppppppppppppppp");
            Picasso.with(this).load(mStrPostImage)
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.caybacha)
                    .into(mImgPostImage);
        }

        if (mIntStatus == 1) {
            mBtnExchange.setText(POST_SHARE);
        } else if (mIntStatus == 2) {
            mBtnExchange.setText(POST_EXCHANGE);
        }

    }
    private void initialData() {

        System.out.println("JJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJ");
        System.out.println("jjjjjjjjjjjj: " + mPostData);
        System.out.println("JJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJ");
    }

    /*get data from bundle with 4 studio*/
    public void getDataHome() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            mPostData = (PostData) bundle.getSerializable(KEY_BUNDLE_HOME);
            PostData postData = mPostData;
            if (postData != null) {
                mStrPostUser = postData.getFullName();
                mStrPostTime = postData.getCreatedDate();
                mStrPostContent = postData.getContent();
                mIntPostQuantity = postData.getQuantity();
                mStrAccountIdOfPost = postData.getAccountId();
                mIntCheck = 1;
                mIntStatus = postData.getStatius();
                mIntQuantityOfShare = postData.getQuantity();
                mShareIdOfShare = postData.getId();
                mIntSizeListVegetableShare = postData.getVegetableShareList().size();
                mListVegetableNeed = postData.getVegetableShareList();

                int maxSize = postData.getImageVegetablesList().size() - 1;
                if (postData.getImageVegetablesList() == null || postData.getImageVegetablesList().size() == 0) {
                    mStrPostImage = "";
                } else {
                    mStrPostImage = postData.getImageVegetablesList().get(maxSize).getUrl().trim();
                }
            }
        }
    }
    public void getDataSearchDescription() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            mPostSearchDescription = (PostSearchDescription) bundle.getSerializable(KEY_BUNDLE_DESCRIPTION);
            PostSearchDescription postData = mPostSearchDescription;
            if (postData != null) {
                mStrPostUser = postData.getFullName();
                mStrPostTime = postData.getCreatedDate();
                mStrPostContent = postData.getContent();
                mIntPostQuantity = postData.getQuantity();
                mStrAccountIdOfPost = postData.getAccountId();
                mIntCheck = 1;
                mIntStatus = postData.getStatius();
                mIntQuantityOfShare = postData.getQuantity();
                mShareIdOfShare = postData.getId();
                mIntSizeListVegetableShare = postData.getVegetableShareList().size();
                mListVegetableNeed = postData.getVegetableShareList();

                int maxSize = postData.getImageVegetablesList().size() - 1;
                if (postData.getImageVegetablesList() == null || postData.getImageVegetablesList().size() == 0) {
                    mStrPostImage = "";
                } else {
                    mStrPostImage = postData.getImageVegetablesList().get(maxSize).getUrl().trim();
                }
            }
        }
    }
    public void getDataSearchKeyword() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            mPostSearchKeyword = (PostSearchKeyword) bundle.getSerializable(KEY_BUNDLE_KEYWORD);
            PostSearchKeyword postData = mPostSearchKeyword;
            if (postData != null) {
                mStrPostUser = postData.getFullName();
                mStrPostTime = postData.getCreatedDate();
                mStrPostContent = postData.getContent();
                mIntPostQuantity = postData.getQuantity();
                mStrAccountIdOfPost = postData.getAccountId();
                mIntCheck = 1;
                mIntStatus = postData.getStatius();
                mIntQuantityOfShare = postData.getQuantity();
                mShareIdOfShare = postData.getId();
                mIntSizeListVegetableShare = postData.getVegetableShareList().size();
                mListVegetableNeed = postData.getVegetableShareList();

                int maxSize = postData.getImageVegetablesList().size() - 1;
                if (postData.getImageVegetablesList() == null || postData.getImageVegetablesList().size() == 0) {
                    mStrPostImage = "";
                } else {
                    mStrPostImage = postData.getImageVegetablesList().get(maxSize).getUrl().trim();
                }
            }
        }
    }
    public void getDataSearchName() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            mPostSearchName = (PostSearchName) bundle.getSerializable(KEY_BUNDLE_NAME);
            PostSearchName postData = mPostSearchName;
            if (postData != null) {
                mStrPostUser = postData.getFullName();
                mStrPostTime = postData.getCreatedDate();
                mStrPostContent = postData.getContent();
                mIntPostQuantity = postData.getQuantity();
                mStrAccountIdOfPost = postData.getAccountId();
                mIntCheck = 1;
                mIntStatus = postData.getStatius();
                mIntQuantityOfShare = postData.getQuantity();
                mShareIdOfShare = postData.getId();
                mIntSizeListVegetableShare = postData.getVegetableShareList().size();
                mListVegetableNeed = postData.getVegetableShareList();

                int maxSize = postData.getImageVegetablesList().size() - 1;
                if (postData.getImageVegetablesList() == null || postData.getImageVegetablesList().size() == 0) {
                    mStrPostImage = "";
                } else {
                    mStrPostImage = postData.getImageVegetablesList().get(maxSize).getUrl().trim();
                }
            }
        }
    }
    /*end get data from bundle with 4 studio*/

    /*open report dialog bottom sheet with 4 studio*/
    public void clickOpenReportBottomSheet(PostData postData) {
        String accessToken = mUser.getToken();
        String accountId = mUser.getAccountId();
        ReportPostBottomSheetFragment reportPostBottomSheetFragment = new ReportPostBottomSheetFragment(postData, accessToken, accountId);
        reportPostBottomSheetFragment.show(getSupportFragmentManager(), reportPostBottomSheetFragment.getTag());
    }
    public void clickOpenReportBottomSheetSearchDescription(PostSearchDescription postData) {
        String accessToken = mUser.getToken();
        String accountId = mUser.getAccountId();
        ReportPostBottomSheetFragment reportPostBottomSheetFragment = new ReportPostBottomSheetFragment(postData, accessToken, accountId);
        reportPostBottomSheetFragment.show(getSupportFragmentManager(), reportPostBottomSheetFragment.getTag());
    }
    public void clickOpenReportBottomSheetSearchName(PostSearchName postData) {
        String accessToken = mUser.getToken();
        String accountId = mUser.getAccountId();
        ReportPostBottomSheetFragment reportPostBottomSheetFragment = new ReportPostBottomSheetFragment(postData, accessToken, accountId);
        reportPostBottomSheetFragment.show(getSupportFragmentManager(), reportPostBottomSheetFragment.getTag());
    }
    public void clickOpenReportBottomSheetSearchKeyword(PostSearchKeyword postData) {
        String accessToken = mUser.getToken();
        String accountId = mUser.getAccountId();
        ReportPostBottomSheetFragment reportPostBottomSheetFragment = new ReportPostBottomSheetFragment(postData, accessToken, accountId);
        reportPostBottomSheetFragment.show(getSupportFragmentManager(), reportPostBottomSheetFragment.getTag());
    }
    /*end report dialog bottom sheet with 4 studio*/

    /*open edit post dialog bottom sheet 3 option*/
    private void clickOpenEditBottomSheetSearchName(PostSearchName postData) {
        String token = mUser.getToken();
        AccountEditPostBottomSheetFragment accountEditPostBottomSheetFragment = new AccountEditPostBottomSheetFragment(postData, token);
        accountEditPostBottomSheetFragment.show(getSupportFragmentManager(), accountEditPostBottomSheetFragment.getTag());
    }
    private void clickOpenEditBottomSheetSearchKeyword(PostSearchKeyword postData) {
        String token = mUser.getToken();
        AccountEditPostBottomSheetFragment accountEditPostBottomSheetFragment = new AccountEditPostBottomSheetFragment(postData, token);
        accountEditPostBottomSheetFragment.show(getSupportFragmentManager(), accountEditPostBottomSheetFragment.getTag());
    }
    private void clickOpenEditBottomSheetSearchDescription(PostSearchDescription postData) {
        String token = mUser.getToken();
        AccountEditPostBottomSheetFragment accountEditPostBottomSheetFragment = new AccountEditPostBottomSheetFragment(postData, token);
        accountEditPostBottomSheetFragment.show(getSupportFragmentManager(), accountEditPostBottomSheetFragment.getTag());
    }
    /*end edit post dialog bottom sheet 3 option*/
    /*check open report*/
    private void checkOpenReport() {
        if (mPostData != null) {
            clickOpenReportBottomSheet(mPostData);
        } else if (mPostSearchName != null) {
            clickOpenReportBottomSheetSearchName(mPostSearchName);
        } else if (mPostSearchKeyword != null) {
            clickOpenReportBottomSheetSearchKeyword(mPostSearchKeyword);
        } else if (mPostSearchDescription != null) {
            clickOpenReportBottomSheetSearchDescription(mPostSearchDescription);
        }
    }
    /*check open edit*/
    private void checkOpenEdit() {
        if (mPostSearchName != null) {
            clickOpenEditBottomSheetSearchName(mPostSearchName);
        } else if (mPostSearchKeyword != null) {
            clickOpenEditBottomSheetSearchKeyword(mPostSearchKeyword);
        } else if (mPostSearchDescription != null) {
            clickOpenEditBottomSheetSearchDescription(mPostSearchDescription);
        }
    }

    private void clickBtnExchange() {
        if (mIntStatus == 1) {
            openShowShare();
        } else if (mIntStatus == 2) {
            if (mIntSizeListVegetableShare == 0) {
                System.out.println("show dia log trao doi rau bat ki");
            } else {
                System.out.println("show dilog nhan rau co dinh");
                openShowExchange();
            }
        }
    }
    /* show dialog share*/
    private void openShowShare() {
        final Dialog dialog = new Dialog(PostDetailActivity.this);
        dialog.setContentView(R.layout.dialog_exchange_quantity);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        final EditText edtQuantity;
        Button btnOk, btnClose;
        edtQuantity = (EditText) dialog.findViewById(R.id.edt_exchange_quantity);
        btnOk = (Button) dialog.findViewById(R.id.btn_ok);
        btnClose = (Button) dialog.findViewById(R.id.btn_close);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (edtQuantity.getText().toString().equals("")) {
                        mIntExchangeQuantityDonate = 0;
                    } else {
                        mIntExchangeQuantityDonate = Integer.parseInt(edtQuantity.getText().toString());
                    }

                    if (mIntExchangeQuantityDonate > mIntQuantityOfShare || mIntExchangeQuantityDonate < 1) {
                        showDialogQuantityErr();
                        System.out.println("chay vao if showDialogQuantityErr");
                    } else {
                        // goi api exchange
                        ExchangeRequest exchangeRequest = new ExchangeRequest(mIntExchangeQuantityDonate, 0, mShareIdOfShare, "");
                        mCreateExchangePresenter.createExchange(exchangeRequest, mUser.getToken());
                    }
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                }
                dialog.dismiss();
            }
        });

        dialog.show();
    }
    /* show dialog exchange */
    private void openShowExchange() {
        final Dialog dialog = new Dialog(PostDetailActivity.this);
        dialog.setContentView(R.layout.dialog_exchange_2_quantity);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        final EditText edtQuantityDonate, edtQuantityReceive;
        Button btnOk, btnClose;
        edtQuantityReceive = (EditText) dialog.findViewById(R.id.edt_quantity_receive);
        edtQuantityDonate = (EditText) dialog.findViewById(R.id.edt_quantity_donate);
        btnOk = (Button) dialog.findViewById(R.id.btn_ok);
        btnClose = (Button) dialog.findViewById(R.id.btn_close);

        /* spinner*/
        ArrayAdapter<VegetableShare> adapterVegetableNeed = new ArrayAdapter<VegetableShare>(getApplicationContext(),
                android.R.layout.simple_spinner_item, mListVegetableNeed);
        adapterVegetableNeed.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpVegetableNedd = dialog.findViewById(R.id.sp_vegetable_need);
        mSpVegetableNedd.setAdapter(adapterVegetableNeed);
        mSpVegetableNedd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                VegetableShare vegetableShare = (VegetableShare) adapterView.getSelectedItem();
                displaySpinnerVegetableNeed(vegetableShare);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mIntExchangeQuantityReceive = Integer.parseInt(edtQuantityReceive.getText().toString());
                    mIntExchangeQuantityDonate = Integer.parseInt(edtQuantityDonate.getText().toString());

                    if (mIntExchangeQuantityReceive > mIntQuantityOfShare) {
                        showDialogQuantityErr();
                    }

                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                }

                mCheckVegetableOfAccountPresenter.CheckVegetableOfAccountPresenter(mStrVegetableNeedId, mStrVegetableNeedName, mUser.getToken());
                dialog.dismiss();
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
    /* show dialog exchange any vegetable */

//    private void showDialogAnyVegetable() {
//        final Dialog dialog = new Dialog(PostDetailActivity.this);
//        dialog.setContentView(R.layout.dialog_exchange_any_vegetable);
//        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
//        final EditText edtQuantityDonate, edtQuantityReceive;
//        Button btnOk, btnClose;
//        edtQuantityDonate = dialog.findViewById(R.id.edt_quantity_donate);
//        edtQuantityReceive = dialog.findViewById(R.id.edt_quantity_receive);
//
//        btnClose = dialog.findViewById(R.id.btn_close);
//        btnOk = dialog.findViewById(R.id.btn_ok);
//        /*adapterGarden*/
//        ArrayAdapter<GardenResult> adapterGarden = new ArrayAdapter<GardenResult>(getApplicationContext(),
//                android.R.layout.simple_spinner_item, mListGarden);
//        adapterGarden.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        /*adapterVegetable*/
//        ArrayAdapter<VegetableData> adapterVegetable = new ArrayAdapter<VegetableData>(getContext(),
//                android.R.layout.simple_spinner_item, mListVegetable);
//        adapterGarden.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        mSpGarden = dialog.findViewById(R.id.sp_garden);
//        mSpVegetable = dialog.findViewById(R.id.sp_vegetable);
//        mSpGarden.setAdapter(adapterGarden);
//        mSpVegetable.setAdapter(adapterVegetable);
//        mSpGarden.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                GardenResult gardenResult = (GardenResult) adapterView.getSelectedItem();
//                displaySpinnerGarden(gardenResult);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
//        mSpVegetable.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                VegetableData vegetableData = (VegetableData) adapterView.getSelectedItem();
//                displaySpinnerVegetable(vegetableData);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
//        btnClose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//            }
//        });
//        btnOk.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int quantityDonate = 0, quantityReceive = 0;
//                try {
//                    if (edtQuantityDonate.getText().equals("")) {
//                        quantityDonate = 0;
//                    } else {
//                        quantityDonate = Integer.parseInt(edtQuantityDonate.getText().toString().trim());
//                    }
//                    if (edtQuantityReceive.getText().equals("")) {
//                        quantityReceive = 0;
//                    } else {
//                        quantityReceive = Integer.parseInt(edtQuantityReceive.getText().toString().trim());
//                    }
//                } catch (NumberFormatException ex) {
//                    ex.printStackTrace();
//                }
//                mIntExchangeQuantityDonate = quantityDonate;
//                mIntExchangeQuantityReceive = quantityReceive;
//                createExchangeAnyVegetable();
//                dialog.dismiss();
//            }
//        });
//
//        dialog.show();
//    }
    /* display vegetable need*/
    public void displaySpinnerVegetableNeed(VegetableShare vegetableShare) {
        String needName = vegetableShare.getVegetableShareName();
        String needId = vegetableShare.getVegetableShareId();
        mStrVegetableNeedId = vegetableShare.getVegetableShareId();
        mStrVegetableNeedName = vegetableShare.getVegetableShareName();
//        createExchange();
    }
    /*dialog quantity receive */
    private void showDialogQuantityErr() {
        final Dialog dialog = new Dialog(PostDetailActivity.this);
        dialog.setContentView(R.layout.dialog_exchange_quantity_err);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        TextView txtQuantity;
        Button btnClose;
        btnClose = (Button) dialog.findViewById(R.id.btn_close);
        txtQuantity = (TextView) dialog.findViewById(R.id.txt_exchange_quantity);
        txtQuantity.setText("Số lượng nhận không lớn hơn " + mIntQuantityOfShare + " cây");

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
    /*dialog quantity receive */
    private void showDialogQuantityDonate() {
        final Dialog dialog = new Dialog(PostDetailActivity.this);
        dialog.setContentView(R.layout.dialog_exchange_quantity_err);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        TextView txtQuantity;
        Button btnClose;
        btnClose = (Button) dialog.findViewById(R.id.btn_close);
        txtQuantity = (TextView) dialog.findViewById(R.id.txt_exchange_quantity);
        txtQuantity.setText("Số lượng cho không lớn hơn " + mIntQuantityOfAccount + " cây");

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    public void createExchange() {
        ExchangeRequest exchangeRequest = new ExchangeRequest(mIntExchangeQuantityDonate,
                mIntExchangeQuantityReceive, mShareIdOfShare, mStrVegetableNeedId);
        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        System.out.println("mExchangeQuantity " + mIntExchangeQuantityDonate);
        System.out.println("mIntExchangeQuantityDonate: " + mIntExchangeQuantityDonate);
        System.out.println("mShareIdOfShare " + mShareIdOfShare);
        System.out.println("veget table id need any vegetable: " + mStrVegetableNeedId);
        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");

        mCreateExchangePresenter.createExchange(exchangeRequest, mUser.getToken());
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lnl_left_menu:
                if (mIntCheck == 1) {
                    checkOpenReport();
                    System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAa");
                    System.out.println("chay report");
                    System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
                } else if (mIntCheck == 2) {
                    checkOpenEdit();
                    System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
                    System.out.println("chay edit");
                    System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
                }
            case R.id.btn_exchange:
                clickBtnExchange();
                break;
        }
    }
    @Override
    public void showInfoPersonal(User user) {
        mUser = user;
        mStrAccountId = user.getAccountId();
        if (mStrAccountId.equals(mStrAccountIdOfPost)) {
            mIntCheck = 2;
            mLnlBtnExchange.setVisibility(View.GONE);
        }
    }

    private void showDialogCreateExchangeSuccess() {
        final Dialog dialog = new Dialog(PostDetailActivity.this);
        dialog.setContentView(R.layout.dialog_exchange_quantity_err);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        TextView txtQuantity;
        Button btnClose;
        btnClose = (Button) dialog.findViewById(R.id.btn_close);
        txtQuantity = (TextView) dialog.findViewById(R.id.txt_exchange_quantity);
        txtQuantity.setText("Trao đổi thành công" );
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PostDetailActivity.this, MainActivity.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
    @Override
    public void createExchangeSuccess(List<ExchangeData> exchangeData) {
        showDialogCreateExchangeSuccess();
    }

    @Override
    public void createExchangeFail() {

    }

    @Override
    public void checkVegetableOfAccountSuccess(List<VegetableData> vegetableData) {
        mIntQuantityOfAccount = vegetableData.get(0).getQuantity();
        mStrVegetableNeedId = vegetableData.get(0).getId();
        if (mIntExchangeQuantityDonate > mIntQuantityOfAccount) {
            showDialogQuantityDonate();
        } else {
            System.out.println("AAAAAAAAAAAAAAA chay api create exchange AAAAAAAAAAAAAAAAAA");
            createExchange();
            System.out.println("AAAAAAAAAAAAAAA chay api create exchange AAAAAAAAAAAAAAAAAA");
        }

        System.out.println("check thanh cong AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAa");
        System.out.println("so luong: " + mIntQuantityOfAccount);
        System.out.println("rau need id: " + mStrVegetableNeedId);
        System.out.println("check thanh cong AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAa");
    }

    @Override
    public void checkVegetableOfAccountFail() {

    }
}
