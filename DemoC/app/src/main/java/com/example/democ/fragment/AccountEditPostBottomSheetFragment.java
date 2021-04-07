package com.example.democ.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.democ.MessageFragment;
import com.example.democ.PersonalFragment;
import com.example.democ.R;
import com.example.democ.activity.AddFriendRequestActivity;
import com.example.democ.activity.MainActivity;
import com.example.democ.activity.UpdatePostActivity;
import com.example.democ.model.PostData;
import com.example.democ.presenters.DeleteSharePresenter;
import com.example.democ.views.DeleteShareView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


public class AccountEditPostBottomSheetFragment extends BottomSheetDialogFragment implements View.OnClickListener, DeleteShareView {

    private BottomSheetDialog mBottomSheetDialog;
    private PostData mPostData;
    private String mToken;
    private DeleteSharePresenter mDeleteSharePresenter;

    public AccountEditPostBottomSheetFragment(PostData mPostData, String mToken) {
        this.mPostData = mPostData;
        this.mToken = mToken;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        mBottomSheetDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_account_edit_post_bottom_sheet, null);
        mBottomSheetDialog.setContentView(view);

        //bat su kien
        LinearLayout lnlEdit = view.findViewById(R.id.lnl_post_edit);
        lnlEdit.setOnClickListener(this);
        LinearLayout lnlDelete = view.findViewById(R.id.lnl_post_delete);
        lnlDelete.setOnClickListener(this);

        mDeleteSharePresenter = new DeleteSharePresenter(getActivity().getApplication(), getActivity(), this);

        return mBottomSheetDialog;
    }

    private void showDialogDeletePost() {
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
                System.out.println("token: " + mToken);
//                new PersonalFragment();
//                Fragment fragment = new PersonalFragment();
//                loadFragment(fragment);
                System.out.println("YYYYYYYYYYYYYYYYYYYYYYYYYYYYYY");
//                mDeleteSharePresenter.deleteShare(mPostData.getId(), mToken);
                dialog.dismiss();
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void clickOpenUpdatePost() {
        int maxSize = mPostData.getImageVegetablesList().size() -1;
        String urlImage = "";

        Intent intent = new Intent(getActivity(), UpdatePostActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        Bundle bundle = new Bundle();
        bundle.putString("POST_CONTENT", mPostData.getContent());
        if (mPostData.getImageVegetablesList().size() == 0) {
            urlImage = "";
        } else {
            urlImage = mPostData.getImageVegetablesList().get(maxSize).getUrl();
        }
        bundle.putString("POST_IMAGE", urlImage);
        bundle.putInt("POST_QUANTITY", mPostData.getQuantity());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lnl_post_edit:
                System.out.println("AAAAAAAAAA AccountEditPostBottomSheetFragment AAAAAAAAAAAAAAA");
                System.out.println("post content: " + mPostData.getContent());
                System.out.println("post id: " + mPostData.getId());
                mBottomSheetDialog.dismiss();
                clickOpenUpdatePost();
                break;
            case R.id.lnl_post_delete:
                System.out.println("BBBBBBBBBBBBB   AccountEditPostBottomSheetFragment  BBBBBBBBBBBBBBBBBBBBBBB");
                System.out.println("post content: " + mPostData.getContent());
                System.out.println("post id: " + mPostData.getId());
                System.out.println("post full name: " + mPostData.getFullName());
                System.out.println("post account id: " + mPostData.getAccountId());
                System.out.println("post status: " + mPostData.getStatius());
                System.out.println("post quantity: " + mPostData.getQuantity());
                mBottomSheetDialog.dismiss();
                showDialogDeletePost();
                break;
        }
    }

    @Override
    public void deleteShareSuccess() {
        new PersonalFragment();
    }

    @Override
    public void deleteShareFail() {

    }
}
