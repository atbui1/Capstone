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
import com.example.democ.adapter.PostByAccountAdapter;
import com.example.democ.model.PostData;
import com.example.democ.model.PostSearchDescription;
import com.example.democ.model.PostSearchKeyword;
import com.example.democ.model.PostSearchName;
import com.example.democ.presenters.DeleteSharePresenter;
import com.example.democ.views.DeleteShareView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


public class AccountEditPostBottomSheetFragment extends BottomSheetDialogFragment implements View.OnClickListener, DeleteShareView {

    private BottomSheetDialog mBottomSheetDialog;
    private PostData mPostData;
    private PostSearchDescription mPostSearchDescription;
    private PostSearchKeyword mPostSearchKeyword;
    private PostSearchName mPostSearchName;
    private String mToken;
    private DeleteSharePresenter mDeleteSharePresenter;

    public AccountEditPostBottomSheetFragment(PostData mPostData, String mToken) {
        this.mPostData = mPostData;
        this.mToken = mToken;
    }

    public AccountEditPostBottomSheetFragment(PostSearchDescription mPostSearchDescription, String mToken) {
        this.mPostSearchDescription = mPostSearchDescription;
        this.mToken = mToken;
    }

    public AccountEditPostBottomSheetFragment(PostSearchKeyword mPostSearchKeyword, String mToken) {
        this.mPostSearchKeyword = mPostSearchKeyword;
        this.mToken = mToken;
    }

    public AccountEditPostBottomSheetFragment(PostSearchName mPostSearchName, String mToken) {
        this.mPostSearchName = mPostSearchName;
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

    /*event delete share handle*/
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

        String shareId = "";
        if (mPostData != null) {
            shareId = mPostData.getId().trim();
        } else if (mPostSearchName != null) {
            shareId = mPostSearchName.getId().trim();
        } else if (mPostSearchDescription != null) {
            shareId = mPostSearchDescription.getId().trim();
        } else if (mPostSearchKeyword != null) {
            shareId = mPostSearchKeyword.getId();
        }
        final String finalShareId = shareId;

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
                System.out.println("shareId: " + finalShareId);
                System.out.println("YYYYYYYYYYYYYYYYYYYYYYYYYYYYYY");
                mDeleteSharePresenter.deleteShare(finalShareId, mToken);
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

    /*event update*/
    public void clickOpenUpdatePost() {
        int maxSize = 0, quantity = 0;
        String urlImage = "", postContent = "";
        if (mPostData != null) {
            maxSize = mPostData.getImageVegetablesList().size() -1;
            quantity = mPostData.getQuantity();
            postContent = mPostData.getContent();
            try {
                if (mPostData.getImageVegetablesList().size() == 0) {
                    urlImage = "";
                } else {
                    urlImage = mPostData.getImageVegetablesList().get(maxSize).getUrl();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (mPostSearchName != null) {
            maxSize = mPostSearchName.getImageVegetablesList().size() -1;
            quantity = mPostSearchName.getQuantity();
            postContent = mPostSearchName.getContent();
            try {
                if (mPostSearchName.getImageVegetablesList().size() == 0) {
                    urlImage = "";
                } else {
                    urlImage = mPostSearchName.getImageVegetablesList().get(maxSize).getUrl();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (mPostSearchDescription != null) {
            maxSize = mPostSearchDescription.getImageVegetablesList().size() -1;
            quantity = mPostSearchDescription.getQuantity();
            postContent = mPostSearchDescription.getContent();
            try {
                if (mPostSearchDescription.getImageVegetablesList().size() == 0) {
                    urlImage = "";
                } else {
                    urlImage = mPostSearchDescription.getImageVegetablesList().get(maxSize).getUrl();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (mPostSearchKeyword != null) {
            maxSize = mPostSearchKeyword.getImageVegetablesList().size() -1;
            quantity = mPostSearchKeyword.getQuantity();
            postContent = mPostSearchKeyword.getContent();
            try {
                if (mPostSearchKeyword.getImageVegetablesList().size() == 0) {
                    urlImage = "";
                } else {
                    urlImage = mPostSearchKeyword.getImageVegetablesList().get(maxSize).getUrl();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }



        Intent intent = new Intent(getActivity(), UpdatePostActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        Bundle bundle = new Bundle();
        bundle.putString("POST_CONTENT", postContent);
//        if (mPostData.getImageVegetablesList().size() == 0) {
//            urlImage = "";
//        } else {
//            urlImage = mPostData.getImageVegetablesList().get(maxSize).getUrl();
//        }
        bundle.putString("POST_IMAGE", urlImage);
        bundle.putInt("POST_QUANTITY", quantity);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /*delete share err*/
    private void showDialogDeleteErr() {
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
                mBottomSheetDialog.dismiss();
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lnl_post_edit:
                System.out.println("AAAAAAAAAA AccountEditPostBottomSheetFragment AAAAAAAAAAAAAAA");
//                System.out.println("post content: " + mPostData.getContent());
//                System.out.println("post id: " + mPostData.getId());
                mBottomSheetDialog.dismiss();
                clickOpenUpdatePost();
                break;
            case R.id.lnl_post_delete:
                System.out.println("BBBBBBBBBBBBB   AccountEditPostBottomSheetFragment  BBBBBBBBBBBBBBBBBBBBBBB");
                System.out.println("click dele post");
                System.out.println("BBBBBBBBBBBBB   AccountEditPostBottomSheetFragment  BBBBBBBBBBBBBBBBBBBBBBB");

//                mBottomSheetDialog.dismiss();
                showDialogDeletePost();
                break;
        }
    }

    @Override
    public void deleteShareSuccess() {
        mBottomSheetDialog.dismiss();
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void deleteShareFail() {
        showDialogDeleteErr();
    }
}
