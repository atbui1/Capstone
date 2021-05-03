package com.example.democ.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.democ.R;
import com.example.democ.activity.CreateVegetableActivity;
import com.example.democ.adapter.SearchNameAdapter;
import com.example.democ.iclick.IClickListTextWiki;
import com.example.democ.iclick.IClickVegetable;
import com.example.democ.iclick.IClickWikiTitle;
import com.example.democ.model.VegetableData;
import com.example.democ.model.WikiData;
import com.example.democ.model.WikiDataTitle;
import com.example.democ.presenters.GetDescriptionByWikiPresenter;
import com.example.democ.presenters.PersonalPresenter;
import com.example.democ.presenters.SearchByWikiTitlePresenter;
import com.example.democ.room.entities.User;
import com.example.democ.views.GetDescriptionByWikiView;
import com.example.democ.views.PersonalView;
import com.example.democ.views.SearchByWikiTitleView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchByNameBottomSheetFragment extends BottomSheetDialogFragment implements View.OnClickListener, SearchByWikiTitleView,
        GetDescriptionByWikiView, PersonalView {
    private ArrayList<VegetableData> mListVegetable;
    private String mStrSearchValue, mStrName, mStrDescription, mStrFeature, mStrLinkImage = "";;
    private IClickVegetable mIClickVegetable;

    private BottomSheetDialog mBottomSheetDialog;
    private SearchByWikiBottomSheetFragment mSearchByWikiBottomSheetFragment;
    private View mView;
    private TextView mTxtSearchWiki;
    private LinearLayout mLnlCloseBottom;
    private ArrayList<WikiDataTitle> mListWikiTitle;
    private ProgressDialog mProgressDialog;
    private User mUser;
    private PersonalPresenter mPersonalPresenter;
    private SearchByWikiTitlePresenter mSearchByWikiTitlePresenter;
    private GetDescriptionByWikiPresenter mGetDescriptionByWikiPresenter;
    private WikiData WikiData;



    public SearchByNameBottomSheetFragment(ArrayList<VegetableData> mListVegetable, String mStrSearchValue, IClickVegetable mIClickVegetable) {
        this.mListVegetable = mListVegetable;
        this.mStrSearchValue = mStrSearchValue;
        this.mIClickVegetable = mIClickVegetable;
    }
    /*interface listener button admit dialog*/
    IGetDataSearchWikiListener mIGetDataSearchWikiListener;
    IGetDataSearchWikiTitleListener mIGetDataSearchWikiTitleListener;
    public interface IGetDataSearchWikiListener {
        void getDataSearchWiki(String vegName, String vegDescription, String vegFeature, String vegLinkUrl);
    }
    public interface IGetDataSearchWikiTitleListener {
        void getDataSearchWikiTitle(String vegName, String vegLinkUrl, String vegDescription, String vegFeature);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mIGetDataSearchWikiListener = (IGetDataSearchWikiListener) getActivity();
        mIGetDataSearchWikiTitleListener = (IGetDataSearchWikiTitleListener) getActivity();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        mBottomSheetDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        mBottomSheetDialog = new BottomSheetDialog(getContext(), R.style.BottomSheetDialogTheme);
        mView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_search_by_name_bottom_sheet, null);
        mBottomSheetDialog.setContentView(mView);
        RecyclerView recyclerView = mView.findViewById(R.id.recycler_view_search_by_name_bottom_sheet);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        SearchNameAdapter searchNameAdapter = new SearchNameAdapter(mListVegetable, new IClickVegetable() {
            @Override
            public void clickVegetable(VegetableData vegetableData) {
                mIClickVegetable.clickVegetable(vegetableData);
//                bottomSheetDialog.dismiss();
            }
        });
        recyclerView.setAdapter(searchNameAdapter);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        mTxtSearchWiki = mView.findViewById(R.id.txt_search_wiki);
        mTxtSearchWiki.setOnClickListener(this);
        mLnlCloseBottom = mView.findViewById(R.id.lnl_close_bottom_sheet);
        mLnlCloseBottom.setOnClickListener(this);

        mPersonalPresenter = new PersonalPresenter(getActivity(), this);
        mPersonalPresenter.getInfoPersonal();
        mSearchByWikiTitlePresenter = new SearchByWikiTitlePresenter(getActivity().getApplication(), getActivity(), this);
        mGetDescriptionByWikiPresenter = new GetDescriptionByWikiPresenter(getActivity().getApplication(), getActivity(), this);


        return mBottomSheetDialog;
    }

    public void clickOpenListText(List<String> listText) {
        ListTextWikiBottomSheetFragment listTextWikiBottomSheetFragment = new ListTextWikiBottomSheetFragment(listText,
                new IClickListTextWiki() {
                    @Override
                    public void clickListText(String msg, int pos) {
                        System.out.println("MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM");
                        System.out.println("MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM");
                        System.out.println("list text: " + msg);
                        System.out.println("MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM");
                        System.out.println("MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM");

                        showDialogIntroVegetable(msg);
                    }
                });

//        listTextWikiBottomSheetFragment.setCancelable(false);
        listTextWikiBottomSheetFragment.show(getFragmentManager(), listTextWikiBottomSheetFragment.getTag());
    }
    /*dialog intro vegetable*/
    private void showDialogIntroVegetable(final String msgFeature) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_intro_vegetable);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);

        /*anh xa view*/
        TextView txtName, txtDescription, txtFeature, txtClose, txtAdmit;
        ImageView imgVegetable;
        Button btnClose, btnAdmit;
        txtName = (TextView) dialog.findViewById(R.id.txt_vegetable_name);
        txtDescription = (TextView) dialog.findViewById(R.id.txt_vegetable_description);
        txtFeature = (TextView) dialog.findViewById(R.id.txt_vegetable_feature);
        txtClose = (TextView) dialog.findViewById(R.id.txt_close);
        txtAdmit = (TextView) dialog.findViewById(R.id.txt_admit);
        imgVegetable = (ImageView) dialog.findViewById(R.id.img_create_vegetable);
        /**/
        mStrFeature = msgFeature;
        txtName.setText(mStrName);
        txtDescription.setText(mStrDescription);
        txtFeature.setText(msgFeature);
        if (mStrLinkImage.equals("")) {
            imgVegetable.setImageResource(R.mipmap.addimage64);
            mStrLinkImage = "";
        } else {
            Picasso.with(getActivity()).load("https:" + mStrLinkImage)
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.caybacha)
                    .into(imgVegetable);
        }

        txtAdmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mIGetDataSearchWikiListener.getDataSearchWiki(mStrName, mStrDescription, msgFeature, mStrLinkImage);
                mSearchByWikiBottomSheetFragment.dismiss();
                mBottomSheetDialog.dismiss();
                dialog.dismiss();
            }
        });
        txtClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
    /*dialog intro vegetable with name and image*/
    private void showDialogIntroVegetableWikiTitle() {
        final Dialog dialog = new Dialog(mBottomSheetDialog.getContext());
        dialog.setContentView(R.layout.dialog_intro_vegetable_wiki_title);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);

        /*anh xa view*/
        TextView txtName, txtDescription, txtFeature, txtClose, txtAdmit;
        ImageView imgVegetable;
        txtName = (TextView) dialog.findViewById(R.id.txt_vegetable_name);
        txtDescription = (TextView) dialog.findViewById(R.id.txt_vegetable_description);
        txtFeature = (TextView) dialog.findViewById(R.id.txt_vegetable_feature);
        txtClose = (TextView) dialog.findViewById(R.id.txt_close);
        txtAdmit = (TextView) dialog.findViewById(R.id.txt_admit);
        imgVegetable = (ImageView) dialog.findViewById(R.id.img_create_vegetable);
        /**/
        txtName.setText(mStrName);
        txtDescription.setText(mStrDescription);
        txtFeature.setText(mStrFeature);
        if (mStrLinkImage.equals("")) {
            imgVegetable.setImageResource(R.mipmap.addimage64);
            mStrLinkImage = "";
        } else {
            Picasso.with(getActivity()).load("https:" + mStrLinkImage)
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.caybacha)
                    .into(imgVegetable);
        }

        txtAdmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mIGetDataSearchWikiTitleListener.getDataSearchWikiTitle(mStrName, mStrLinkImage, mStrDescription, mStrFeature);
                mSearchByWikiBottomSheetFragment.dismiss();
                mBottomSheetDialog.dismiss();
                dialog.dismiss();
            }
        });
        txtClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
    /*dialog search description err*/
    private  void showDialogGetDescriptionErr() {
        final Dialog dialog = new Dialog(mBottomSheetDialog.getContext());
        dialog.setContentView(R.layout.dialog_login_fail);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);

        TextView txtDetail;
        Button btnOk;
        txtDetail = (TextView) dialog.findViewById(R.id.txt_detail_err);
        btnOk = (Button) dialog.findViewById(R.id.btn_close);
        txtDetail.setText("Không tìm thấy thông tin, vui lòng thử lại");
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_search_wiki:
                System.out.println("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
                System.out.println("goi bottom sheet search wiki: " + mStrSearchValue);
                System.out.println("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
                mSearchByWikiTitlePresenter.searchByWikiTitle(mStrSearchValue, mUser.getToken());
                mProgressDialog = ProgressDialog.show(getActivity(), "Vui lòng chờ",
                        "Đang tìm kiếm thông tin...!", true);
                break;
            case R.id.lnl_close_bottom_sheet:
                mBottomSheetDialog.dismiss();
                break;
        }
    }

    @Override
    public void searchByWikiTitleSuccess(List<WikiDataTitle> wikiDataTitles) {
        mProgressDialog.dismiss();
        mListWikiTitle = (ArrayList<WikiDataTitle>) wikiDataTitles;
        mSearchByWikiBottomSheetFragment =
                new SearchByWikiBottomSheetFragment(mListWikiTitle, new IClickWikiTitle() {
                    @Override
                    public void clickWikiTitle(WikiDataTitle wikiDataTitle) {

                        if (wikiDataTitle.getImage().equals("")) {
                            mStrLinkImage = "";
                        } else {
                            mStrLinkImage = wikiDataTitle.getImage().trim();
                        }
                        mStrName = wikiDataTitle.getTitle();
                        mGetDescriptionByWikiPresenter.getDescriptionByWiki(wikiDataTitle.getTitle(), mUser.getToken());
                    }
                });
        mSearchByWikiBottomSheetFragment.show(getFragmentManager(), mSearchByWikiBottomSheetFragment.getTag());
    }

    @Override
    public void searchByWikiTitleFail() {
        showDialogGetDescriptionErr();
    }

    @Override
    public void showInfoPersonal(User user) {
        mUser = user;
    }

    @Override
    public void getDescriptionByWikiSuccess(WikiData wikiData) {
//        WikiData = wikiData;
        if (wikiData.getListText().size() == 0) {
            System.out.println("KKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK");
            System.out.println("show dialog name and image");
            System.out.println("link image wiki: " + mStrLinkImage);
            mStrDescription = wikiData.getDescription();
            mStrFeature = wikiData.getFeature();
            showDialogIntroVegetableWikiTitle();
            System.out.println("KKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK");
        } else {
            mStrDescription = wikiData.getDescription();
            List<String> listTextWikis = wikiData.getListText();
            clickOpenListText(listTextWikis);

            System.out.println("HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH");
            System.out.println("show bottom sheet search full wiki");
            System.out.println("link image wiki: " + mStrLinkImage);
            System.out.println("HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH");
        }
    }

    @Override
    public void getDescriptionByWikiFail() {
        showDialogGetDescriptionErr();
    }
}
