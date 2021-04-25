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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.democ.R;
import com.example.democ.adapter.GardenPostAdapter;
import com.example.democ.adapter.PosterAccountAdapter;
import com.example.democ.adapter.VegetablePostAdapter;
import com.example.democ.iclick.IClickGarden;
import com.example.democ.iclick.IClickPostAccount;
import com.example.democ.iclick.IClickVegetable;
import com.example.democ.model.AccountData;
import com.example.democ.model.AccountSearchByName;
import com.example.democ.model.AddFriendRequest;
import com.example.democ.model.ExchangeData;
import com.example.democ.model.ExchangeRequest;
import com.example.democ.model.GardenResult;
import com.example.democ.model.PostData;
import com.example.democ.model.VegetableData;
import com.example.democ.model.VegetableShare;
import com.example.democ.presenters.AllGardenPresenter;
import com.example.democ.presenters.AllVegetableByGardenIdPresenter;
import com.example.democ.presenters.CheckVegetableOfAccountPresenter;
import com.example.democ.presenters.CreateExchangePresenter;
import com.example.democ.presenters.GetAllShareByIdPresenter;
import com.example.democ.presenters.GetInfoAccountPresenter;
import com.example.democ.presenters.PersonalPresenter;
import com.example.democ.presenters.SendAddFriendPresenter;
import com.example.democ.room.entities.User;
import com.example.democ.views.AllGardenView;
import com.example.democ.views.AllVegetableByGardenIdView;
import com.example.democ.views.CheckVegetableOfAccountView;
import com.example.democ.views.CreateExchangeView;
import com.example.democ.views.GetAllShareByIdView;
import com.example.democ.views.GetInfoAccountView;
import com.example.democ.views.PersonalView;
import com.example.democ.views.SendAddFriendView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PosterProfileActivity extends AppCompatActivity implements View.OnClickListener, SendAddFriendView, PersonalView,
        GetAllShareByIdView, IClickPostAccount, GetInfoAccountView,
        CreateExchangeView, CheckVegetableOfAccountView, AllGardenView, AllVegetableByGardenIdView {

    private final static String KEY_POST_DETAIL_SEND = "KEY_POST_DETAIL_SEND";
    private final static String KEY_BUNDLE_POSTER_DETAIL = "KEY_BUNDLE_POSTER_DETAIL";
    private LinearLayout mLnlBackProfileHome;
    private TextView mTxtPosterFullName, mTxtTotalPosts;
    private Button mBtnAddFriend, mBtnRemoveAddFriend;
    private LinearLayout mLnlBtnRemoveAddFriend;
    private CircleImageView mImgAvatar;

    private SendAddFriendPresenter mSendAddFriendPresenter;
    private String mStrNameOfShare, mStrAccountUserId, mStrAccountShareId;
    private final static String ADD_FRIEND_1 = "KẾT BẠN";
    private final static String ADD_FRIEND_2 = "ĐÃ GỬI YÊU CẦU KẾT BẠN";
    private final static String ADD_FRIEND_3 = "XÁC NHẬN";
    private final static String ADD_FRIEND_4 = "BẠN BÈ";
    private final static String ADD_FRIEND_2_U = "HỦY KẾT BẠN";
    private final static String ADD_FRIEND_3_U = "3333333";
    private final static String ADD_FRIEND_4_U = "HỦY BẠN BÈ";
    private final static String SEARCH_ACCOUNT = "SearchAccount";

    //RecyclerView
//    private PostByAccountAdapter mPostByAccountAdapter;
    private PosterAccountAdapter mPosterAccountAdapter;
    private RecyclerView mRecyclerView;
    private GetAllShareByIdPresenter mGetAllShareByIdPresenter;
    private ArrayList<PostData> mListPost;
    private int mIntTotalPosts = 0;
    private PersonalPresenter mPersonalPresenter;
    private String mStrPostUser = "", mStrPostTime = "", mStrPostContent = "", mStrPostImage = "",
            mStrAccountIdOfPost = "", mStrAccountId = "", mShareIdOfShare = "", mAccessTokenAAA = "",
            mStrVegetableNeedId = "", mStrVegetableNeedName = "",
            mStrGardenNameDonate = "", mStrVegetableNameDonate = "";
    private int mIntExchangeQuantityDonate = 0, mIntExchangeQuantityReceive = 0,
            mIntQuantityOfShare = 0, mIntSizeListVegetableShare = 0, mIntQuantityOfAccount = 0, mIntIsFriend = 0;
    private User mUser;

    private CreateExchangePresenter mCreateExchangePresenter;
    private CheckVegetableOfAccountPresenter mCheckVegetableOfAccountPresenter;
    private GetInfoAccountPresenter mGetInfoAccountPresenter;
    private Spinner mSpVegetableNedd;
    private List<VegetableShare> mListVegetableNeed;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_poster_profile);
        setContentView(R.layout.activity_poster_profile);
        initialView();
        initialData();
    }

    private void initialView() {
        mPersonalPresenter = new PersonalPresenter(getApplicationContext(), this);
        mCreateExchangePresenter = new CreateExchangePresenter(getApplication(), getApplicationContext(), this);
        mCheckVegetableOfAccountPresenter = new CheckVegetableOfAccountPresenter(getApplication(), getApplicationContext(), this);
        mAllGardenPresenter = new AllGardenPresenter(getApplication(), getApplicationContext(), this);
        mAllVegetableByGardenIdPresenter = new AllVegetableByGardenIdPresenter(getApplication(), getApplicationContext(), this);
        mGetInfoAccountPresenter = new GetInfoAccountPresenter(getApplication(), getApplicationContext(), this);

        getDataPostExchange();
        getDataSearchAccount();
        getDataPostDetail();

        mPersonalPresenter.getInfoPersonal();

        mLnlBackProfileHome = (LinearLayout) findViewById(R.id.lnl_back_profile_home);
        mLnlBackProfileHome.setOnClickListener((View.OnClickListener) this);
        mTxtPosterFullName = (TextView) findViewById(R.id.txt_poster_full_name);
        mImgAvatar = (CircleImageView) findViewById(R.id.img_avatar);
        mTxtPosterFullName.setText(mStrNameOfShare);

        //add friend
        mBtnAddFriend = (Button) findViewById(R.id.btn_add_friend);
        mBtnAddFriend.setOnClickListener(this);
        mBtnRemoveAddFriend = (Button) findViewById(R.id.btn_remove_add_friend);
        mBtnRemoveAddFriend.setOnClickListener(this);
        mLnlBtnRemoveAddFriend = (LinearLayout) findViewById(R.id.lnl_btn_remove_add_friend);
        mLnlBtnRemoveAddFriend.setVisibility(View.GONE);
        mSendAddFriendPresenter = new SendAddFriendPresenter(getApplication(), getApplicationContext(), this);

//        RecyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_share_by_account);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setNestedScrollingEnabled(false);

        mGetAllShareByIdPresenter = new GetAllShareByIdPresenter(getApplication(), getApplicationContext(), this);
        mListPost = new ArrayList<>();
        mTxtTotalPosts = (TextView) findViewById(R.id.txt_total_posts);

        mListVegetableNeed = new ArrayList<>();
    }

    private void initialData() {
//        mGetAllShareByIdPresenter.getAllShareById(mStrAccountUserId, mUser.getToken());

    }

    /*getting date search account name*/
    private void getDataSearchAccount() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        AccountSearchByName accountSearchByName = (AccountSearchByName) bundle.getSerializable(SEARCH_ACCOUNT);
        if (accountSearchByName != null) {
            mStrAccountShareId = accountSearchByName.getAccountId();
            mStrNameOfShare = accountSearchByName.getAccountName();
        }
    }
    /*getting date home*/
    private void getDataPostExchange() {
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
//        mStrNameOfShare = (String) intent.getSerializableExtra("OWNER_PROFILE");
        if (bundle != null) {
            mStrNameOfShare = bundle.getString("NAME_SHARE");
            mStrAccountUserId = bundle.getString("ACCOUNT_ID");
            mStrAccountShareId = bundle.getString("ACCOUNT_SHARE");
        }
    }
    /*getting data post detail*/
    private void getDataPostDetail() {
        Intent intent = this.getIntent();
        Bundle bundle = intent.getBundleExtra(KEY_POST_DETAIL_SEND);

        if (bundle != null) {
            mStrAccountShareId = bundle.getString("ACCOUNT_SHARE");
        }
    }
    /*send friend request*/
    private void clickSendAddFriend() {
        Toast.makeText(getApplication(), mBtnAddFriend.getText(), Toast.LENGTH_SHORT).show();
        String contentType = mBtnAddFriend.getText().toString().trim();
        if (contentType.equals(ADD_FRIEND_1)) {
            mBtnAddFriend.setText(ADD_FRIEND_2);
            AddFriendRequest addFriendRequest = new AddFriendRequest(mUser.getAccountId(), mStrAccountShareId);
            mSendAddFriendPresenter.sendAddFriend(addFriendRequest, mUser.getToken());
        } else if (contentType.equals(ADD_FRIEND_2)) {

            if (mLnlBtnRemoveAddFriend.getVisibility() == View.GONE) {
                Toast.makeText(getApplication(), "222 aaaaaa", Toast.LENGTH_SHORT).show();
                mLnlBtnRemoveAddFriend.setVisibility(View.VISIBLE);
                mBtnRemoveAddFriend.setText(ADD_FRIEND_2_U);
            } else if (mLnlBtnRemoveAddFriend.getVisibility() == View.VISIBLE) {
                mLnlBtnRemoveAddFriend.setVisibility(View.GONE);
            }
        } else if (contentType.equals(ADD_FRIEND_3)) {
            Toast.makeText(getApplication(), "33333333333", Toast.LENGTH_SHORT).show();
        } else if (contentType.equals(ADD_FRIEND_4)) {

            if (mLnlBtnRemoveAddFriend.getVisibility() == View.GONE) {
                mLnlBtnRemoveAddFriend.setVisibility(View.VISIBLE);
                mBtnRemoveAddFriend.setText(ADD_FRIEND_4_U);
            } else if (mLnlBtnRemoveAddFriend.getVisibility() == View.VISIBLE) {
                mLnlBtnRemoveAddFriend.setVisibility(View.GONE);
            }
        }

    }
    /*remove send friend request*/
    private void clickRemoveSendAddFriend() {
        String contentType = mBtnRemoveAddFriend.getText().toString().trim();
        if (contentType.equals(ADD_FRIEND_2_U)) {
            Toast.makeText(getApplication(), "huy kb", Toast.LENGTH_SHORT).show();
            mLnlBtnRemoveAddFriend.setVisibility(View.GONE);
        } else if (contentType.equals(ADD_FRIEND_4_U)) {
            Toast.makeText(getApplication(), "huy ban be", Toast.LENGTH_SHORT).show();
            mLnlBtnRemoveAddFriend.setVisibility(View.GONE);
        }
    }
    public void updateUI() {
        if (mPosterAccountAdapter == null) {
            mPosterAccountAdapter = new PosterAccountAdapter(mListPost, getApplicationContext(), this);
            mRecyclerView.setAdapter(mPosterAccountAdapter);
        } else {
            mPosterAccountAdapter.notifyDataSetChanged();
        }
    }

    private void openDialogShowShare() {
        final Dialog dialog = new Dialog(PosterProfileActivity.this);
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
                    if (edtQuantity.getText().toString().trim().equals("")) {
                        mIntExchangeQuantityReceive = 0;
                    } else {
                        mIntExchangeQuantityReceive = Integer.parseInt(edtQuantity.getText().toString());
                    }

                    if (mIntExchangeQuantityReceive > mIntQuantityOfShare) {
                        showDialogQuantityErr();
                        return;
                    } else if (mIntExchangeQuantityReceive < 1){
                        showDialogInputReceiveZero();
                        return;
                    }else {
                        // goi api exchange
                        ExchangeRequest exchangeRequest = new ExchangeRequest(mIntExchangeQuantityReceive, 0, mShareIdOfShare, "");
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
        mDlExchangeDetermineVegetable = new Dialog(PosterProfileActivity.this);
        mDlExchangeDetermineVegetable.setContentView(R.layout.dialog_exchange_2_quantity);
        mDlExchangeDetermineVegetable.getWindow().setBackgroundDrawableResource(R.color.transparent);
        final EditText edtQuantityDonate, edtQuantityReceive;
        Button btnOk, btnClose;
        edtQuantityReceive = (EditText) mDlExchangeDetermineVegetable.findViewById(R.id.edt_quantity_receive);
        edtQuantityDonate = (EditText) mDlExchangeDetermineVegetable.findViewById(R.id.edt_quantity_donate);
        btnOk = (Button) mDlExchangeDetermineVegetable.findViewById(R.id.btn_ok);
        btnClose = (Button) mDlExchangeDetermineVegetable.findViewById(R.id.btn_close);

        /* spinner*/
        ArrayAdapter<VegetableShare> adapterVegetableNeed = new ArrayAdapter<VegetableShare>(PosterProfileActivity.this,
                android.R.layout.simple_spinner_item, mListVegetableNeed);
        adapterVegetableNeed.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpVegetableNedd = mDlExchangeDetermineVegetable.findViewById(R.id.sp_vegetable_need);
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

                mCheckVegetableOfAccountPresenter.CheckVegetableOfAccountPresenter(mStrVegetableNeedId, mStrVegetableNeedName, mUser.getToken());
//                dialog.dismiss();
            }
        });
        mDlExchangeDetermineVegetable.setCanceledOnTouchOutside(false);
        mDlExchangeDetermineVegetable.show();
    }
    /* show dialog donate any vegetables from the exchange  post*/
    private void openDialogShowAnyVegetable() {
        mDlExchangeAnyVegetable = new Dialog(PosterProfileActivity.this);
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
    public void displaySpinnerVegetableNeed(VegetableShare vegetableShare) {
        String needName = vegetableShare.getVegetableShareName();
        String needId = vegetableShare.getVegetableShareId();
        mStrVegetableNeedId = vegetableShare.getVegetableShareId();
        mStrVegetableNeedName = vegetableShare.getVegetableShareName();
//        createExchange();
    }
    /*dialog quantity receive */
    private void showDialogQuantityErr() {
        final Dialog dialog = new Dialog(PosterProfileActivity.this);
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
        final Dialog dialog = new Dialog(PosterProfileActivity.this);
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
    /*dialog input info err */
    private void showDialogInputInfoErr() {
        final Dialog dialog = new Dialog(PosterProfileActivity.this);
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
        final Dialog dialog = new Dialog(PosterProfileActivity.this);
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
        final Dialog dialog = new Dialog(PosterProfileActivity.this);
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
        final Dialog dialog = new Dialog(PosterProfileActivity.this);
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
        final Dialog dialog = new Dialog(PosterProfileActivity.this);
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
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(PosterProfileActivity.this, DividerItemDecoration.VERTICAL);
        mRecyclerViewGarden.addItemDecoration(itemDecoration);

        dialog.show();
    }
    /*show dialog choose Vegetable*/
    private void showDialogChooseVegetable() {
        final Dialog dialog = new Dialog(PosterProfileActivity.this);
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
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(PosterProfileActivity.this, DividerItemDecoration.VERTICAL);
        mRecyclerViewGarden.addItemDecoration(itemDecoration);

        dialog.show();
    }
    /*goi api create exchange determined vegetable*/
    public void createExchangeDeterminedVegetable() {
        ExchangeRequest exchangeRequest = new ExchangeRequest(mIntExchangeQuantityReceive,
                mIntExchangeQuantityDonate, mShareIdOfShare, mStrVegetableNeedId);

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
            showDialogQuantityErr();
            return;
        } else if (mIntExchangeQuantityDonate > mIntQuantityOfAccount || mIntExchangeQuantityDonate < 1) {
            System.out.println("chay vao else if mIntExchangeQuantityDonate > mIntQuantityOfAccount");
            showDialogQuantityDonate();
            return;
        } else {
            ExchangeRequest exchangeRequest = new ExchangeRequest(mIntExchangeQuantityReceive,
                    mIntExchangeQuantityDonate, mShareIdOfShare, mStrVegetableNeedId);

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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lnl_back_profile_home:
                finish();
                break;
            case R.id.btn_add_friend:
                Toast.makeText(getApplication(), "btn kb", Toast.LENGTH_SHORT).show();
                clickSendAddFriend();
                break;
            case R.id.btn_remove_add_friend:
                clickRemoveSendAddFriend();
                break;
        }
    }

    @Override
    public void sendAddFriendSuccess(AddFriendRequest addFriendRequest) {
        mBtnAddFriend.setText(ADD_FRIEND_2);
        Toast.makeText(getApplication(), "Đã gửi yêu cầu kết bạn", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void sendAddFriendFail() {
        System.out.println("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF");
        System.out.println("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF");
        System.out.println("sendAddFriendFail");
        System.out.println("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF");
        System.out.println("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF");
    }

    @Override
    public void showInfoPersonal(User user) {
        mUser = user;
        mStrAccountUserId = user.getAccountId();

        mGetAllShareByIdPresenter.getAllShareById(mStrAccountShareId, mUser.getToken());
        mGetInfoAccountPresenter.getInfoAccount(mStrAccountShareId, mUser.getToken());
    }

    @Override
    public void getAllShareByIdSuccess(List<PostData> postDataList) {
        mListPost = (ArrayList<PostData>) postDataList;
        if (mListPost.size() > 0) {
            mIntTotalPosts = mListPost.size();
            System.out.println("TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT");
            System.out.println("TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT");
            System.out.println("mIntTotalPosts: " + mIntTotalPosts);
            System.out.println("TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT");
            System.out.println("TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT");
            mTxtTotalPosts.setText(String.valueOf(mIntTotalPosts));
            updateUI();
        }
    }

    @Override
    public void getAllShareByIdFail() {

    }

    @Override
    public void clickPostAccount(PostData postData) {
        mIntQuantityOfShare = postData.getQuantity();
        mShareIdOfShare = postData.getId();
        mIntSizeListVegetableShare = postData.getVegetableShareList().size();
        mListVegetableNeed = postData.getVegetableShareList();

        if (postData.getStatius() == 1) {
            openDialogShowShare();
        } else if (postData.getStatius() == 2) {
            if (mIntSizeListVegetableShare == 0) {
                openDialogShowAnyVegetable();
            } else {
                openDialogShowExchange();
            }
        }
    }

    @Override
    public void clickDeletePostAccount(PostData postData, int positionDelete) {

    }

    @Override
    public void clickCallPhone(PostData postData) {
        String numberPhone = "tel:" + postData.getPhoneNumber().trim();
        Intent callPhone = new Intent(Intent.ACTION_DIAL);
        try {
            callPhone.setData(Uri.parse(numberPhone));
            startActivity(callPhone);
        } catch (Exception ex){
            String phoneErr = getString(R.string.phone_number_error);
            Toast.makeText(this, phoneErr, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void clickPostDetail(PostData postData) {
        Intent intent = new Intent(PosterProfileActivity.this, PostDetailActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_BUNDLE_POSTER_DETAIL, postData);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void showDialogCreateExchangeSuccess() {
        final Dialog dialog = new Dialog(PosterProfileActivity.this);
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
                Intent intent = new Intent(PosterProfileActivity.this, MainActivity.class);
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
            return;
        } else {
            System.out.println("AAAAAAAAAAAAAAA chay api create exchange AAAAAAAAAAAAAAAAAA");
            createExchangeDeterminedVegetable();
            System.out.println("AAAAAAAAAAAAAAA chay api create exchange AAAAAAAAAAAAAAAAAA");
        }
    }

    private void showDialogCheckVegetableNeedErr() {
        final Dialog dialog = new Dialog(PosterProfileActivity.this);
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
    public void checkVegetableOfAccountFail() {
        showDialogCheckVegetableNeedErr();
    }

    @Override
    public void getAllGardenSuccess(List<GardenResult> listAllGarden) {
        mListGarden = new ArrayList<>();
        mListGarden = listAllGarden;
        if (mListGarden != null) {
            showDialogChooseGarden();
        }
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

    @Override
    public void getInfoAccountSuccess(AccountData accountData) {
        mIntIsFriend = accountData.getIsFriend();
        if (mIntIsFriend == 1){
            mBtnAddFriend.setText(ADD_FRIEND_1);
        } else if (mIntIsFriend == 2) {
            mBtnAddFriend.setText(ADD_FRIEND_2);
        } else if (mIntIsFriend == 3) {
            mBtnAddFriend.setText(ADD_FRIEND_3);
        } else if (mIntIsFriend == 4) {
            mBtnAddFriend.setText(ADD_FRIEND_4);
        }

        if (accountData.getAvatarResponse() == null) {
            mImgAvatar.setImageResource(R.drawable.avatardefault);
        }else {
            Picasso.with(PosterProfileActivity.this).load(accountData.getAvatarResponse())
                    .placeholder(R.drawable.avatardefault)
                    .error(R.drawable.avatardefault)
                    .into(mImgAvatar);
        }
    }

    @Override
    public void getInfoAccountFail() {

    }
}
