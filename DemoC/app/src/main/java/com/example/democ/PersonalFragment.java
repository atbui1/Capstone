package com.example.democ;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.democ.activity.FriendActivity;
import com.example.democ.activity.HistoryExchangeActivity;
import com.example.democ.activity.LoginActivity;
import com.example.democ.activity.AddFriendRequestActivity;
import com.example.democ.activity.UpdateAccountActivity;
import com.example.democ.activity.UpdatePostActivity;
import com.example.democ.adapter.PostByAccountAdapter;
import com.example.democ.fragment.AccountEditPostBottomSheetFragment;
import com.example.democ.iclick.IClickPostAccount;
import com.example.democ.model.PostData;
import com.example.democ.presenters.DeleteSharePresenter;
import com.example.democ.presenters.GetAllShareByIdPresenter;
import com.example.democ.presenters.LogoutPresenter;
import com.example.democ.presenters.PersonalPresenter;
import com.example.democ.room.entities.User;
import com.example.democ.views.DeleteShareView;
import com.example.democ.views.GetAllShareByIdView;
import com.example.democ.views.LogoutView;
import com.example.democ.views.PersonalView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class PersonalFragment extends Fragment implements View.OnClickListener, LogoutView, PersonalView, GetAllShareByIdView,
        IClickPostAccount, DeleteShareView {

    private View mView;
    private LinearLayout mLnlImagePerson;
    private TextView mTxtFullNamePersonal, mTxtTotalPosts;
    private LogoutPresenter mLogoutPresenter;
    private PersonalPresenter mPersonalPresenter;
    private DeleteSharePresenter mDeleteSharePresenter;

    private User mUser;
    private static int mIntPostionDelete = 0;

    //11
    private RecyclerView mRecyclerViewPost;
    private ArrayList<PostData> mListPost;
    private PostByAccountAdapter mPostByAccountAdapter;
    private GetAllShareByIdPresenter mGetAllShareByIdPresenter;
    private int mIntTotalPosts;
    //11
    //22
    private DrawerLayout mDrawerLayout;
    private LinearLayout mLnlMenu, mLnlEditProfile, mLnlRequestAddFriend, mLnlLogout, mLnlHistoryExchange, mLnlFriend;
    //22

    private static MultipartBody.Part fileUpload;
    private static List<MultipartBody.Part> mListFile;
    private String mediaPath;
    private static String url;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.abc, container, false);
        initialView();
        initialData();

        return mView;
    }


    private void initialView() {
        mPersonalPresenter = new PersonalPresenter(getActivity().getApplication(), this);
        mPersonalPresenter.getInfoPersonal();
        mDeleteSharePresenter = new DeleteSharePresenter(getActivity().getApplication(), getActivity(), this);

        mLogoutPresenter = new LogoutPresenter(getActivity().getApplication(), getActivity(), this);

        //22
        mLnlImagePerson = (LinearLayout) mView.findViewById(R.id.lnl_image_person);
        mDrawerLayout = (DrawerLayout) mView.findViewById(R.id.drawer_layout);
        mLnlMenu = (LinearLayout) mView.findViewById(R.id.lnl_menu);
        mLnlMenu.setOnClickListener(this);
        mLnlRequestAddFriend = (LinearLayout) mView.findViewById(R.id.lnl_request_add_friend);
        mLnlRequestAddFriend.setOnClickListener(this);
        mLnlEditProfile = (LinearLayout) mView.findViewById(R.id.lnl_edit_profile);
        mLnlEditProfile.setOnClickListener(this);
        mLnlLogout = (LinearLayout) mView.findViewById(R.id.lnl_logout);
        mLnlLogout.setOnClickListener(this);
        mLnlHistoryExchange = (LinearLayout) mView.findViewById(R.id.lnl_history_exchange);
        mLnlHistoryExchange.setOnClickListener(this);
        mLnlFriend = (LinearLayout) mView.findViewById(R.id.lnl_friend);
        mLnlFriend.setOnClickListener(this);
        //22

        //11
        mGetAllShareByIdPresenter = new GetAllShareByIdPresenter(getActivity().getApplication(), getActivity(), this);
        mListPost = new ArrayList<>();
        //11

        mLnlImagePerson.setOnClickListener(this);
        mTxtFullNamePersonal = (TextView) mView.findViewById(R.id.txt_full_name_personal);

        //11
        mRecyclerViewPost = (RecyclerView) mView.findViewById(R.id.recycler_share_by_account);
        mRecyclerViewPost.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerViewPost.setLayoutManager(layoutManager);
        mRecyclerViewPost.setNestedScrollingEnabled(false);
        mIntTotalPosts = 0;
        mTxtTotalPosts = (TextView) mView.findViewById(R.id.txt_total_posts);
        mTxtTotalPosts.setText(mIntTotalPosts + "");
        //11

    }

    private void initialData() {
//        mPersonalPresenter.getInfoPersonal();
        //11
//        updateUI();
    }

    private void logoutApp() {
        mLogoutPresenter.deleteAccountRoom();
    }

    public void uploadImage() {
        mListFile = new ArrayList<>();
        File file = new File(mediaPath);

        // Parsing any Media type file
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        fileUpload = MultipartBody.Part.createFormData("files", file.getName(), requestBody);
        mListFile.add(fileUpload);
        String fileName = url.substring(23);
        if (!fileName.equals(file.getName())) {
//            mUploadImagePresenter.uploadImage(mListFile);
        } else {
//            updateRoom();
        }
    }

    public void clickOpenRequestAddFriend() {
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        Intent intent = new Intent(getActivity(), AddFriendRequestActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        startActivity(intent);
    }
    public void clickOpenUpdateAccount() {
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        Intent intent = new Intent(getActivity(), UpdateAccountActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        startActivity(intent);
    }
    public void clickOpenHistoryExchange() {
        Intent intent = new Intent(getActivity(), HistoryExchangeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        startActivity(intent);
    }
    public void clickOpenFriend() {
        Intent intent = new Intent(getActivity(), FriendActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        startActivity(intent);
    }

    public void updateUI() {
        if (mPostByAccountAdapter == null) {
//            mPostByAccountAdapter = new PostByAccountAdapter(mListPost, getContext().getApplicationContext());
            mPostByAccountAdapter = new PostByAccountAdapter(mListPost, getContext().getApplicationContext(), this);
            mRecyclerViewPost.setAdapter(mPostByAccountAdapter);
        } else {
            mPostByAccountAdapter.notifyDataSetChanged();
        }
    }

    private void clickOpenLeftMenu(PostData postData) {
        String token = mUser.getToken();
        AccountEditPostBottomSheetFragment accountEditPostBottomSheetFragment = new AccountEditPostBottomSheetFragment(postData, token);
//        accountEditPostBottomSheetFragment.show();

        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        System.out.println("a1: " + postData.getContent());
        System.out.println("b1: " + postData.getId());
        System.out.println("ab1 token: " + token);
        System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");

        accountEditPostBottomSheetFragment.show(getFragmentManager(), accountEditPostBottomSheetFragment.getTag());

    }
    private void clickOpenUpdatePost(PostData postData) {
        int maxSize = postData.getImageVegetablesList().size() -1;
        String urlImage = "";

        Intent intent = new Intent(getActivity(), UpdatePostActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        Bundle bundle = new Bundle();
        bundle.putString("POST_CONTENT", postData.getContent());
        if (postData.getImageVegetablesList().size() == 0) {
            urlImage = "";
        } else {
            urlImage = postData.getImageVegetablesList().get(maxSize).getUrl();
        }
        bundle.putString("POST_IMAGE", urlImage);
        bundle.putInt("POST_QUANTITY", postData.getQuantity());
        intent.putExtras(bundle);
        startActivity(intent);
    }
    private void showDialogDeletePost(final String posterId) {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_delete_garden);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        TextView txtContentDelete;
        Button btnDeleteYes, btnDeleteNo;
        btnDeleteYes = (Button) dialog.findViewById(R.id.btn_delete_yes);
        btnDeleteNo = (Button) dialog.findViewById(R.id.btn_delete_no);
        txtContentDelete = (TextView) dialog.findViewById(R.id.txt_content_delete);
        txtContentDelete.setText("Bạn có muốn xóa bài đăng này không");
        btnDeleteNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnDeleteYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("YYYYYYYYYYYYYYYYYYYYYYYYYYYYYY");
                System.out.println("goi api xoa bai post");
                System.out.println("token: " + mUser.getToken());
                System.out.println("postid: " + posterId);
                System.out.println("YYYYYYYYYYYYYYYYYYYYYYYYYYYYYY");
                mDeleteSharePresenter.deleteShare(posterId, mUser.getToken());
                dialog.dismiss();
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    private void showDialoDeleteErr() {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_exchange_quantity_err);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        TextView txtQuantity;
        Button btnClose;
        btnClose = (Button) dialog.findViewById(R.id.btn_close);
        txtQuantity = (TextView) dialog.findViewById(R.id.txt_exchange_quantity);
        txtQuantity.setText("Bài đăng đang có yêu cầu nhận rau");


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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lnl_image_person:
                Toast.makeText(view.getContext(),
                         " ahihi 22222222222222", Toast.LENGTH_SHORT)
                        .show();
                break;
                //DrawerLayout - navigationView
            case R.id.lnl_menu:
                mDrawerLayout.openDrawer(GravityCompat.END);
                break;
            case R.id.lnl_request_add_friend:
                clickOpenRequestAddFriend();
                break;
            case R.id.lnl_history_exchange:
                clickOpenHistoryExchange();
                break;
            case R.id.lnl_edit_profile:
                clickOpenUpdateAccount();
                break;
            case R.id.lnl_friend:
                clickOpenFriend();
                break;
            case R.id.lnl_logout:
                logoutApp();
                break;
        }
    }

    @Override
    public void logoutSuccess() {
        Intent intentLogin = new Intent(getActivity(), LoginActivity.class);
        intentLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intentLogin);
        getActivity().finish();
    }

    @Override
    public void logoutFail() {

    }

    @Override
    public void showInfoPersonal(User user) {

        mUser = user;

        mTxtFullNamePersonal.setText(user.getFullName());
        //11
        mGetAllShareByIdPresenter.getAllShareById(user.getAccountId(), user.getToken());
        //11
    }

    @Override
    public void getAllShareByIdSuccess(List<PostData> postDataList) {
        this.mListPost = (ArrayList<PostData>) postDataList;
        if (mListPost.size() > 0) {
            mIntTotalPosts = mListPost.size();
            mTxtTotalPosts.setText(String.valueOf(mIntTotalPosts));
            updateUI();
        }
    }

    @Override
    public void getAllShareByIdFail() {

    }

    @Override
    public void clickPostAccount(PostData postData) {
//        clickOpenLeftMenu(postData);
        clickOpenUpdatePost(postData);
    }

    @Override
    public void clickDeletePostAccount(PostData postData, int positionDelete) {
        mIntPostionDelete = positionDelete;
        showDialogDeletePost(postData.getId().trim());
        System.out.println("AAAAAAAAAAAAAAA     clickDeletePostAccount  AAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        System.out.println("AAAAAAAAAAAAAAA     clickDeletePostAccount  AAAAAAAAAAAAAAAAAAAAAAAAAAAA");
    }

    @Override
    public void clickCallPhone(PostData postData) {

    }

    @Override
    public void deleteShareSuccess() {
        mListPost.remove(mIntPostionDelete);
        mPostByAccountAdapter.notifyItemRemoved(mIntPostionDelete);
        System.out.println("BBBBBBBBBBBBBBBBBBB     deleteShareSuccess  BBBBBBBBBBBBBBBBBBB");
        System.out.println("BBBBBBBBBBBBBBB     deleteShareSuccess  BBBBBBBBBBBBBBB");
    }

    @Override
    public void deleteShareFail() {
        showDialoDeleteErr();
    }
}
