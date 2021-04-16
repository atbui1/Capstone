package com.example.democ.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.democ.R;
import com.example.democ.model.PostData;
import com.example.democ.model.PostSearchDescription;
import com.example.democ.model.PostSearchKeyword;
import com.example.democ.model.PostSearchName;
import com.example.democ.model.ReportPost;
import com.example.democ.presenters.ReportPostPresenter;
import com.example.democ.views.ReportPostView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ReportPostBottomSheetFragment extends BottomSheetDialogFragment implements View.OnClickListener, ReportPostView {
    public static String R_SPAM = "Đây là spam";
    public static String R_CONTENT_ERR = "Nội dung không phù hợp";
    public static String R_CHEAT = "Lừa đảo hoặc gian lận";

    private BottomSheetDialog mBottomSheetDialog;
    private LinearLayout mLnlReportSpam, mLnlReportContentErr, mLnlReportCheat;
    private ReportPostPresenter mReportPostPresenter;
    private View mView;
    private PostData mPostData;
    private PostSearchName mPostSearchName;
    private PostSearchKeyword mPostSearchKeyword;
    private PostSearchDescription mPostSearchDescription;
    private String accessToken;
    private String accountId;
    private String mPostId = "";

    public ReportPostBottomSheetFragment(PostData mPostData, String accessToken, String accountId) {
        this.mPostData = mPostData;
        this.accessToken = accessToken;
        this.accountId = accountId;
    }

    public ReportPostBottomSheetFragment(PostSearchName mPostSearchName, String accessToken, String accountId) {
        this.mPostSearchName = mPostSearchName;
        this.accessToken = accessToken;
        this.accountId = accountId;
    }

    public ReportPostBottomSheetFragment(PostSearchKeyword mPostSearchKeyword, String accessToken, String accountId) {
        this.mPostSearchKeyword = mPostSearchKeyword;
        this.accessToken = accessToken;
        this.accountId = accountId;
    }

    public ReportPostBottomSheetFragment(PostSearchDescription mPostSearchDescription, String accountId, String mPostId) {
        this.mPostSearchDescription = mPostSearchDescription;
        this.accountId = accountId;
        this.mPostId = mPostId;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        mBottomSheetDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        mBottomSheetDialog = new BottomSheetDialog(getContext(), R.style.BottomSheetDialogTheme);
        mView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_report_post_bottom_sheet, null);
        mBottomSheetDialog.setContentView(mView);

        mReportPostPresenter = new ReportPostPresenter(getActivity().getApplication(), getActivity(), this);

        mLnlReportSpam = (LinearLayout) mView.findViewById(R.id.lnl_report_spam);
        mLnlReportContentErr = (LinearLayout) mView.findViewById(R.id.lnl_report_content_err);
        mLnlReportCheat = (LinearLayout) mView.findViewById(R.id.lnl_report_cheat);

        mLnlReportSpam.setOnClickListener(this);
        mLnlReportContentErr.setOnClickListener(this);
        mLnlReportCheat.setOnClickListener(this);

        return mBottomSheetDialog;
    }

    private void reportSpam() {
        String reportContent = R_SPAM;
        if (mPostData != null) {
            mPostId = mPostData.getId();
        } else if (mPostSearchName != null) {
            mPostId = mPostSearchName.getId();
        } else if (mPostSearchKeyword != null) {
            mPostId = mPostSearchKeyword.getId();
        } else if (mPostSearchDescription != null) {
            mPostId = mPostSearchDescription.getId();
        }
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        System.out.println("mPostId report: " + mPostId);
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        ReportPost reportPost = new ReportPost(reportContent, mPostId, accountId);
        mReportPostPresenter.reportPost(reportPost, accessToken);
        mBottomSheetDialog.dismiss();
    }
    private void reportContentErr() {
        String reportContent = R_CONTENT_ERR;
        if (mPostData != null) {
            mPostId = mPostData.getId();
        } else if (mPostSearchName != null) {
            mPostId = mPostSearchName.getId();
        } else if (mPostSearchKeyword != null) {
            mPostId = mPostSearchKeyword.getId();
        } else if (mPostSearchDescription != null) {
            mPostId = mPostSearchDescription.getId();
        }

        ReportPost reportPost = new ReportPost(reportContent, mPostId, accountId);
        mReportPostPresenter.reportPost(reportPost, accessToken);
        mBottomSheetDialog.dismiss();
    }
    private void reportCheat() {
        String reportContent = R_CHEAT;
        if (mPostData != null) {
            mPostId = mPostData.getId();
        } else if (mPostSearchName != null) {
            mPostId = mPostSearchName.getId();
        } else if (mPostSearchKeyword != null) {
            mPostId = mPostSearchKeyword.getId();
        } else if (mPostSearchDescription != null) {
            mPostId = mPostSearchDescription.getId();
        }
        ReportPost reportPost = new ReportPost(reportContent, mPostId, accountId);
        mReportPostPresenter.reportPost(reportPost, accessToken);
        mBottomSheetDialog.dismiss();
    }
    private void showDialogReportSuccess() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_exchange_quantity_err);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        TextView txtQuantity;
        Button btnClose;
        btnClose = (Button) dialog.findViewById(R.id.btn_close);
        txtQuantity = (TextView) dialog.findViewById(R.id.txt_exchange_quantity);
        txtQuantity.setText("    Gửi báo cáo thành công     \n Cảm ơn bạn đã đóng góp");
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
            case R.id.lnl_report_spam:
                reportSpam();
                break;
            case R.id.lnl_report_content_err:
                reportContentErr();
                break;
            case R.id.lnl_report_cheat:
                reportCheat();
                break;
        }
    }

    @Override
    public void reportPostSuccess() {
        System.out.println("DDDDDDDDDDDDDDDDDDDDDDDDDDDDDĐ");
        System.out.println("Send report success");
        System.out.println("DDDDDDDDDDDDDDDDDDDDDDDDDDDDDĐ");
//        Toast.makeText(getActivity(), "Gửi báo cáo thành công", Toast.LENGTH_SHORT).show();
//        showDialogReportSuccess();
    }

    @Override
    public void reportPostFail() {

    }
}
