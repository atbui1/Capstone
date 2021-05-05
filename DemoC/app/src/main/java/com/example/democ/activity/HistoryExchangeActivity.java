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
import com.example.democ.presenters.DeleteHistoryExchangePresenter;
import com.example.democ.presenters.GetHistoryExchangePresenter;
import com.example.democ.presenters.PersonalPresenter;
import com.example.democ.room.entities.User;
import com.example.democ.views.DeleteHistoryExchangeView;
import com.example.democ.views.GetHistoryExchangeView;
import com.example.democ.views.PersonalView;

import java.util.ArrayList;
import java.util.List;

public class HistoryExchangeActivity extends AppCompatActivity implements IClickExChange, GetHistoryExchangeView, PersonalView,
        View.OnClickListener, DeleteHistoryExchangeView {

    private final static String KEY_QR_CODE = "key_qr_code";
    private RecyclerView mRecyclerView;
    private LinearLayout mLnlBack;
    private List<ExchangeData> mListHistory;
    private HistoryExchangeAdapter mHistoryExchangeAdapter;
    private GetHistoryExchangePresenter mGetHistoryExchangePresenter;
    private DeleteHistoryExchangePresenter mDeleteHistoryExchangePresenter;
    private PersonalPresenter mPersonalPresenter;

    private User mUser;
    private String mAccessToken;
    private static int mIntPositionRemove = 0;
    private String mStrAccountId = "";

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
        mDeleteHistoryExchangePresenter = new DeleteHistoryExchangePresenter(getApplication(), getApplicationContext(), this);
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
            mHistoryExchangeAdapter = new HistoryExchangeAdapter(mListHistory, getApplicationContext(), mStrAccountId, this);
            mRecyclerView.setAdapter(mHistoryExchangeAdapter);
        } else {
            mHistoryExchangeAdapter.notifyDataSetChanged();
        }
    }

    /*Unprocessed request   status = 1*/
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
    /*the request was denied    status = 3*/
    private void showDialogRequestDenied() {
        final Dialog dialog = new Dialog(HistoryExchangeActivity.this);
        dialog.setContentView(R.layout.dialog_login_fail);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        TextView txtDetail;
        Button btnClose;
        txtDetail = dialog.findViewById(R.id.txt_detail_err);
        txtDetail.setText("Yêu cầu đã bị từ chối");
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
    /*the exchange finish   status = 4*/
    private void showDialogRequestFinish() {
        final Dialog dialog = new Dialog(HistoryExchangeActivity.this);
        dialog.setContentView(R.layout.dialog_login_fail);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        TextView txtDetail;
        Button btnClose;
        txtDetail = dialog.findViewById(R.id.txt_detail_err);
        txtDetail.setText("Trao đổi đã hoàn tất");
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
    /*request has been processed        status = 2 but not owner*/
    private void showDialogOwner() {
        final Dialog dialog = new Dialog(HistoryExchangeActivity.this);
        dialog.setContentView(R.layout.dialog_login_fail);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        TextView txtDetail;
        Button btnClose;
        txtDetail = dialog.findViewById(R.id.txt_detail_err);
        txtDetail.setText("Bạn không phải người cho rau");
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
    /*dialog delete*/
    private void showDialogDelete(final String exchangeId) {
        final Dialog dialog = new Dialog(HistoryExchangeActivity.this);
        dialog.setContentView(R.layout.dialog_delete_garden);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        TextView txtQuantity;
        Button btnClose, btnDelete;
        btnClose = (Button) dialog.findViewById(R.id.btn_delete_no);
        btnDelete = (Button) dialog.findViewById(R.id.btn_delete_yes);
        txtQuantity = (TextView) dialog.findViewById(R.id.txt_content_delete);
        txtQuantity.setText("Bạn có muốn xóa lịch sữ này không?");


        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDeleteHistoryExchangePresenter.deleteHistoryExchange(exchangeId, mAccessToken);
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
            if (exchangeData.getAccountHostId().equals(mUser.getAccountId())) {
                Intent intent = new Intent(HistoryExchangeActivity.this, QRCodeActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                Bundle bundle = new Bundle();
                bundle.putSerializable(KEY_QR_CODE, exchangeData);
                intent.putExtras(bundle);
                startActivity(intent);
            } else {
                showDialogOwner();
            }
        } else if (exchangeData.getStatus() == 3) {
            showDialogRequestDenied();
        } else if (exchangeData.getStatus() == 4) {
            showDialogRequestFinish();
        }
    }

    private void showDialogHistoryNoDelete() {
        final Dialog dialog = new Dialog(HistoryExchangeActivity.this);
        dialog.setContentView(R.layout.dialog_exchange_quantity_err);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        TextView txtQuantity;
        Button btnClose;
        btnClose = (Button) dialog.findViewById(R.id.btn_close);
        txtQuantity = (TextView) dialog.findViewById(R.id.txt_exchange_quantity);
        txtQuantity.setText("Bạn không thể xóa \n Yêu cầu chưa sữ lý");

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
    public void clickExchangeRemove(ExchangeData exchangeData, int positionClick) {
        String exchangeId = exchangeData.getId();
        mIntPositionRemove = positionClick;
        if (exchangeData.getStatus() == 2) {
            System.out.println("khg dc xoa");
            showDialogHistoryNoDelete();
        } else {
            System.out.println("xoa lich su");
            showDialogDelete(exchangeId);

        }

        System.out.println("exchange id: " + exchangeId);
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
        mUser = user;
        mAccessToken = user.getToken();
        mStrAccountId = user.getAccountId();
        mGetHistoryExchangePresenter.getHistoryExchange(user.getToken());
    }

    @Override
    public void deleteHistoryExchangeSuccess() {
        mListHistory.remove(mIntPositionRemove);
        mHistoryExchangeAdapter.notifyItemRemoved(mIntPositionRemove);
    }

    @Override
    public void deleteHistoryExchangeFail() {

    }
}
