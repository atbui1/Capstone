package com.example.democ.presenters;

import android.app.Application;
import android.content.Context;

import com.example.democ.capstone.CapstoneRepository;
import com.example.democ.capstone.CapstoneRepositoryImp;
import com.example.democ.model.ReportPost;
import com.example.democ.utils.CallBackData;
import com.example.democ.views.ReportPostView;

public class ReportPostPresenter {
    private CapstoneRepository mCapstoneRepository;
    private Application mApplication;
    private Context mContext;
    private ReportPostView mReportPostView;

    public ReportPostPresenter(Application mApplication, Context mContext, ReportPostView mReportPostView) {
        this.mApplication = mApplication;
        this.mContext = mContext;
        this.mReportPostView = mReportPostView;
        mCapstoneRepository = new CapstoneRepositoryImp();
    }

    public void reportPost(ReportPost reportPost, String token) {
        mCapstoneRepository.reportPost(mContext, reportPost, token, new CallBackData<String>() {
            @Override
            public void onSuccess(String s) {
                mReportPostView.reportPostSuccess();
            }

            @Override
            public void onFail(String msgFail) {
                mReportPostView.reportPostFail();
            }
        });
    }
}
