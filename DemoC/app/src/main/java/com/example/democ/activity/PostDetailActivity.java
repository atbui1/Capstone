package com.example.democ.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
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
import android.widget.Toast;

import com.example.democ.R;
import com.example.democ.adapter.GardenPostAdapter;
import com.example.democ.adapter.VegetablePostAdapter;
import com.example.democ.fragment.AccountEditPostBottomSheetFragment;
import com.example.democ.fragment.ReportPostBottomSheetFragment;
import com.example.democ.iclick.IClickGarden;
import com.example.democ.iclick.IClickVegetable;
import com.example.democ.model.ExchangeData;
import com.example.democ.model.ExchangeRequest;
import com.example.democ.model.GardenResult;
import com.example.democ.model.PostData;
import com.example.democ.model.PostSearchDescription;
import com.example.democ.model.PostSearchKeyword;
import com.example.democ.model.PostSearchName;
import com.example.democ.model.Vegetable;
import com.example.democ.model.VegetableCheckIsExist;
import com.example.democ.model.VegetableData;
import com.example.democ.model.VegetableExchange;
import com.example.democ.model.VegetableNeedAll;
import com.example.democ.presenters.AllGardenPresenter;
import com.example.democ.presenters.AllVegetableByGardenIdPresenter;
import com.example.democ.presenters.CheckVegetableOfAccountPresenter;
import com.example.democ.presenters.CreateExchangePresenter;
import com.example.democ.presenters.PersonalPresenter;
import com.example.democ.room.entities.User;
import com.example.democ.views.AllGardenView;
import com.example.democ.views.AllVegetableByGardenIdView;
import com.example.democ.views.CheckVegetableOfAccountView;
import com.example.democ.views.CreateExchangeView;
import com.example.democ.views.PersonalView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostDetailActivity extends AppCompatActivity implements PersonalView, View.OnClickListener, CreateExchangeView,
        CheckVegetableOfAccountView, AllGardenView, AllVegetableByGardenIdView {

    private final static String KEY_BUNDLE_HOME = "KEY_BUNDLE_HOME";
    private final static String KEY_BUNDLE_DESCRIPTION = "KEY_BUNDLE_DESCRIPTION";
    private final static String KEY_BUNDLE_KEYWORD = "KEY_BUNDLE_KEYWORD";
    private final static String KEY_BUNDLE_NAME = "KEY_BUNDLE_NAME";
    private final static String KEY_POST_DETAIL_SEND = "KEY_POST_DETAIL_SEND";
    private final static String KEY_BUNDLE_POSTER_DETAIL = "KEY_BUNDLE_POSTER_DETAIL";
    private static String POST_SHARE = "Nhận rau";
    private static String POST_EXCHANGE = "Đổi rau";

    private TextView mTxtPostUser, mTxtPostTime, mTxtPostContent, mTxtPostQuantity, mTxtPhoneNumber,
            mTxtDetailName, mTxtDetailDescription, mTxtDetailFeature, mTxtVegetableNeed;
    private ImageView mImgPostImage;
    private CircleImageView mImgPosterImage;
    private LinearLayout mLnlBtnExchange, mLnlLeftMenu, mLnlBack, mLnlRootPoster;
    private Button mBtnExchange;

    private PersonalPresenter mPersonalPresenter;

    private String mStrPostUser = "", mStrPostTime = "", mStrPostContent = "", mStrPostImage = "", mStrPosterImage = "",
            mStrAccountIdOfPost = "", mStrAccountId = "", mShareIdOfShare = "", mStrAccountShareId = ""
            , mStrPhoneNumber = "", mStrVegetableNeedId = "", mStrVegetableNeedName = "",
            mStrGardenNameDonate = "", mStrVegetableNameDonate = "",
            mStrDetailName = "", mStrDetailDescription = "", mStrDetailFeature = "";
    private int mIntCheck = 0, mIntTypePost = 0, mIntExchangeQuantityDonate = 0, mIntExchangeQuantityReceive = 0,
            mIntQuantityOfShare = 0, mIntSizeListVegetableShare = 0, mIntQuantityOfAccount = 0;
    private User mUser;
    private PostData mPostData;
    private PostSearchDescription mPostSearchDescription;
    private PostSearchName mPostSearchName;
    private PostSearchKeyword mPostSearchKeyword;

    private CreateExchangePresenter mCreateExchangePresenter;
    private CheckVegetableOfAccountPresenter mCheckVegetableOfAccountPresenter;
    private Spinner mSpVegetableNedd;
    private List<VegetableExchange> mListVegetableNeed;
    private List<VegetableCheckIsExist> mListVegetableCheckIsExists;
    /*dialog choice garden*/
    private TextView mTxtGardenName, mTxtVegetableName;
    /*dilog garden*/
    private RecyclerView mRecyclerViewGarden, mRecyclerViewVegetable;
    private GardenPostAdapter mGardenPostAdapter;
    private AllGardenPresenter mAllGardenPresenter;
    private VegetablePostAdapter mVegetablePostAdapter;
    private AllVegetableByGardenIdPresenter mAllVegetableByGardenIdPresenter;
    private List<GardenResult> mListGarden;
    private List<VegetableData> mListVegetable;
    private int mIntGardenId = 0;
    private Dialog mDlExchangeAnyVegetable, mDlExchangeDetermineVegetable;
    private int mIntProvinceId = 0, mIntDistrictId = 0, mIntWardId = 0;
    private String mStrSubAddress = "", mStrFullAddress = "";
    //
//    private List<String> mListVegetableExchangeTmp;

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

        /*dialog garden*/
        mAllGardenPresenter = new AllGardenPresenter(getApplication(), getApplicationContext(), this);
        mAllVegetableByGardenIdPresenter = new AllVegetableByGardenIdPresenter(getApplication(), getApplicationContext(), this);


        mListVegetableNeed = new ArrayList<>();

        //

        getDataHome();
        getDataSearchDescription();
        getDataSearchKeyword();
        getDataSearchName();
        getDataPosterProfile();

        mTxtPostUser = (TextView) findViewById(R.id.txt_post_username);
        mTxtPostTime = (TextView) findViewById(R.id.txt_post_time);
        mTxtPostContent = (TextView) findViewById(R.id.txt_post_content);
        mTxtPostQuantity = (TextView) findViewById(R.id.txt_post_vegetable_quantity);
        mImgPostImage = (ImageView) findViewById(R.id.img_post_content);
        mImgPosterImage = (CircleImageView) findViewById(R.id.img_post_user);
        mLnlBtnExchange = (LinearLayout) findViewById(R.id.lnl_btn_exchange);
        mBtnExchange = (Button) findViewById(R.id.btn_exchange);
        mBtnExchange.setOnClickListener(this);
        mLnlLeftMenu = (LinearLayout) findViewById(R.id.lnl_left_menu);
        mLnlLeftMenu.setOnClickListener(this);
        mLnlBack = (LinearLayout) findViewById(R.id.lnl_back);
        mLnlBack.setOnClickListener(this);
        mTxtPhoneNumber = (TextView) findViewById(R.id.txt_post_phone_number);
        mTxtPhoneNumber.setOnClickListener(this);
        mTxtDetailName = (TextView) findViewById(R.id.txt_post_vegetable_name);
        mTxtDetailDescription = (TextView) findViewById(R.id.txt_post_vegetable_description);
        mTxtDetailFeature = (TextView) findViewById(R.id.txt_post_vegetable_feature);
        mLnlRootPoster = (LinearLayout) findViewById(R.id.lnl_root_poster);
        mLnlRootPoster.setOnClickListener(this);
        mTxtVegetableNeed = findViewById(R.id.txt_post_vegetable_need);

        mTxtPostUser.setText(mStrPostUser);
        mTxtPostTime.setText(mStrPostTime);
        mTxtPostContent.setText(mStrPostContent);
        mTxtPostQuantity.setText("Số lượng (chậu): " + String.valueOf(mIntQuantityOfShare));
        mTxtPhoneNumber.setText("Liên hệ: " + mStrPhoneNumber);
        mTxtDetailName.setText(mStrDetailName);
        mTxtDetailDescription.setText(mStrDetailDescription);
        mTxtDetailFeature.setText(mStrDetailFeature);

        if (mIntTypePost == 1) {
            mTxtVegetableNeed.setText("Chia sẻ rau");
        } else if (mIntTypePost == 2) {
            if (mListVegetableNeed.size() == 0 || mListVegetableNeed == null) {
                mTxtVegetableNeed.setText("Nhận lại rau bất kì");
                System.out.println("6666666666666666666666666666666666666");
            } else {
                List<VegetableExchange> ListExchange = mListVegetableNeed;
                List<String> listVegetableExchangeTmp = new ArrayList<>();
                String vegetableNeedName = "";
                for (VegetableExchange x: ListExchange) {
                    vegetableNeedName = x.getVegetableExchangeName();
                    listVegetableExchangeTmp.add(vegetableNeedName);
                }
                mTxtVegetableNeed.setText("Rau cần đổi: " + listVegetableExchangeTmp);
                System.out.println("8888888888888888888888888888888888888888888");
            }
        }

        if (mStrPostImage.equals("")) {
            mImgPostImage.setImageResource(R.mipmap.addimage64);
        } else {
            Picasso.with(this).load(mStrPostImage)
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.caybacha)
                    .into(mImgPostImage);
        }

        if (mStrPosterImage.equals("")) {
            mImgPosterImage.setImageResource(R.drawable.avatardefault);
        } else {
            Picasso.with(this).load(mStrPosterImage)
                    .placeholder(R.drawable.avatardefault)
                    .error(R.drawable.avatardefault)
                    .into(mImgPosterImage);
        }

        if (mIntTypePost == 1) {
            mBtnExchange.setText(POST_SHARE);
        } else if (mIntTypePost == 2) {
            mBtnExchange.setText(POST_EXCHANGE);
        }

    }
    private void initialData() {

        System.out.println("JJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJ");
        System.out.println("jjjjjjjjjjjj: " + mPostData);
        System.out.println("JJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJ");
    }

    /*get data from bundle with 5 types of cases*/
    public void getDataHome() {
//        mListVegetableExchangeTmp = new ArrayList<>();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            mPostData = (PostData) bundle.getSerializable(KEY_BUNDLE_HOME);
            PostData postData = mPostData;
            if (postData != null) {
                mStrPostUser = postData.getFullName().trim();
                mStrPostTime = postData.getCreatedDate().trim().substring(0,10);
                mStrPostContent = postData.getContent().trim();
                mStrAccountIdOfPost = postData.getAccountId().trim();
                mStrPhoneNumber = postData.getPhoneNumber().trim();
                mStrDetailName = postData.getVegName().trim();
                mStrDetailDescription = postData.getVegDescription().trim();
                mStrDetailFeature = postData.getVegFeature().trim();
                mStrAccountShareId = postData.getAccountId().trim();
                mIntCheck = 1;
                mIntTypePost = postData.getType();
                mIntQuantityOfShare = postData.getQuantity();
                mShareIdOfShare = postData.getId().trim();
                mIntSizeListVegetableShare = postData.getVegetableExchange().size();
                mListVegetableNeed = postData.getVegetableExchange();

                int maxSize = postData.getImageVegetablesList().size() - 1;
                if (postData.getImageVegetablesList() == null || postData.getImageVegetablesList().size() == 0) {
                    mStrPostImage = "";
                } else {
                    mStrPostImage = postData.getImageVegetablesList().get(maxSize).getUrl().trim();
                }

                if (postData.getAvatar() == null || postData.getAvatar().equals("")) {
                    mStrPosterImage = "";
                } else {
                    mStrPosterImage = postData.getAvatar().trim();
                }

//                if (postData.getVegetableExchange().size() == 0) {
//                    mListVegetableExchangeTmp = null;
//                } else {
//                    mListVegetableNeed = postData.getVegetableExchange();
//                    String vegetableNeedName = "";
//                    for (VegetableExchange x: mListVegetableNeed) {
//                        vegetableNeedName = x.getVegetableExchangeName();
//                        mListVegetableExchangeTmp.add(vegetableNeedName);
//                    }
//                    System.out.println("8888888888888888888888888888888888888888888");
//                }
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
                mStrPostUser = postData.getFullName().trim();
                mStrPostTime = postData.getCreatedDate().trim().substring(0,10);
                mStrPostContent = postData.getContent().trim();
                mStrAccountIdOfPost = postData.getAccountId().trim();
                mStrPhoneNumber = postData.getPhoneNumber().trim();
                mStrDetailName = postData.getVegName().trim();
                mStrDetailDescription = postData.getVegDescription().trim();
                mStrDetailFeature = postData.getVegFeature().trim();
                mStrAccountShareId = postData.getAccountId().trim();
                mIntCheck = 1;
                mIntTypePost = postData.getType();
                mIntQuantityOfShare = postData.getQuantity();
                mShareIdOfShare = postData.getId().trim();
                mIntSizeListVegetableShare = postData.getVegetableExchange().size();
                mListVegetableNeed = postData.getVegetableExchange();

                int maxSize = postData.getImageVegetablesList().size() - 1;
                if (postData.getImageVegetablesList() == null || postData.getImageVegetablesList().size() == 0) {
                    mStrPostImage = "";
                } else {
                    mStrPostImage = postData.getImageVegetablesList().get(maxSize).getUrl().trim();
                }

                if (postData.getAvatar() == null) {
                    mStrPosterImage = "";
                } else {
                    mStrPosterImage = postData.getAvatar().trim();
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
                mStrPostUser = postData.getFullName().trim();
                mStrPostTime = postData.getCreatedDate().trim().substring(0,10);
                mStrPostContent = postData.getContent().trim();
                mStrAccountIdOfPost = postData.getAccountId().trim();
                mStrPhoneNumber = postData.getPhoneNumber().trim();
                mStrDetailName = postData.getVegName().trim();
                mStrDetailDescription = postData.getVegDescription().trim();
                mStrDetailFeature = postData.getVegFeature().trim();
                mStrAccountShareId = postData.getAccountId().trim();
                mIntCheck = 1;
                mIntTypePost = postData.getType();
                mIntQuantityOfShare = postData.getQuantity();
                mShareIdOfShare = postData.getId().trim();
                mIntSizeListVegetableShare = postData.getVegetableExchange().size();
                mListVegetableNeed = postData.getVegetableExchange();

                int maxSize = postData.getImageVegetablesList().size() - 1;
                if (postData.getImageVegetablesList() == null || postData.getImageVegetablesList().size() == 0) {
                    mStrPostImage = "";
                } else {
                    mStrPostImage = postData.getImageVegetablesList().get(maxSize).getUrl().trim();
                }

                if (postData.getAvatar() == null) {
                    mStrPosterImage = "";
                } else {
                    mStrPosterImage = postData.getAvatar().trim();
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
                mStrPostUser = postData.getFullName().trim();
                mStrPostTime = postData.getCreatedDate().trim().substring(0,10);
                mStrPostContent = postData.getContent().trim();
                mStrAccountIdOfPost = postData.getAccountId().trim();
                mStrPhoneNumber = postData.getPhoneNumber().trim();
                mStrDetailName = postData.getVegName().trim();
                mStrDetailDescription = postData.getVegDescription().trim();
                mStrDetailFeature = postData.getVegFeature().trim();
                mStrAccountShareId = postData.getAccountId().trim();
                mIntCheck = 1;
                mIntTypePost = postData.getType();
                mIntQuantityOfShare = postData.getQuantity();
                mShareIdOfShare = postData.getId().trim();
                mIntSizeListVegetableShare = postData.getVegetableExchange().size();
                mListVegetableNeed = postData.getVegetableExchange();

                int maxSize = postData.getImageVegetablesList().size() - 1;
                if (postData.getImageVegetablesList() == null || postData.getImageVegetablesList().size() == 0) {
                    mStrPostImage = "";
                } else {
                    mStrPostImage = postData.getImageVegetablesList().get(maxSize).getUrl().trim();
                }

                if (postData.getAvatar() == null) {
                    mStrPosterImage = "";
                } else {
                    mStrPosterImage = postData.getAvatar().trim();
                }
            }
        }
    }
    public void getDataPosterProfile() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            mPostData = (PostData) bundle.getSerializable(KEY_BUNDLE_POSTER_DETAIL);
            PostData postData = mPostData;
            if (postData != null) {
                mStrPostUser = postData.getFullName().trim();
                mStrPostTime = postData.getCreatedDate().trim().substring(0,10);
                mStrPostContent = postData.getContent().trim();
                mStrAccountIdOfPost = postData.getAccountId().trim();
                mStrPhoneNumber = postData.getPhoneNumber().trim();
                mStrDetailName = postData.getVegName().trim();
                mStrDetailDescription = postData.getVegDescription().trim();
                mStrDetailFeature = postData.getVegFeature().trim();
                mStrAccountShareId = postData.getAccountId().trim();
                mIntCheck = 1;
                mIntTypePost = postData.getType();
                mIntQuantityOfShare = postData.getQuantity();
                mShareIdOfShare = postData.getId().trim();
                mIntSizeListVegetableShare = postData.getVegetableExchange().size();
                mListVegetableNeed = postData.getVegetableExchange();

                int maxSize = postData.getImageVegetablesList().size() - 1;
                if (postData.getImageVegetablesList() == null || postData.getImageVegetablesList().size() == 0) {
                    mStrPostImage = "";
                } else {
                    mStrPostImage = postData.getImageVegetablesList().get(maxSize).getUrl().trim();
                }

                if (postData.getAvatar() == null) {
                    mStrPosterImage = "";
                } else {
                    mStrPosterImage = postData.getAvatar().trim();
                }
            }
        }
    }
    /*end get data from bundle with 5 types of cases*/

    /*open report dialog bottom sheet with 4 types of cases*/
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
    /*end report dialog bottom sheet with 4 types of cases*/

    /*open edit post dialog bottom sheet 3 types of cases*/
    private void clickOpenEditBottomSheetSearchName(PostSearchName postData) {
        String token = mUser.getToken();
        AccountEditPostBottomSheetFragment accountEditPostBottomSheetFragment =
                new AccountEditPostBottomSheetFragment(postData, token);
        accountEditPostBottomSheetFragment.show(getSupportFragmentManager(), accountEditPostBottomSheetFragment.getTag());
    }
    private void clickOpenEditBottomSheetSearchKeyword(PostSearchKeyword postData) {
        String token = mUser.getToken();
        AccountEditPostBottomSheetFragment accountEditPostBottomSheetFragment =
                new AccountEditPostBottomSheetFragment(postData, token);
        accountEditPostBottomSheetFragment.show(getSupportFragmentManager(), accountEditPostBottomSheetFragment.getTag());
    }
    private void clickOpenEditBottomSheetSearchDescription(PostSearchDescription postData) {
        String token = mUser.getToken();
        AccountEditPostBottomSheetFragment accountEditPostBottomSheetFragment =
                new AccountEditPostBottomSheetFragment(postData, token);
        accountEditPostBottomSheetFragment.show(getSupportFragmentManager(), accountEditPostBottomSheetFragment.getTag());
    }
    /*end edit post dialog bottom sheet 4 types of cases*/
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
    /*clickBtnExchange*/
    private void clickBtnExchange() {
        if (mIntTypePost == 1) {
            openDialogShowShare();
        } else if (mIntTypePost == 2) {
            if (mIntSizeListVegetableShare == 0) {
                System.out.println("show dia log trao doi rau bat ki");
                openDialogShowAnyVegetable();
            } else {
                System.out.println("show dilog nhan rau co dinh");
                checkVegetableAAA();
//                openDialogShowExchange();
            }
        }
    }
    /*check vegetable*/
    private void checkVegetableAAA(){
        List<String> listVegetableExchangeTmp = new ArrayList<>();
        String vegetableExchangeId = "";
        for (VegetableExchange x: mListVegetableNeed) {
            System.out.println("7777777777777777777777777777777777777777777777777777777");
            System.out.println(x.getVegetableExchangeId());
            System.out.println(x.getVegetableExchangeName());
            vegetableExchangeId = x.getVegetableExchangeId();
            listVegetableExchangeTmp.add(vegetableExchangeId);
            System.out.println("8888888888888888888888888888888888888888888");
        }
        System.out.println("list id vegetable exchange aaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        System.out.println(listVegetableExchangeTmp);
        VegetableCheckIsExist vegetableCheckIsExist = new VegetableCheckIsExist(listVegetableExchangeTmp);
        mCheckVegetableOfAccountPresenter.CheckVegetableOfAccountPresenter(listVegetableExchangeTmp, mUser.getToken());
        System.out.println("list id vegetable exchange bbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
    }
    /* show dialog get vegetables from the shared post*/
    private void openDialogShowShare() {
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
                        mIntExchangeQuantityReceive = 0;
                    } else {
                        mIntExchangeQuantityReceive = Integer.parseInt(edtQuantity.getText().toString());
                    }

                    if (mIntExchangeQuantityReceive > mIntQuantityOfShare || mIntExchangeQuantityReceive < 1) {
                        showDialogQuantityReceiveErr();
                        System.out.println("chay vao if showDialogQuantityErr");
                    } else {
                        // goi api exchange
//                        ExchangeRequest exchangeRequest = new ExchangeRequest(mIntExchangeQuantityReceive, 0, mShareIdOfShare, "");
                        ExchangeRequest exchangeRequest = new ExchangeRequest(mIntExchangeQuantityReceive, 0,
                                mIntProvinceId, mIntDistrictId, mIntWardId, mStrSubAddress, mStrFullAddress, mShareIdOfShare, "");
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
    /* show dialog get vegetables from exchange post */
    private void openDialogShowExchange() {
        mDlExchangeDetermineVegetable = new Dialog(PostDetailActivity.this);
        mDlExchangeDetermineVegetable.setContentView(R.layout.dialog_exchange_2_quantity);
        mDlExchangeDetermineVegetable.getWindow().setBackgroundDrawableResource(R.color.transparent);
        final EditText edtQuantityDonate, edtQuantityReceive;
        Button btnOk, btnClose;
        edtQuantityReceive = (EditText) mDlExchangeDetermineVegetable.findViewById(R.id.edt_quantity_receive);
        edtQuantityDonate = (EditText) mDlExchangeDetermineVegetable.findViewById(R.id.edt_quantity_donate);
        btnOk = (Button) mDlExchangeDetermineVegetable.findViewById(R.id.btn_ok);
        btnClose = (Button) mDlExchangeDetermineVegetable.findViewById(R.id.btn_close);

        /* spinner*/
//        ArrayAdapter<VegetableExchange> adapterVegetableNeed = new ArrayAdapter<VegetableExchange>(PostDetailActivity.this,
//                android.R.layout.simple_spinner_item, mListVegetableNeed);
        ArrayAdapter<VegetableData> adapterVegetableNeed = new ArrayAdapter<VegetableData>(PostDetailActivity.this,
                android.R.layout.simple_spinner_item, mListVegetable);
        adapterVegetableNeed.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpVegetableNedd = mDlExchangeDetermineVegetable.findViewById(R.id.sp_vegetable_need);
        mSpVegetableNedd.setAdapter(adapterVegetableNeed);
        mSpVegetableNedd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                VegetableExchange vegetableExchange = (VegetableExchange) adapterView.getSelectedItem();
//                displaySpinnerVegetableNeed(vegetableExchange);
                VegetableData vegetableData = (VegetableData) adapterView.getSelectedItem();
                mIntQuantityOfAccount = vegetableData.getQuantity();
                mStrVegetableNeedId = vegetableData.getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDlExchangeDetermineVegetable.dismiss();
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantityDonate = 0, quantityReceive = 0;
                try {
                    String donateTmp = edtQuantityDonate.getText().toString().trim();
                    String receiveTmp = edtQuantityReceive.getText().toString().trim();
                    if (receiveTmp.equals("")) {
                        showDialogInputReceiveZero();
                        return;
                    } else if(donateTmp.equals("")) {
                        showDialogInputDonateZero();
                        return;
                    } else {
                        quantityDonate = Integer.parseInt(donateTmp);
                        quantityReceive = Integer.parseInt(receiveTmp);
                    }


                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                }
                mIntExchangeQuantityDonate = quantityDonate;
                mIntExchangeQuantityReceive = quantityReceive;

//                mCheckVegetableOfAccountPresenter.CheckVegetableOfAccountPresenter(mStrVegetableNeedId, mStrVegetableNeedName, mUser.getToken());

                /*new*/
                if (mIntExchangeQuantityReceive > mIntQuantityOfShare) {
                    showDialogQuantityReceiveErr();
                    return;
                } else if (mIntExchangeQuantityDonate > mIntQuantityOfAccount) {
                    showDialogQuantityDonate();
                    return;
                }
                System.out.println("AAAAAAAAAAAAAAA chay api create exchange AAAAAAAAAAAAAAAAAA");
                ExchangeRequest exchangeRequest = new ExchangeRequest(mIntExchangeQuantityReceive, mIntExchangeQuantityDonate,
                        mIntProvinceId, mIntDistrictId, mIntWardId, mStrSubAddress, mStrFullAddress, mShareIdOfShare, mStrVegetableNeedId);

                System.out.println("BBBBBBBBBBBBBBBBBBBBb   exchangeRequest BBBBBBBBBBBBBBBBBBBBBBBBBBB");
                System.out.println("sl cho: " + mIntExchangeQuantityDonate);
                System.out.println("sl nhan: " + mIntExchangeQuantityReceive);
                System.out.println("id bai post: " + mShareIdOfShare);
                System.out.println("id rau cho: " + mStrVegetableNeedId);
                System.out.println("name rau cho: " + mStrVegetableNeedName);
                System.out.println("BBBBBBBBBBBBBBBBBBBBb   exchangeRequest BBBBBBBBBBBBBBBBBBBBBBBBBBB");

                mCreateExchangePresenter.createExchange(exchangeRequest, mUser.getToken());

                mDlExchangeDetermineVegetable.dismiss();
            }
        });
        mDlExchangeDetermineVegetable.setCanceledOnTouchOutside(false);
        mDlExchangeDetermineVegetable.show();
    }
    /* show dialog donate any vegetables from the exchange  post*/
    private void openDialogShowAnyVegetable() {
        mDlExchangeAnyVegetable = new Dialog(PostDetailActivity.this);
        mDlExchangeAnyVegetable.setContentView(R.layout.dialog_exchange_any_vegetable_new);
        mDlExchangeAnyVegetable.getWindow().setBackgroundDrawableResource(R.color.transparent);
        final EditText edtQuantityDonate, edtQuantityReceive;

        Button btnOk, btnClose;

        edtQuantityDonate = mDlExchangeAnyVegetable.findViewById(R.id.edt_quantity_donate);
        edtQuantityReceive = mDlExchangeAnyVegetable.findViewById(R.id.edt_quantity_receive);
        mTxtGardenName = (TextView) mDlExchangeAnyVegetable.findViewById(R.id.txt_garden_name);
        mTxtVegetableName = (TextView) mDlExchangeAnyVegetable.findViewById(R.id.txt_vegetable_name);
        btnClose = mDlExchangeAnyVegetable.findViewById(R.id.btn_close);
        btnOk = mDlExchangeAnyVegetable.findViewById(R.id.btn_ok);

        mTxtGardenName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mAllGardenPresenter.getAllGarden(mUser.getToken());

            }
        });

        mTxtVegetableName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mStrGardenNameDonate.equals("")) {
                    showDialogInputGardenZero();
                    return;
                } else {
                    mAllVegetableByGardenIdPresenter.getAllVegetableByGardenId(mIntGardenId, mUser.getToken());
                }
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDlExchangeAnyVegetable.dismiss();
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantityDonate = 0, quantityReceive = 0;
                try {
                    String donateTmp = edtQuantityDonate.getText().toString().trim();
                    String receiveTmp = edtQuantityReceive.getText().toString().trim();
                    if (receiveTmp.equals("")) {
                        showDialogInputReceiveZero();
                        return;
                    } else if(donateTmp.equals("")) {
                        showDialogInputDonateZero();
                        return;
                    } else {
                        quantityDonate = Integer.parseInt(donateTmp);
                        quantityReceive = Integer.parseInt(receiveTmp);
                    }
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                }
                mIntExchangeQuantityDonate = quantityDonate;
                mIntExchangeQuantityReceive = quantityReceive;

                createExchangeAnyVegetable();

//                dialog.dismiss();
            }
        });

        mDlExchangeAnyVegetable.show();
    }
    /* display vegetable need*/
    public void displaySpinnerVegetableNeed(VegetableExchange vegetableExchange) {
        String needName = vegetableExchange.getVegetableExchangeName();
        String needId = vegetableExchange.getVegetableExchangeId();
        mStrVegetableNeedId = vegetableExchange.getVegetableExchangeId();
        mStrVegetableNeedName = vegetableExchange.getVegetableExchangeName();
//        createExchange();
    }
    /*dialog quantity receive */
    private void showDialogQuantityReceiveErr() {
        final Dialog dialog = new Dialog(PostDetailActivity.this);
        dialog.setContentView(R.layout.dialog_exchange_quantity_err);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        TextView txtQuantity;
        Button btnClose;
        btnClose = (Button) dialog.findViewById(R.id.btn_close);
        txtQuantity = (TextView) dialog.findViewById(R.id.txt_exchange_quantity);
        txtQuantity.setText("Số lượng nhận không lớn hơn " + mIntQuantityOfShare + " chậu");

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
        txtQuantity.setText("Số lượng cho không lớn hơn " + mIntQuantityOfAccount + " chậu");

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
    /*dialog input info err */
    private void showDialogInputInfoErr() {
        final Dialog dialog = new Dialog(PostDetailActivity.this);
        dialog.setContentView(R.layout.dialog_exchange_quantity_err);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        TextView txtQuantity;
        Button btnClose;
        btnClose = (Button) dialog.findViewById(R.id.btn_close);
        txtQuantity = (TextView) dialog.findViewById(R.id.txt_exchange_quantity);
        txtQuantity.setText("Chọn vườn rau và tên rau muốn cho");

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
    /*dialog input garden  zero */
    private void showDialogInputGardenZero() {
        final Dialog dialog = new Dialog(PostDetailActivity.this);
        dialog.setContentView(R.layout.dialog_exchange_quantity_err);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        TextView txtQuantity;
        Button btnClose;
        btnClose = (Button) dialog.findViewById(R.id.btn_close);
        txtQuantity = (TextView) dialog.findViewById(R.id.txt_exchange_quantity);
        txtQuantity.setText("Vui lòng chọn vườn rau");

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
    /*dialog quantity donate zero */
    private void showDialogInputDonateZero() {
        final Dialog dialog = new Dialog(PostDetailActivity.this);
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
    /*dialog quantity receive zero */
    private void showDialogInputReceiveZero() {
        final Dialog dialog = new Dialog(PostDetailActivity.this);
        dialog.setContentView(R.layout.dialog_exchange_quantity_err);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        TextView txtQuantity;
        Button btnClose;
        btnClose = (Button) dialog.findViewById(R.id.btn_close);
        txtQuantity = (TextView) dialog.findViewById(R.id.txt_exchange_quantity);
        txtQuantity.setText("Vui lòng nhập số lượng nhận");

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
    /*show dialog choose garden*/
    private void showDialogChooseGarden() {
        final Dialog dialog = new Dialog(PostDetailActivity.this);
        dialog.setContentView(R.layout.dialog_choose_garden);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);

        mRecyclerViewGarden = (RecyclerView)  dialog.findViewById(R.id.recycler_garden_dialog);
        mRecyclerViewGarden.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(dialog.getContext());
        mRecyclerViewGarden.setLayoutManager(layoutManager);

        mGardenPostAdapter = new GardenPostAdapter((ArrayList<GardenResult>) mListGarden, new IClickGarden() {
            @Override
            public void clickGarden(GardenResult gardenResult) {
                mTxtGardenName.setText(gardenResult.getName());
                mTxtVegetableName.setText("Chọn rau muốn cho");
                mStrGardenNameDonate = gardenResult.getName();
                mIntGardenId = gardenResult.getId();
                dialog.dismiss();
            }
        });
        mRecyclerViewGarden.setAdapter(mGardenPostAdapter);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(PostDetailActivity.this, DividerItemDecoration.VERTICAL);
        mRecyclerViewGarden.addItemDecoration(itemDecoration);

        dialog.show();
    }
    /*show dialog choose Vegetable*/
    private void showDialogChooseVegetable() {
        final Dialog dialog = new Dialog(PostDetailActivity.this);
        dialog.setContentView(R.layout.dialog_choose_garden);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);

        mStrGardenNameDonate = mTxtGardenName.getText().toString().trim();

        mRecyclerViewGarden = (RecyclerView)  dialog.findViewById(R.id.recycler_garden_dialog);
        mRecyclerViewGarden.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(dialog.getContext());
        mRecyclerViewGarden.setLayoutManager(layoutManager);

        mVegetablePostAdapter = new VegetablePostAdapter((ArrayList<VegetableData>) mListVegetable, new IClickVegetable() {
            @Override
            public void clickVegetable(VegetableData vegetableData) {
                mTxtVegetableName.setText(vegetableData.getName());
                mStrVegetableNameDonate = vegetableData.getName().trim();
                mStrVegetableNeedId = vegetableData.getId().trim();
                mIntQuantityOfAccount = vegetableData.getQuantity();
                dialog.dismiss();
            }});
        mRecyclerViewGarden.setAdapter(mVegetablePostAdapter);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(PostDetailActivity.this, DividerItemDecoration.VERTICAL);
        mRecyclerViewGarden.addItemDecoration(itemDecoration);

        dialog.show();
    }
    /*goi api create exchange determined vegetable*/
    public void createExchangeDeterminedVegetable() {
//        ExchangeRequest exchangeRequest = new ExchangeRequest(mIntExchangeQuantityReceive,
//                mIntExchangeQuantityDonate, mShareIdOfShare, mStrVegetableNeedId);
        ExchangeRequest exchangeRequest = new ExchangeRequest(mIntExchangeQuantityReceive, mIntExchangeQuantityDonate,
                mIntProvinceId, mIntDistrictId, mIntWardId, mStrSubAddress, mStrFullAddress, mShareIdOfShare, mStrVegetableNeedId);
        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        System.out.println("mExchangeQuantity " + mIntExchangeQuantityDonate);
        System.out.println("mIntExchangeQuantityDonate: " + mIntExchangeQuantityDonate);
        System.out.println("mShareIdOfShare " + mShareIdOfShare);
        System.out.println("veget table id need any vegetable: " + mStrVegetableNeedId);
        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");

        mCreateExchangePresenter.createExchange(exchangeRequest, mUser.getToken());
    }
    /*goi api create exchange any vegetable*/
    public void createExchangeAnyVegetable() {
        int status = 2;

        if (mStrGardenNameDonate.equals("") || mStrVegetableNameDonate.equals("")){
            showDialogInputInfoErr();
            return;
        } else if (mIntExchangeQuantityReceive > mIntQuantityOfShare || mIntExchangeQuantityReceive < 1) {
            System.out.println("chay vao if mIntExchangeQuantityReceive > mIntQuantityOfShare");
            showDialogQuantityReceiveErr();
            return;
        } else if (mIntExchangeQuantityDonate > mIntQuantityOfAccount || mIntExchangeQuantityDonate < 1) {
            System.out.println("chay vao else if mIntExchangeQuantityDonate > mIntQuantityOfAccount");
            showDialogQuantityDonate();
            return;
        } else {
//            ExchangeRequest exchangeRequest = new ExchangeRequest(mIntExchangeQuantityReceive,
//                    mIntExchangeQuantityDonate, mShareIdOfShare, mStrVegetableNeedId);
            ExchangeRequest exchangeRequest = new ExchangeRequest(mIntExchangeQuantityReceive, mIntExchangeQuantityDonate,
                    mIntProvinceId, mIntDistrictId, mIntWardId, mStrSubAddress, mStrFullAddress, mShareIdOfShare, mStrVegetableNeedId);

            System.out.println("nhan bat ki");
            System.out.println("1: " + mIntExchangeQuantityDonate);
            System.out.println("2: " + mIntExchangeQuantityReceive);
            System.out.println("3: " + mShareIdOfShare);
            System.out.println("4: " + mStrVegetableNeedId);
            System.out.println("nhan bat ki");
            mDlExchangeAnyVegetable.dismiss();
            mCreateExchangePresenter.createExchange(exchangeRequest, mUser.getToken());
        }
        System.out.println("ket thuc goi api exchange createExchange");
    }

    public void callPhonePoster() {
        String numberPhone = "tel:" + mStrPhoneNumber.trim();
        Intent callPhone = new Intent(Intent.ACTION_DIAL);
        try {
            callPhone.setData(Uri.parse(numberPhone));
            startActivity(callPhone);
        } catch (Exception ex){
            String phoneErr = getString(R.string.phone_number_error);
            Toast.makeText(this, phoneErr, Toast.LENGTH_SHORT).show();
        }
    }
    public void openPosterProfile() {
        Intent intent = new Intent(PostDetailActivity.this, PosterProfileActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        Bundle bundle = new Bundle();

        bundle.putString("ACCOUNT_SHARE", mStrAccountShareId);
        intent.putExtra(KEY_POST_DETAIL_SEND, bundle);
        startActivity(intent);
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

                break;
            case R.id.btn_exchange:
                clickBtnExchange();
                break;
            case R.id.lnl_back:
                finish();
                break;
            case R.id.txt_post_phone_number:
                callPhonePoster();
                break;
            case R.id.lnl_root_poster:
                openPosterProfile();
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

        mIntProvinceId = user.getProvinceId();
        mIntDistrictId = user.getDistrictId();
        mIntWardId = user.getWardId();
        mStrFullAddress = user.getAddress();
        if (!mStrFullAddress.equals("")) {
            String[] addressTmp = mStrFullAddress.split(",");
            mStrSubAddress = addressTmp[0];
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
        txtQuantity.setText("Gửi yêu cầu thành công" );
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
//        mIntQuantityOfAccount = vegetableData.get(0).getQuantity();
//        mStrVegetableNeedId = vegetableData.get(0).getId();
//        if (mIntExchangeQuantityDonate > mIntQuantityOfAccount) {
//            showDialogQuantityDonate();
//            return;
//        } else {
//            System.out.println("AAAAAAAAAAAAAAA chay api create exchange AAAAAAAAAAAAAAAAAA");
//            createExchangeDeterminedVegetable();
//            System.out.println("AAAAAAAAAAAAAAA chay api create exchange AAAAAAAAAAAAAAAAAA");
//        }
//
//        System.out.println("check thanh cong AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAa");
//        System.out.println("so luong: " + mIntQuantityOfAccount);
//        System.out.println("rau need id: " + mStrVegetableNeedId);
//        System.out.println("check thanh cong AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAa");

        mListVegetable = vegetableData;
        if (mListVegetable.size() > 0 || mListVegetable != null) {
            openDialogShowExchange();
        }
    }

    @Override
    public void checkVegetableOfAccountFail(String msg) {
        System.out.println("FFFFFFFFFFFFFFFFFFF FFFFFFFFFFFFFFFFFFFFFFF FFFFFFFFFFFFFFFFFFFFFFFFFF");
        System.out.println("FFFFFFFFFFFFFFFFFFF FFFFFFFFFFFFFFFFFFFFFFF FFFFFFFFFFFFFFFFFFFFFFFFFF");
        System.out.println("mess: " + msg);
        System.out.println("FFFFFFFFFFFFFFFFFFF FFFFFFFFFFFFFFFFFFFFFFF FFFFFFFFFFFFFFFFFFFFFFFFFF");
        System.out.println("FFFFFFFFFFFFFFFFFFF FFFFFFFFFFFFFFFFFFFFFFF FFFFFFFFFFFFFFFFFFFFFFFFFF");
    }

    private void showDialogCheckVegetableNeedErr() {
        final Dialog dialog = new Dialog(PostDetailActivity.this);
        dialog.setContentView(R.layout.dialog_exchange_quantity_err);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        TextView txtQuantity;
        Button btnClose;
        btnClose = (Button) dialog.findViewById(R.id.btn_close);
        txtQuantity = (TextView) dialog.findViewById(R.id.txt_exchange_quantity);
        txtQuantity.setText("Bạn không có: " + mStrVegetableNeedName );
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
    public void getAllGardenSuccess(List<GardenResult> listAllGarden) {
        mListGarden = new ArrayList<>();
        mListGarden = listAllGarden;
        if (mListGarden != null) {
            showDialogChooseGarden();
        }
        System.out.println("GGGGGGGGGGGGG  getAllGardenSuccess  GGGGGGGGGGGGGGGGGGG ");
        System.out.println("garden list: " + mListGarden.size());
        System.out.println("GGGGGGGGGGGGG  getAllGardenSuccess  GGGGGGGGGGGGGGGGGGG ");
    }

    @Override
    public void getAllGardenFail() {

    }

    @Override
    public void getAllVegetableByGardenIdSuccess(List<VegetableData> vegetableData) {
        mListVegetable = vegetableData;
        if (mListVegetable != null) {
            showDialogChooseVegetable();
        }
    }

    @Override
    public void getAllVegetableByGardenIdFail() {

    }
}
