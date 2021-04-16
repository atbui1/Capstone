package com.example.democ.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.democ.R;
import com.example.democ.adapter.HistoryExchangeAdapter;
import com.example.democ.iclick.IClickExChange;
import com.example.democ.model.ExchangeData;
import com.example.democ.presenters.GetHistoryExchangePresenter;
import com.example.democ.presenters.PersonalPresenter;
import com.example.democ.room.entities.User;
import com.example.democ.views.GetHistoryExchangeView;
import com.example.democ.views.PersonalView;

import java.util.ArrayList;
import java.util.List;

public class HistoryExchangeActivity extends AppCompatActivity implements IClickExChange, GetHistoryExchangeView, PersonalView,
        View.OnClickListener {

    private final static String KEY_QR_CODE = "key_qr_code";
    private RecyclerView mRecyclerView;
    private LinearLayout mLnlBack;
    private List<ExchangeData> mListHistory;
    private HistoryExchangeAdapter mHistoryExchangeAdapter;
    private GetHistoryExchangePresenter mGetHistoryExchangePresenter;
    private PersonalPresenter mPersonalPresenter;

    private String mAccessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_exchange);

        initialView();
        initialData();
    }

    private void initialView() {
        mPersonalPresenter = new PersonalPresenter(getApplicationContext(), this);
        mPersonalPresenter.getInfoPersonal();

        mGetHistoryExchangePresenter = new GetHistoryExchangePresenter(getApplication(), getApplicationContext(), this);
        mListHistory = new ArrayList<>();

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_history);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);

        mLnlBack = (LinearLayout) findViewById(R.id.lnl_back);
        mLnlBack.setOnClickListener(this);
    }

    private void initialData() {
//        updateUI();
    }
    private void updateUI() {
        if (mHistoryExchangeAdapter == null) {
            mHistoryExchangeAdapter = new HistoryExchangeAdapter(mListHistory, getApplicationContext(), this);
            mRecyclerView.setAdapter(mHistoryExchangeAdapter);
        } else {
            mHistoryExchangeAdapter.notifyDataSetChanged();
        }
    }

    private void showDialogNotification() {
        final Dialog dialog = new Dialog(HistoryExchangeActivity.this);
        dialog.setContentView(R.layout.dialog_login_fail);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        TextView txtDetail;
        Button btnClose;
        txtDetail = dialog.findViewById(R.id.txt_detail_err);
        txtDetail.setText("Chưa cho, nhận rau");
        btnClose = dialog.findViewById(R.id.btn_close);

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
            case R.id.lnl_back:
                finish();
                break;
        }
    }

    @Override
    public void clickExchangeAccept(ExchangeData exchangeData, int positionClick) {
//        ExchangeData exchangeDataSend = exchangeData;
        if (exchangeData.getStatus() == 1) {
            showDialogNotification();
        } else if (exchangeData.getStatus() == 2) {
            Intent intent = new Intent(HistoryExchangeActivity.this, QRCodeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            Bundle bundle = new Bundle();
            bundle.putSerializable(KEY_QR_CODE, exchangeData);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    @Override
    public void clickExchangeRemove(ExchangeData exchangeData, int positionClick) {
        //no event
    }

    @Override
    public void GetHistoryExchangeSuccess(List<ExchangeData> exchangeData) {
        mListHistory = exchangeData;
        System.out.println("SSSSSSSSSSSSS   GetHistoryExchangeSuccess      SSSSSSSSSSSSSSSSSSSS");
        if (mListHistory != null) {
            System.out.println("list history exchange size: " + mListHistory.size());
            updateUI();
        }
    }

    @Override
    public void GetHistoryExchangeFail() {

    }

    @Override
    public void showInfoPersonal(User user) {
        mAccessToken = user.getToken();
        mGetHistoryExchangePresenter.getHistoryExchange(user.getToken());
    }
}
