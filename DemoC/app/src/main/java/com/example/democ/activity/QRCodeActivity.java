package com.example.democ.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.democ.R;
import com.example.democ.model.ExchangeData;
import com.example.democ.model.QRCodeData;
import com.example.democ.presenters.GetQRCodePresenter;
import com.example.democ.presenters.PersonalPresenter;
import com.example.democ.room.entities.User;
import com.example.democ.views.GetQRCodeView;
import com.example.democ.views.PersonalView;
import com.squareup.picasso.Picasso;

public class QRCodeActivity extends AppCompatActivity implements View.OnClickListener, PersonalView, GetQRCodeView {

    private final static String KEY_QR_CODE = "key_qr_code";
    private LinearLayout mLnlBack;
    private ImageView mImgQRCode;

    private PersonalPresenter mPersonalPresenter;
    private GetQRCodePresenter mGetQRCodePresenter;

    private String mStrExchangeId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);

        initialView();
        initialData();
    }

    private void initialView() {

        mLnlBack = (LinearLayout) findViewById(R.id.lnl_back);
        mLnlBack.setOnClickListener(this);
        mImgQRCode = (ImageView) findViewById(R.id.img_qr_code);
    }

    private void initialData() {
        getData();
        mPersonalPresenter = new PersonalPresenter(getApplicationContext(), this);
        mPersonalPresenter.getInfoPersonal();
        mGetQRCodePresenter = new GetQRCodePresenter(getApplication(), this, this);
    }

    private void getData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            ExchangeData exchangeData = (ExchangeData) bundle.getSerializable(KEY_QR_CODE);
            System.out.println("AAAAAAAAAAAAAAAAAAAAgetdataAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
            System.out.println("id post history: " + exchangeData.getId());
            mStrExchangeId = exchangeData.getId();
            System.out.println("AAAAAAAAAAAAAAAAAAAAgetdataAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        } else {
            mStrExchangeId = "";
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lnl_back:
                break;
        }
    }

    @Override
    public void showInfoPersonal(User user) {
        System.out.println("QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQ");
        System.out.println("exchangeid: " + mStrExchangeId);
        System.out.println("accessToken: " + user.getToken());
        System.out.println("QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQ");
        mGetQRCodePresenter.getQRCode(mStrExchangeId, user.getToken());
    }

    @Override
    public void GetQRCodeSuccess(QRCodeData qrCodeData) {
//        QRCodeData qrCodeDataResponse = qrCodeData;
//        if (qrCodeDataResponse != null) {
//            Picasso.with(this).load(qrCodeDataResponse.getUrl().trim())
//                    .placeholder(R.drawable.ic_launcher_background)
//                    .error(R.drawable.caybacha)
//                    .into(mImgQRCode);
//        }
        if (qrCodeData != null) {
            Picasso.with(this).load(qrCodeData.getUrl().trim())
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.caybacha)
                    .into(mImgQRCode);
        }
        System.out.println("QQQQQQQQQQQQQ   GetQRCodeSuccess    QQQQQQQQQQQQQQQQQQQ");
        System.out.println("linkUrl: " + qrCodeData.getUrl());
        System.out.println("QQQQQQQQQQQQQ   GetQRCodeSuccess    QQQQQQQQQQQQQQQQQQQ");
    }

    @Override
    public void GetQRCodeFail() {

    }
}
