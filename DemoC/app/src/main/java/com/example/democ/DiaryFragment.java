package com.example.democ;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.democ.activity.PostDetailActivity;
import com.example.democ.activity.PosterProfileActivity;
import com.example.democ.activity.QRCodeScannerActivity;
import com.example.democ.activity.SearchActivity;
import com.example.democ.adapter.GardenPostAdapter;
import com.example.democ.adapter.PostAdapter;
import com.example.democ.adapter.VegetablePostAdapter;
import com.example.democ.fragment.ReportPostBottomSheetFragment;
import com.example.democ.iclick.IClickGarden;
import com.example.democ.iclick.IClickPost;
import com.example.democ.iclick.IClickVegetable;
import com.example.democ.model.ExchangeData;
import com.example.democ.model.ExchangeRequest;
import com.example.democ.model.GardenResult;
import com.example.democ.model.PostData;
import com.example.democ.model.VegetableCheckIsExist;
import com.example.democ.model.VegetableData;
import com.example.democ.model.VegetableExchange;
import com.example.democ.presenters.AllGardenPresenter;
import com.example.democ.presenters.AllSharePresenter;
import com.example.democ.presenters.AllVegetableByGardenIdPresenter;
import com.example.democ.presenters.CheckVegetableOfAccountPresenter;
import com.example.democ.presenters.CreateExchangePresenter;
import com.example.democ.presenters.PersonalPresenter;
import com.example.democ.room.entities.User;
import com.example.democ.views.AllGardenView;
import com.example.democ.views.AllShareView;
import com.example.democ.views.AllVegetableByGardenIdView;
import com.example.democ.views.CheckVegetableOfAccountView;
import com.example.democ.views.CreateExchangeView;
import com.example.democ.views.PersonalView;

import java.util.ArrayList;
import java.util.List;


public class DiaryFragment extends Fragment implements View.OnClickListener, IClickPost, CreateExchangeView, CheckVegetableOfAccountView,
        PersonalView, AllGardenView, AllVegetableByGardenIdView, AllShareView {

    private final static String KEY_BUNDLE_HOME = "KEY_BUNDLE_HOME";
    private LinearLayout mLnlSearch, mLnlQRCode;

    private View mView;
    private RecyclerView mRecyclerPost;
    private List<PostData> mPostList;
    private List<ExchangeData> mExchangeList;
    private PostAdapter mPostAdapter;


    //new
    private User mUser;
    private int mIntProvinceId = 0, mIntDistrictId = 0, mIntWardId = 0;
    private String mStrSubAddress = "", mStrFullAddress = "";
    int mIntExchangeQuantityDonate = 0;
    int mIntExchangeQuantityReceive = 0;
    int mIntQuantityOfShare = 0;
    int mIntQuantityOfAccount = 0;
    String mStrVegetableNeedId = "", mStrVegetableNeedName = "",
           mShareIdOfShare = "", mAccountIdUser = "", mAccountIdOfShare = "", mVegetableIdOfShare = "", mStrNameOfShare = "",
           mStrGardenNameDonate = "", mStrVegetableNameDonate = "";

    private AllSharePresenter mAllSharePresenter;
    private CreateExchangePresenter mCreateExchangePresenter;
    private CheckVegetableOfAccountPresenter mCheckVegetableOfAccountPresenter;
    private PersonalPresenter mPersonalPresenter;
    private AllGardenPresenter mAllGardenPresenter;
    private AllVegetableByGardenIdPresenter mAllVegetableByGardenIdPresenter;

    /* Spinner*/
    private Spinner mSpVegetableNeed;
    private List<GardenResult> mListGarden;
    private List<VegetableData> mListVegetable;
    private List<VegetableExchange> mListVegetableNeed;
    private Dialog mDlExchangeDetermineVegetable, mDlExchangeAnyVegetable;
    private TextView mTxtGardenName, mTxtVegetableName;
    private int mIntGardenId = 0;
    private RecyclerView mRecyclerViewGarden;
    private VegetablePostAdapter mVegetablePostAdapter;
    private GardenPostAdapter mGardenPostAdapter;


//    public DiaryFragment(List<PostData> mPostList, String mAccessToken, User mUser) {
//        this.mPostList = mPostList;
//        this.mAccessToken = mAccessToken;
//        this.mUser = mUser;
//    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_diary, container, false);
        initialView();
        initialData();
        return mView;
    }

    private void initialView() {

        mPersonalPresenter = new PersonalPresenter(getActivity(), this);
        mPersonalPresenter.getInfoPersonal();

        mLnlSearch = (LinearLayout) mView.findViewById(R.id.lnl_search);
        mLnlSearch.setOnClickListener(this);
        mLnlQRCode = (LinearLayout) mView.findViewById(R.id.lnl_qr_code);
        mLnlQRCode.setOnClickListener(this);

        mRecyclerPost = mView.findViewById(R.id.recycler_diary);
        mRecyclerPost.setHasFixedSize(true);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),
//                LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerPost.setLayoutManager(layoutManager);

        mCreateExchangePresenter = new CreateExchangePresenter(getActivity().getApplication(), getActivity(), this);
        mCheckVegetableOfAccountPresenter = new CheckVegetableOfAccountPresenter(getActivity().getApplication(), getActivity(), this);
        mAllGardenPresenter = new AllGardenPresenter(getActivity().getApplication(), getActivity(), this);
        mAllVegetableByGardenIdPresenter = new AllVegetableByGardenIdPresenter(getActivity().getApplication(), getActivity(), this);
        mAllSharePresenter = new AllSharePresenter(getActivity().getApplication(), getActivity(), this);


        /* Spinner */
        mListGarden = new ArrayList<>();
        mListVegetable = new ArrayList<>();
        mListVegetableNeed = new ArrayList<>();
    }

    private void initialData() {

//        updateUI();
    }

    public void updateUI() {
        if (mPostAdapter == null) {
            mPostAdapter = new PostAdapter((ArrayList<PostData>) mPostList, getContext().getApplicationContext(), this);
            mRecyclerPost.setAdapter(mPostAdapter);
        } else {
            mPostAdapter.notifyDataSetChanged();
        }
    }

//    dialog quantity share
    private void openDialogShowShare() {
        final Dialog dialog = new Dialog(getContext());
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
                        showDialogInputReceiveZero();
                        return;
                    } else {
                        mIntExchangeQuantityReceive = Integer.parseInt(edtQuantity.getText().toString());
                    }

                    if (mIntExchangeQuantityReceive > mIntQuantityOfShare || mIntExchangeQuantityReceive < 1) {
                        showDialogQuantityReceiveErr();
                        return;
                    }

//                    ExchangeRequest exchangeRequest = new ExchangeRequest(mIntExchangeQuantityReceive, 0, mShareIdOfShare, "");
                    ExchangeRequest exchangeRequest = new ExchangeRequest(mIntExchangeQuantityReceive, 0,
                            mIntProvinceId, mIntDistrictId, mIntWardId, mStrSubAddress, mStrFullAddress, mShareIdOfShare, "");

                    System.out.println("*********************nhanrau*************************************");
                    System.out.println("mIntExchangeQuantityReceive:" + mIntExchangeQuantityReceive);
                    System.out.println("mIntProvinceId:" + mIntProvinceId);
                    System.out.println("mIntDistrictId:" + mIntDistrictId);
                    System.out.println("mIntWardId:" + mIntWardId);
                    System.out.println("mStrSubAddress:" + mStrSubAddress);
                    System.out.println("mStrFullAddress:" + mStrFullAddress);
                    System.out.println("mShareIdOfShare:" + mShareIdOfShare);

                    mCreateExchangePresenter.createExchange(exchangeRequest, mUser.getToken());

                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                }
                dialog.dismiss();
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
    /* show dialog get vegetables from exchange post */
    private void openDialogShowExchange() {
        mDlExchangeDetermineVegetable = new Dialog(getContext());
        mDlExchangeDetermineVegetable.setContentView(R.layout.dialog_exchange_2_quantity);
        mDlExchangeDetermineVegetable.getWindow().setBackgroundDrawableResource(R.color.transparent);
        final EditText edtQuantityDonate, edtQuantityReceive;
        Button btnOk, btnClose;
        edtQuantityReceive = (EditText) mDlExchangeDetermineVegetable.findViewById(R.id.edt_quantity_receive);
        edtQuantityDonate = (EditText) mDlExchangeDetermineVegetable.findViewById(R.id.edt_quantity_donate);
        btnOk = (Button) mDlExchangeDetermineVegetable.findViewById(R.id.btn_ok);
        btnClose = (Button) mDlExchangeDetermineVegetable.findViewById(R.id.btn_close);

        /* spinner*/
//        ArrayAdapter<VegetableExchange> adapterVegetableNeed = new ArrayAdapter<VegetableExchange>(getContext(),
//                android.R.layout.simple_spinner_item, mListVegetableNeed);
        ArrayAdapter<VegetableData> adapterVegetableNeed = new ArrayAdapter<VegetableData>(getContext(),
                android.R.layout.simple_spinner_item, mListVegetable);
        adapterVegetableNeed.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpVegetableNeed = mDlExchangeDetermineVegetable.findViewById(R.id.sp_vegetable_need);
        mSpVegetableNeed.setAdapter(adapterVegetableNeed);
        mSpVegetableNeed.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                VegetableExchange vegetableExchange = (VegetableExchange) adapterView.getSelectedItem();
//                displaySpinnerVegetableNeed(vegetableExchange);
                VegetableData vegetableData = (VegetableData) adapterView.getSelectedItem();
                mIntQuantityOfAccount = vegetableData.getQuantity();
                mStrVegetableNeedId = vegetableData.getId();
//                displaySpinnerVegetableNeed(vegetableData);
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

            }
        });
        mDlExchangeDetermineVegetable.setCanceledOnTouchOutside(false);
        mDlExchangeDetermineVegetable.show();
    }
//    display vegetable need
    public void displaySpinnerVegetableNeed(VegetableExchange vegetableExchange) {
        String needName = vegetableExchange.getVegetableExchangeName();
        String needId = vegetableExchange.getVegetableExchangeId();
        mStrVegetableNeedId = vegetableExchange.getVegetableExchangeName();
        mStrVegetableNeedName = vegetableExchange.getVegetableExchangeId();
//        createExchange();
    }
    /* show dialog donate any vegetables from the exchange  post*/
    private void openDialogShowAnyVegetable() {
        mDlExchangeAnyVegetable = new Dialog(getContext());
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

    /* create Exchange Any Vegetable */
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
            showDialogQuantityExchangeErr();
            return;
        }
//        else {
//            ExchangeRequest exchangeRequest = new ExchangeRequest(mIntExchangeQuantityReceive,
//                    mIntExchangeQuantityDonate, mShareIdOfShare, mStrVegetableNeedId);
//            ExchangeRequest exchangeRequest = new ExchangeRequest(mIntExchangeQuantityReceive, mIntExchangeQuantityDonate,
//                    mIntProvinceId, mIntDistrictId, mIntWardId, mStrSubAddress, mStrFullAddress, mShareIdOfShare, mStrVegetableNeedId);
//
//            System.out.println("1: " + mIntExchangeQuantityReceive);
//            System.out.println("2: " + mIntExchangeQuantityDonate);
//            System.out.println("3: " + mShareIdOfShare);
//            System.out.println("4: " + mStrVegetableNeedId);
//            mCreateExchangePresenter.createExchange(exchangeRequest, mUser.getToken());
//        }
        ExchangeRequest exchangeRequest = new ExchangeRequest(mIntExchangeQuantityReceive, mIntExchangeQuantityDonate,
                mIntProvinceId, mIntDistrictId, mIntWardId, mStrSubAddress, mStrFullAddress, mShareIdOfShare, mStrVegetableNeedId);

        System.out.println("1: " + mIntExchangeQuantityReceive);
        System.out.println("2: " + mIntExchangeQuantityDonate);
        System.out.println("3: " + mShareIdOfShare);
        System.out.println("4: " + mStrVegetableNeedId);
        mCreateExchangePresenter.createExchange(exchangeRequest, mUser.getToken());
    }



    /*dialog input info err */
    private void showDialogInputInfoErr() {
        final Dialog dialog = new Dialog(getContext());
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
    private void showDialogQuantityReceiveErr() {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_exchange_quantity_err);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        TextView txtQuantity;
        Button btnClose;
        btnClose = (Button) dialog.findViewById(R.id.btn_close);
        txtQuantity = (TextView) dialog.findViewById(R.id.txt_exchange_quantity);
        txtQuantity.setText("Số lượng nhận không lớn hơn " + mIntQuantityOfShare + " chậu");

        mIntExchangeQuantityDonate = 0;

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIntExchangeQuantityDonate = 0;
                dialog.dismiss();
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
    /*dialog input garden  zero */
    private void showDialogInputGardenZero() {
        final Dialog dialog = new Dialog(getContext());
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
    /*dialog quantity receive zero */
    private void showDialogInputReceiveZero() {
        final Dialog dialog = new Dialog(getContext());
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
    /*dialog quantity donate zero */
    private void showDialogInputDonateZero() {
        final Dialog dialog = new Dialog(getContext());
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
    /*show dialog choose Vegetable*/
    private void showDialogChooseVegetable() {
        final Dialog dialog = new Dialog(getContext());
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
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        mRecyclerViewGarden.addItemDecoration(itemDecoration);

        dialog.show();
    }
    /*show dialog choose garden*/
    private void showDialogChooseGarden() {
        final Dialog dialog = new Dialog(getContext());
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
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        mRecyclerViewGarden.addItemDecoration(itemDecoration);

        dialog.show();
    }
    /*dialog quantity receive */
    private void showDialogQuantityDonate() {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_exchange_quantity_err);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        TextView txtQuantity;
        Button btnClose;
        btnClose = (Button) dialog.findViewById(R.id.btn_close);
        txtQuantity = (TextView) dialog.findViewById(R.id.txt_exchange_quantity);
        txtQuantity.setText("Số lượng cho không lớn hơn " + mIntQuantityOfAccount + " Chậu");

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
    private void showDialogQuantityExchangeErr() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_exchange_quantity_err);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        TextView txtQuantity;
        Button btnClose;
        btnClose = (Button) dialog.findViewById(R.id.btn_close);
        txtQuantity = (TextView) dialog.findViewById(R.id.txt_exchange_quantity);
        txtQuantity.setText("Số lượng cho không lớn hơn " + mIntQuantityOfAccount + " Chậu");
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
    private void showDialogCheckVegetableNeedErr() {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_exchange_quantity_err);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        TextView txtQuantity;
        Button btnClose;
        btnClose = (Button) dialog.findViewById(R.id.btn_close);
        txtQuantity = (TextView) dialog.findViewById(R.id.txt_exchange_quantity);
//        txtQuantity.setText("Bạn không có: " + mStrVegetableNeedName );
        txtQuantity.setText("Bạn không có rau người dùng cần trao đổi");
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
    private void showDialogCreateExchangeSuccess() {
        final Dialog dialog = new Dialog(getContext());
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
                dialog.dismiss();
//                mDlExchangeAnyVegetable.dismiss();
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    public void clickOpenReportBottomSheet(PostData postData) {
        String accessToken = mUser.getToken();
        String accountId = mUser.getAccountId();
        ReportPostBottomSheetFragment reportPostBottomSheetFragment = new ReportPostBottomSheetFragment(postData, accessToken, accountId);
        reportPostBottomSheetFragment.show(getFragmentManager(), reportPostBottomSheetFragment.getTag());
    }

    private void openQRCodeScanner() {
        Intent intent = new Intent(getActivity().getApplicationContext(), QRCodeScannerActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        startActivity(intent);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lnl_search:
                Intent intent = new Intent(getActivity().getApplicationContext(), SearchActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                startActivity(intent);
                break;
            case R.id.lnl_qr_code:
                openQRCodeScanner();
                break;
        }
    }


    @Override
    public void clickBtnExchange(PostData postData) {
        mIntQuantityOfShare = postData.getQuantity();
        mShareIdOfShare = postData.getId();
        mAccountIdOfShare = postData.getAccountId();
        mVegetableIdOfShare = postData.getId();

        mListVegetableNeed = postData.getVegetableExchange();
        System.out.println("************************list vegetable exchange*************************");
        System.out.println("size: " + mListVegetableNeed.size());
        for (int i = 0; i < mListVegetableNeed.size(); i++) {
            System.out.println("id: " + mListVegetableNeed.get(i).getVegetableExchangeId());
            System.out.println("ten: " + mListVegetableNeed.get(i).getVegetableExchangeName());
        }
        System.out.println("************************list vegetable exchange*************************");
        if (mVegetableIdOfShare.isEmpty()) {
            mVegetableIdOfShare = "";
        }
        if (postData.getType() == 1) {
            openDialogShowShare();
        } else if(postData.getType() == 2) {
            if (postData.getVegetableExchange().size() == 0) {
                openDialogShowAnyVegetable();
            } else {
                checkVegetableNeed();
//                openDialogShowExchange();
            }

        }
    }
    /*check vegetable*/
    private void checkVegetableNeed(){
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
//        VegetableCheckIsExist vegetableCheckIsExist = new VegetableCheckIsExist(listVegetableExchangeTmp);
        mCheckVegetableOfAccountPresenter.CheckVegetableOfAccountPresenter(listVegetableExchangeTmp, mUser.getToken());
        System.out.println("list id vegetable exchange bbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
    }

    @Override
    public void clickPosterUser(PostData postData) {

        if (postData.getAccountId().equals(mAccountIdUser)) {
            System.out.println("AAAAAAAAAAAAAAAAAA");
//            BottomNavigationView.setOnNavigationItemSelectedListener();
            Fragment fragment = new PersonalFragment();
            loadFragment(fragment);
        } else {

            Intent intent = new Intent(getContext().getApplicationContext(), PosterProfileActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Bundle bundle = new Bundle();
            mStrNameOfShare = postData.getFullName();
            mAccountIdOfShare = postData.getAccountId();
            bundle.putString("ACCOUNT_ID", mAccountIdUser);
            bundle.putString("ACCOUNT_SHARE", mAccountIdOfShare);
            bundle.putString("NAME_SHARE", mStrNameOfShare);
            intent.putExtras(bundle);
//            intent.putExtra("OWNER_PROFILE", posterName);
            startActivity(intent);
        }

    }

    @Override
    public void clickReportPost(PostData postData) {
        mAccountIdUser = mUser.getAccountId();
        String accountIdiInShare = postData.getAccountId();
        mShareIdOfShare = postData.getId();
        clickOpenReportBottomSheet(postData);
    }

    @Override
    public void clickPostDetail(PostData postData) {
        System.out.println("XXXXXXXXX   XXXXXXXXXXXX    XXXXXXXXXXXXXX");
        System.out.println("post detail");
        System.out.println("XXXXXXXXX   XXXXXXXXXXXX    XXXXXXXXXXXXXX");
        Intent intent = new Intent(getContext().getApplicationContext(), PostDetailActivity.class);
        Bundle  bundle = new Bundle();
        bundle.putSerializable(KEY_BUNDLE_HOME, postData);
        intent.putExtras(bundle);
        startActivity(intent);
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
            Toast.makeText(mView.getContext(), phoneErr, Toast.LENGTH_SHORT).show();
        }
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void createExchangeSuccess(List<ExchangeData> exchangeData) {
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA createExchangeSuccess");
        System.out.println("createExchangeSuccess");
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA createExchangeSuccess");
        showDialogCreateExchangeSuccess();
        System.out.println("chay toi day roi");
//        if (mDlExchangeAnyVegetable != null) {
//            System.out.println("da chay vao toi dialog != null");
//            mDlExchangeAnyVegetable.dismiss();
//        }
        System.out.println("het line roi");
//        initialView();
    }

    @Override
    public void createExchangeFail() {
        System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
        System.out.println("createExchangeFail");
        System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
    }

    @Override
    public void checkVegetableOfAccountSuccess(List<VegetableData> vegetableData) {

        mListVegetable = vegetableData;
        if (mListVegetable.size() > 0) {
//            mIntQuantityOfAccount = vegetableData.get(0).getQuantity();
//            mStrVegetableNeedId = vegetableData.get(0).getId();
//            System.out.println("mIntExchangeQuantityDonate: " + mIntExchangeQuantityDonate);
//            System.out.println("mIntQuantityOfAccount: " + mIntQuantityOfAccount);
//
//            if (mIntExchangeQuantityDonate > mIntQuantityOfAccount) {
//                showDialogQuantityDonate();
//                return;
//            } else {
//                System.out.println("AAAAAAAAAAAAAAA chay api create exchange AAAAAAAAAAAAAAAAAA");
////                createExchangeDeterminedVegetable();
//                System.out.println("AAAAAAAAAAAAAAA chay api create exchange AAAAAAAAAAAAAAAAAA");
//            }
            openDialogShowExchange();
        }
//        System.out.println("SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS");
//        System.out.println("SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS");
//        System.out.println("************* co rau ***************************");
//        for (VegetableData x: vegetableData) {
//            System.out.println("ten");
//            System.out.println(x.getName());
//            System.out.println("***then ten ***");
//        }
//        System.out.println("SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS");
//        System.out.println("SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS");
    }

    @Override
    public void checkVegetableOfAccountFail(String msg) {
        showDialogCheckVegetableNeedErr();
    }

    @Override
    public void showInfoPersonal(User user) {
        mUser = user;

        mIntProvinceId = user.getProvinceId();
        mIntDistrictId = user.getDistrictId();
        mIntWardId = user.getWardId();
        mStrFullAddress = user.getAddress();
        if (!mStrFullAddress.equals("")) {
            String[] addressTmp = mStrFullAddress.split(",");
            mStrSubAddress = addressTmp[0];
        }

        mAccountIdUser = mUser.getAccountId();
        mAllSharePresenter.getAllShare(user.getToken());

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
    public void allShareSuccess(List<PostData> postData) {
        mPostList = postData;
        updateUI();
    }

    @Override
    public void allShareFail() {

    }
}
