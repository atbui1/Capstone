package com.example.democ;

import android.app.Dialog;
import android.content.Intent;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.democ.activity.CreatePostActivity;
import com.example.democ.activity.PostDetailActivity;
import com.example.democ.activity.PosterProfileActivity;
import com.example.democ.activity.SearchActivity;
import com.example.democ.adapter.PostAdapter;
import com.example.democ.fragment.ReportPostBottomSheetFragment;
import com.example.democ.iclick.IClickPost;
import com.example.democ.model.ExchangeData;
import com.example.democ.model.ExchangeRequest;
import com.example.democ.model.Garden;
import com.example.democ.model.GardenResult;
import com.example.democ.model.Post;
import com.example.democ.model.PostData;
import com.example.democ.model.VegetableData;
import com.example.democ.model.VegetableShare;
import com.example.democ.presenters.AllGardenPresenter;
import com.example.democ.presenters.AllVegetableByGardenIdPresenter;
import com.example.democ.presenters.CheckVegetableOfAccountPresenter;
import com.example.democ.presenters.CreateExchangePresenter;
import com.example.democ.presenters.PersonalPresenter;
import com.example.democ.presenters.ReportPostPresenter;
import com.example.democ.room.entities.User;
import com.example.democ.room.managements.UserManagement;
import com.example.democ.views.AllGardenView;
import com.example.democ.views.AllVegetableByGardenIdView;
import com.example.democ.views.CheckVegetableOfAccountView;
import com.example.democ.views.CreateExchangeView;
import com.example.democ.views.PersonalView;
import com.example.democ.views.ReportPostView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;


public class DiaryFragment extends Fragment implements View.OnClickListener, IClickPost, CreateExchangeView, CheckVegetableOfAccountView,
        PersonalView, AllGardenView, AllVegetableByGardenIdView {

    private final static String KEY_BUNDLE_HOME = "KEY_BUNDLE_HOME";
    private Button mBtnCreatePost;
    private LinearLayout mLnlSearch;

    private View mView;
    private RecyclerView mRecyclerPost;
    private List<PostData> mPostList;
    private List<ExchangeData> mExchangeList;
    private PostAdapter mPostAdapter;
    private String mAccessToken, mAccessTokenAAA;

    //new
    private User mUser;
    int mIntExchangeQuantityDonate = 0;
    int mIntExchangeQuantityReceive = 0;
    int mIntQuantityOfShare = 0;
    int mIntQuantityOfAccount = 0;
    String mStrVegetableNeedId = "";
    String mStrVegetableNeedName = "";
    String mShareIdOfShare, mAccountIdUser, mAccountIdOfShare, mVegetableIdOfShare, mStrNameOfShare = "";
    private UserManagement mUserManagement;
    private CreateExchangePresenter mCreateExchangePresenter;
    private CheckVegetableOfAccountPresenter mCheckVegetableOfAccountPresenter;
    private PersonalPresenter mPersonalPresenter;
    private AllGardenPresenter mAllGardenPresenter;
    private AllVegetableByGardenIdPresenter mAllVegetableByGardenIdPresenter;

    /* Spinner*/
    private Spinner mSpGarden, mSpVegetable, mSpVegetableNedd;
    private List<GardenResult> mListGarden;
    private List<VegetableData> mListVegetable;
    private List<VegetableShare> mListVegetableNeed;


    public DiaryFragment(List<PostData> mPostList, String mAccessToken, User mUser) {
        this.mPostList = mPostList;
        this.mAccessToken = mAccessToken;
        this.mUser = mUser;
    }

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

        mBtnCreatePost = (Button) mView.findViewById(R.id.btn_create_post);
        mBtnCreatePost.setOnClickListener(this);
        mLnlSearch = (LinearLayout) mView.findViewById(R.id.lnl_search);
        mLnlSearch.setOnClickListener(this);

        mRecyclerPost = mView.findViewById(R.id.recycler_diary);
        mRecyclerPost.setHasFixedSize(true);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),
//                LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerPost.setLayoutManager(layoutManager);

        mUserManagement = new UserManagement(getContext());
        mCreateExchangePresenter = new CreateExchangePresenter(getActivity().getApplication(), getActivity(), this);
        mCheckVegetableOfAccountPresenter = new CheckVegetableOfAccountPresenter(getActivity().getApplication(), getActivity(), this);
        mAllGardenPresenter = new AllGardenPresenter(getActivity().getApplication(), getActivity(), this);
        mAllVegetableByGardenIdPresenter = new AllVegetableByGardenIdPresenter(getActivity().getApplication(), getActivity(), this);


        /* Spinner */
        mListGarden = new ArrayList<>();
        mListVegetable = new ArrayList<>();
        mListVegetableNeed = new ArrayList<>();
    }

    private void initialData() {
        updateUI();
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
    private void showDialogQuantityShare() {
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
                        mIntExchangeQuantityDonate = 0;
                    } else {
                        mIntExchangeQuantityDonate = Integer.parseInt(edtQuantity.getText().toString());
                    }
                    int status = 1;
                    System.out.println("thuc hien goi api exchange showDialogQuantity");
                    System.out.println("exchange quantity: " + mIntExchangeQuantityDonate);
                    System.out.println("share quantity: " + mIntQuantityOfShare);
                    System.out.println("status: " + status);
                    System.out.println("shareid: " + mShareIdOfShare);
                    System.out.println("receivedBy: " + mAccountIdUser);
                    System.out.println("accessToken: " + mAccessToken);
                    System.out.println("ket thuc goi api exchange showDialogQuantity");
                    String aabaa = "";
                    if (mIntExchangeQuantityDonate > mIntQuantityOfShare || mIntExchangeQuantityDonate < 1) {
                        showDialogQuantityErr();
                        System.out.println("chay vao if showDialogQuantityErr");
                    } else {
                        // goi api exchange
                        ExchangeRequest exchangeRequest = new ExchangeRequest(mIntExchangeQuantityDonate, 0, mShareIdOfShare, aabaa);
                        System.out.println("xxxxxxxxxxxxxxxxxSHARExxxxxxxxxxxxxxxxxx");
                        System.out.println("chay vao if showDialogQuantity");
                        System.out.println("mExchangeQuantity " + mIntExchangeQuantityDonate);
                        System.out.println("mShareIdOfShare " + mShareIdOfShare);
                        System.out.println("aaaa: " + aabaa);
                        System.out.println("xxxxxxxxxxxxxxxxxSHARExxxxxxxxxxxxxxxxxx");
                        mCreateExchangePresenter.createExchange(exchangeRequest, mAccessTokenAAA);
                    }

                    System.out.println("showDialogQuantity XXXXXXXXXXXXXXXXXx showDialogQuantity");


                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                }
                dialog.dismiss();
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
//    dialog quantity exchange
    private void showDialogQuantityExchange() {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_exchange_2_quantity);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        final EditText edtQuantityDonate, edtQuantityReceive;
        Button btnOk, btnClose;
        edtQuantityReceive = (EditText) dialog.findViewById(R.id.edt_quantity_receive);
        edtQuantityDonate = (EditText) dialog.findViewById(R.id.edt_quantity_donate);
        btnOk = (Button) dialog.findViewById(R.id.btn_ok);
        btnClose = (Button) dialog.findViewById(R.id.btn_close);

        /* spinner*/
        ArrayAdapter<VegetableShare> adapterVegetableNeed = new ArrayAdapter<VegetableShare>(getContext(),
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
                    int status = 2;
                    System.out.println("thuc hien goi api exchange");
                    System.out.println("exchange quantity: " + mIntExchangeQuantityDonate);
                    System.out.println("status: " + status);
                    System.out.println("shareid: " + mShareIdOfShare);
                    System.out.println("receivedBy: " + mAccountIdUser);
                    System.out.println("accessToken: " + mAccessToken);
                    System.out.println("ket thuc goi api exchange");
                    String aabaa = "";
                    if (mIntExchangeQuantityReceive < mIntQuantityOfAccount) {
                        showDialogQuantityErr();
                    } else if (mIntExchangeQuantityDonate > mIntQuantityOfShare) {
                        showDialogQuantityErr();
                    } else {
                        // goi api exchange
                        ExchangeRequest exchangeRequest = new ExchangeRequest(mIntExchangeQuantityDonate,
                                mIntExchangeQuantityReceive, mShareIdOfShare, mStrVegetableNeedId);
                        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
                        System.out.println("mExchangeQuantity " + mIntExchangeQuantityDonate);
                        System.out.println("mIntExchangeQuantityReceive: " + mIntExchangeQuantityReceive);
                        System.out.println("mShareIdOfShare " + mShareIdOfShare);
                        System.out.println("mStrVegetableNeedId: " + mStrVegetableNeedId);
                        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
//                    mCreateExchangePresenter.createExchange(exchangeRequest, mAccessToken);
                    }

                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                }

                mCheckVegetableOfAccountPresenter.CheckVegetableOfAccountPresenter(mStrVegetableNeedId, mStrVegetableNeedName, mAccessTokenAAA);
                dialog.dismiss();
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
//    display vegetable need
    public void displaySpinnerVegetableNeed(VegetableShare vegetableShare) {
        String needName = vegetableShare.getVegetableShareName();
        String needId = vegetableShare.getVegetableShareId();
        mStrVegetableNeedId = vegetableShare.getVegetableShareId();
        mStrVegetableNeedName = vegetableShare.getVegetableShareName();
//        createExchange();
    }
//    dialog exchange any vegetables
    private void showDialogAnyVegetable() {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_exchange_any_vegetable);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        final EditText edtQuantityDonate, edtQuantityReceive;
        Button btnOk, btnClose;
        edtQuantityDonate = dialog.findViewById(R.id.edt_quantity_donate);
        edtQuantityReceive = dialog.findViewById(R.id.edt_quantity_receive);

        btnClose = dialog.findViewById(R.id.btn_close);
        btnOk = dialog.findViewById(R.id.btn_ok);
        /*adapterGarden*/
        ArrayAdapter<GardenResult> adapterGarden = new ArrayAdapter<GardenResult>(getContext(),
                android.R.layout.simple_spinner_item, mListGarden);
        adapterGarden.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        /*adapterVegetable*/
        ArrayAdapter<VegetableData> adapterVegetable = new ArrayAdapter<VegetableData>(getContext(),
                android.R.layout.simple_spinner_item, mListVegetable);
        adapterGarden.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mSpGarden = dialog.findViewById(R.id.sp_garden);
        mSpVegetable = dialog.findViewById(R.id.sp_vegetable);
        mSpGarden.setAdapter(adapterGarden);
        mSpVegetable.setAdapter(adapterVegetable);
        mSpGarden.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                GardenResult gardenResult = (GardenResult) adapterView.getSelectedItem();
                displaySpinnerGarden(gardenResult);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mSpVegetable.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                VegetableData vegetableData = (VegetableData) adapterView.getSelectedItem();
                displaySpinnerVegetable(vegetableData);
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
                int quantityDonate = 0, quantityReceive = 0;
                try {
                    if (edtQuantityDonate.getText().equals("")) {
                        quantityDonate = 0;
                    } else {
                        quantityDonate = Integer.parseInt(edtQuantityDonate.getText().toString().trim());
                    }
                    if (edtQuantityReceive.getText().equals("")) {
                        quantityReceive = 0;
                    } else {
                        quantityReceive = Integer.parseInt(edtQuantityReceive.getText().toString().trim());
                    }
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                }
                mIntExchangeQuantityDonate = quantityDonate;
                mIntExchangeQuantityReceive = quantityReceive;
                createExchangeAnyVegetable();
                dialog.dismiss();
            }
        });

        dialog.show();
    }
    public void displaySpinnerGarden(GardenResult gardenResult) {
        String name = gardenResult.getName();
        int gardenId = gardenResult.getId();
        mAllVegetableByGardenIdPresenter.getAllVegetableByGardenId(gardenId, mAccessTokenAAA);
    }
    public void displaySpinnerVegetable(VegetableData vegetableData) {
        String name = vegetableData.getName();
        mStrVegetableNeedId = vegetableData.getId();
        mIntQuantityOfAccount = vegetableData.getQuantity();
        System.out.println("KKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK");
        System.out.println("vegetablenameabc: " + name);
        System.out.println("vegetableidabc: " + mStrVegetableNeedId);
        System.out.println("mIntQuantityOfAccountabcid: " + mIntQuantityOfAccount);
        System.out.println("KKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK");
    }

    public void createExchangeAnyVegetable() {
        int status = 2;

        if (mIntExchangeQuantityReceive > mIntQuantityOfShare || mIntExchangeQuantityReceive < 1) {
            System.out.println("chay vao if mIntExchangeQuantityReceive > mIntQuantityOfShare");
            showDialogQuantityErr();
        } else if (mIntExchangeQuantityDonate > mIntQuantityOfAccount || mIntExchangeQuantityDonate < 1) {
            System.out.println("chay vao else if mIntExchangeQuantityDonate > mIntQuantityOfAccount");
            showDialogQuantityExchangeErr();
        } else {
            ExchangeRequest exchangeRequest = new ExchangeRequest(mIntExchangeQuantityDonate,
                    mIntExchangeQuantityReceive, mShareIdOfShare, mStrVegetableNeedId);
            System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
            System.out.println("mExchangeQuantity donate " + mIntExchangeQuantityDonate);
            System.out.println("mExchangeQuantity receive " + mIntExchangeQuantityReceive);
            System.out.println("mShareIdOfShare " + mShareIdOfShare);
            System.out.println("veget table id need any vegetable: " + mStrVegetableNeedId);
            System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");

            mCreateExchangePresenter.createExchange(exchangeRequest, mAccessTokenAAA);
        }
        System.out.println("ket thuc goi api exchange createExchange");
    }
    //create exchange with vegetable need detail
    public void createExchange() {
//        int status = 2;
//        System.out.println("thuc hien goi api exchange createExchange");
//        System.out.println("exchange quantity: " + mIntExchangeQuantityDonate);
//        System.out.println("status: " + status);
//        System.out.println("shareid: " + mShareIdOfShare);
//        System.out.println("receivedBy: " + mAccountIdUser);
//        System.out.println("accessToken: " + mAccessToken);
//        String aabaa = "";
//        System.out.println("mIntExchangeQuantityReceive: " + mIntExchangeQuantityReceive);
//        System.out.println("mIntExchangeQuantityDonate: " + mIntExchangeQuantityDonate);
//        System.out.println("mIntQuantityOfAccount: " + mIntQuantityOfAccount);
//        System.out.println("mIntQuantityOfShare: " + mIntQuantityOfShare);
//        if (mIntExchangeQuantityReceive > mIntQuantityOfShare || mIntExchangeQuantityReceive < 1) {
//            System.out.println("chay vao if mIntExchangeQuantityReceive > mIntQuantityOfShare");
//            showDialogQuantityErr();
//        } else if (mIntExchangeQuantityDonate > mIntQuantityOfAccount || mIntExchangeQuantityDonate < 1) {
//            System.out.println("chay vao else if mIntExchangeQuantityDonate > mIntQuantityOfAccount");
//            showDialogQuantityExchangeErr();
//        } else {
//            // goi api exchange
////            ExchangeRequest exchangeRequest = new ExchangeRequest(mIntExchangeQuantityDonate, mIntExchangeQuantityReceive, mShareIdOfShare, aabaa);
//            ExchangeRequest exchangeRequest = new ExchangeRequest(mIntExchangeQuantityDonate,
//                    mIntExchangeQuantityReceive, mShareIdOfShare, mStrVegetableNeedId);
//            System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
//            System.out.println("mExchangeQuantity " + mIntExchangeQuantityDonate);
//            System.out.println("mIntExchangeQuantityDonate: " + mIntExchangeQuantityDonate);
//            System.out.println("mShareIdOfShare " + mShareIdOfShare);
//            System.out.println("veget table id need any vegetable: " + mStrVegetableNeedId);
//            System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
//
//            mCreateExchangePresenter.createExchange(exchangeRequest, mAccessToken);
//        }
//        System.out.println("ket thuc goi api exchange createExchange");
        ExchangeRequest exchangeRequest = new ExchangeRequest(mIntExchangeQuantityDonate,
                mIntExchangeQuantityReceive, mShareIdOfShare, mStrVegetableNeedId);
        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        System.out.println("mExchangeQuantity " + mIntExchangeQuantityDonate);
        System.out.println("mIntExchangeQuantityDonate: " + mIntExchangeQuantityDonate);
        System.out.println("mShareIdOfShare " + mShareIdOfShare);
        System.out.println("veget table id need any vegetable: " + mStrVegetableNeedId);
        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");

        mCreateExchangePresenter.createExchange(exchangeRequest, mAccessTokenAAA);
    }

    private void showDialogQuantityErr() {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_exchange_quantity_err);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        TextView txtQuantity;
        Button btnClose;
        btnClose = (Button) dialog.findViewById(R.id.btn_close);
        txtQuantity = (TextView) dialog.findViewById(R.id.txt_exchange_quantity);
        txtQuantity.setText("Số lượng nhận không lớn hơn " + mIntQuantityOfShare + " cây");

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
    private void showDialogQuantityExchangeErr() {
        final Dialog dialog = new Dialog(getActivity());
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
    private void showDialogCheckVegetableNeedErr() {
        final Dialog dialog = new Dialog(getContext());
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
    private void showDialogCreateExchangeSuccess() {
        final Dialog dialog = new Dialog(getContext());
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
                dialog.dismiss();
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_create_post:
                Intent intentCreatePost = new Intent(getActivity().getApplicationContext(), CreatePostActivity.class);
                startActivity(intentCreatePost);
                break;
            case R.id.lnl_search:
                Intent intentSearch1 = new Intent(getActivity().getApplicationContext(), SearchActivity.class);
                startActivity(intentSearch1);
                break;
        }
    }


    @Override
    public void clickBtnExchange(PostData shareData) {
        Toast.makeText(getContext(), "position: "
                + "\n shareId: " + shareData.getId(), Toast.LENGTH_SHORT).show();
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA clickBtnExchange");
        mIntQuantityOfShare = shareData.getQuantity();
        mShareIdOfShare = shareData.getId();
        mAccountIdOfShare = shareData.getAccountId();
        mVegetableIdOfShare = shareData.getId();
//        mStrVegetableNeedId = shareData.getVegetableNeedId();
//        mStrVegetableNeedName = shareData.getVegetableNeedName();
        //
        mListVegetableNeed = shareData.getVegetableShareList();
        //

        System.out.println("shareId: " + shareData.getId());
        System.out.println("quantity: " + mIntQuantityOfShare);
        System.out.println("Quantity input: " + mIntExchangeQuantityDonate);
        System.out.println("AccountId of share: " + shareData.getAccountId());
        System.out.println("AccountId of user: " + mAccountIdUser);
        System.out.println("mStrVegetableNeedName " + mStrVegetableNeedName);
        System.out.println("mStrVegetableNeedId " + mStrVegetableNeedId);
        System.out.println("mVegetableIdOfShare: " + mVegetableIdOfShare);
        if (mVegetableIdOfShare.isEmpty()) {
            mVegetableIdOfShare = "";
        }
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA clickBtnExchange");
        /* */
        if (shareData.getStatius() == 1) {
            showDialogQuantityShare();
        } else if(shareData.getStatius() == 2) {
            if (shareData.getVegetableShareList().size() == 0) {
                showDialogAnyVegetable();
            } else {
                System.out.println("QQQQQQQQQQQQQQAAAAAAAAAAAAAAAAAAQQQQQQQQQQQQQQQQQQQQQ");
                System.out.println("AAAAAAAAAAAListNeed: " + mListVegetableNeed.size());
                System.out.println("AAAAAAAAAAAListNeed: " + mListVegetableNeed);
                System.out.println("QQQQQQQQQQQQQQAAAAAAAAAAAAAAAAAAQQQQQQQQQQQQQQQQQQQQQ");
                showDialogQuantityExchange();
            }
//            showDialogQuantityExchange();
        }
        /* */
    }

    @Override
    public void clickPosterUser(PostData shareData) {

        if (shareData.getAccountId().equals(mAccountIdUser)) {
            System.out.println("AAAAAAAAAAAAAAAAAA");
//            BottomNavigationView.setOnNavigationItemSelectedListener();
            Fragment fragment = new PersonalFragment();
            loadFragment(fragment);
        } else {

            Intent intent = new Intent(getContext().getApplicationContext(), PosterProfileActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            Bundle bundle = new Bundle();
            mStrNameOfShare = shareData.getFullName();
            mAccountIdOfShare = shareData.getAccountId();
            bundle.putString("ACCOUNT_ID", mAccountIdUser);
            bundle.putString("ACCOUNT_SHARE", mAccountIdOfShare);
            bundle.putString("NAME_SHARE", mStrNameOfShare);
            intent.putExtras(bundle);
//            intent.putExtra("OWNER_PROFILE", posterName);
            startActivity(intent);
        }

    }

    @Override
    public void clickReportPost(PostData shareData) {
        mAccountIdUser = mUser.getAccountId();
        String accountIdiInShare = shareData.getAccountId();
        mShareIdOfShare = shareData.getId();
        System.out.println("RRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR");
        System.out.println("RRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR");
        System.out.println("account id: " + mAccountIdUser);
        System.out.println("accountIdiInShare: " + accountIdiInShare);
        System.out.println("share id: " + mShareIdOfShare);
        System.out.println("RRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR");
        System.out.println("RRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR");
        clickOpenReportBottomSheet(shareData);
    }

    @Override
    public void clickPostDetail(PostData shareData) {
        System.out.println("XXXXXXXXX   XXXXXXXXXXXX    XXXXXXXXXXXXXX");
        System.out.println("post detail");
        System.out.println("XXXXXXXXX   XXXXXXXXXXXX    XXXXXXXXXXXXXX");
        Intent intent = new Intent(getContext().getApplicationContext(), PostDetailActivity.class);
        Bundle  bundle = new Bundle();
        bundle.putSerializable(KEY_BUNDLE_HOME, shareData);
        intent.putExtras(bundle);
        startActivity(intent);
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
        initialView();
    }

    @Override
    public void createExchangeFail() {
        System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
        System.out.println("createExchangeFail");
        System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
    }

    @Override
    public void checkVegetableOfAccountSuccess(List<VegetableData> vegetableData) {
        /*
        System.out.println("LLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL");
        System.out.println("LLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL");
        System.out.println("co rau");
        System.out.println("mIntQuantityOfAccount: " + vegetableData.get(0).getQuantity());
        mIntQuantityOfAccount = vegetableData.get(0).getQuantity();
        createExchange();
        System.out.println("LLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL");
        System.out.println("LLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL");
        */

        System.out.println("LLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLl");
        System.out.println("LLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLl");
        System.out.println("co rau");
        Toast.makeText(getContext(), "co rau " + mStrVegetableNeedName, Toast.LENGTH_SHORT).show();
        mStrVegetableNeedId = vegetableData.get(0).getId();
        createExchange();
        System.out.println("LLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLl");
        System.out.println("LLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLl");
    }

    @Override
    public void checkVegetableOfAccountFail() {
        System.out.println("NNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN");
        System.out.println("NNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN");
        System.out.println("khong co rau");
        showDialogCheckVegetableNeedErr();
        Toast.makeText(getContext(), "kkkkk rau " + mStrVegetableNeedName, Toast.LENGTH_SHORT).show();
        System.out.println("NNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN");
        System.out.println("NNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN");
    }

    @Override
    public void showInfoPersonal(User user) {
        mUser = user;
        mAccountIdUser = mUser.getAccountId();

        /* */
        mAccessTokenAAA = user.getToken();
        mAllGardenPresenter.getAllGarden(user.getToken());
    }

    @Override
    public void getAllGardenSuccess(List<GardenResult> listAllGarden) {
        mListGarden = new ArrayList<>();
        mListGarden = listAllGarden;
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        System.out.println("list garden: " + mListGarden.size());
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
    }

    @Override
    public void getAllGardenFail() {

    }

    @Override
    public void getAllVegetableByGardenIdSuccess(List<VegetableData> vegetableData) {
        mListVegetable = vegetableData;
        System.out.println("KKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK");
        System.out.println("list vegetable: " + mListVegetable.size());
        System.out.println("KKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK");
    }

    @Override
    public void getAllVegetableByGardenIdFail() {

    }

}
