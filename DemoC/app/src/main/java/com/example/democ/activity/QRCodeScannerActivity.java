package com.example.democ.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.democ.R;
import com.example.democ.presenters.ConfirmExchangeFinishPresenter;
import com.example.democ.presenters.PersonalPresenter;
import com.example.democ.presenters.ReplyExchangeRequestPresenter;
import com.example.democ.room.entities.User;
import com.example.democ.views.ConfirmExchangeFinishView;
import com.example.democ.views.PersonalView;
import com.example.democ.views.ReplyExchangeRequestView;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QRCodeScannerActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler,
        View.OnClickListener, PersonalView, ReplyExchangeRequestView, ConfirmExchangeFinishView {

    ZXingScannerView mZXingScannerView;
    private LinearLayout mLnlBack;
    private String mStrQRCodeScanner = "";
    private User mUser;
    private PersonalPresenter mPersonalPresenter;
    private ReplyExchangeRequestPresenter mReplyExchangeRequestPresenter;
    private ConfirmExchangeFinishPresenter mConfirmExchangeFinishPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mZXingScannerView = new ZXingScannerView(this);
        setContentView(R.layout.activity_qrcode_scanner);
//        setContentView(mZXingScannerView);

        initialView();
        initialData();
    }

    private void initialView() {
        mLnlBack = (LinearLayout) findViewById(R.id.lnl_back);
        mLnlBack.setOnClickListener(this);
        mZXingScannerView = findViewById(R.id.zxingscannerview);

        mPersonalPresenter = new PersonalPresenter(getApplicationContext(), this);
        mPersonalPresenter.getInfoPersonal();
        mReplyExchangeRequestPresenter = new ReplyExchangeRequestPresenter(getApplication(), getApplicationContext(), this);
        mConfirmExchangeFinishPresenter = new ConfirmExchangeFinishPresenter(getApplication(), getApplicationContext(), this);

        Dexter.withContext(getApplicationContext())
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        mZXingScannerView.startCamera();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }

    private void initialData() {
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
    public void handleResult(Result rawResult) {
        mStrQRCodeScanner = rawResult.getText();
        showDialogSendQRCodeScanner(mStrQRCodeScanner);
        Toast.makeText(getApplicationContext(), rawResult.getText(), Toast.LENGTH_SHORT).show();
        System.out.println("QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQ");
        System.out.println("abc: " + rawResult.getText());
        System.out.println("QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQ");
    }
    @Override
    public void onResume() {
        super.onResume();
        mZXingScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mZXingScannerView.startCamera();          // Start camera on resume
    }
    @Override
    public void onPause() {
        super.onPause();
        mZXingScannerView.stopCamera();           // Stop camera on pause
    }
    /*dialog delete*/
    private void showDialogSendQRCodeScanner(final String requestId) {
        final Dialog dialog = new Dialog(QRCodeScannerActivity.this);
        dialog.setContentView(R.layout.dialog_delete_garden);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        TextView txtQuantity;
        Button btnClose, btnDelete;
        btnClose = (Button) dialog.findViewById(R.id.btn_delete_no);
        btnDelete = (Button) dialog.findViewById(R.id.btn_delete_yes);
        btnDelete.setText("Xác nhận");
        txtQuantity = (TextView) dialog.findViewById(R.id.txt_content_delete);
        txtQuantity.setText("Xác nhận bạn đã nhận cây ");


        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Intent intent = new Intent(QRCodeScannerActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                mReplyExchangeRequestPresenter.replyExchangeRequest(requestId, 4, mUser.getToken());
                mConfirmExchangeFinishPresenter.confirmExchangeFinish(requestId, mUser.getToken());
                dialog.dismiss();
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
    /*send request err*/
    private void showDialogSendQRCodeScannerSuccess() {
        final Dialog dialog = new Dialog(QRCodeScannerActivity.this);
        dialog.setContentView(R.layout.dialog_exchange_quantity_err);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        TextView txtQuantity;
        Button btnClose;
        btnClose = (Button) dialog.findViewById(R.id.btn_close);
        txtQuantity = (TextView) dialog.findViewById(R.id.txt_exchange_quantity);
        txtQuantity.setText("Xác nhận thành công" );
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
                Intent intent = new Intent(QRCodeScannerActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
    /*send request err*/
    private void showDialogSendQRCodeScannerErr() {
        final Dialog dialog = new Dialog(QRCodeScannerActivity.this);
        dialog.setContentView(R.layout.dialog_exchange_quantity_err);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        TextView txtQuantity;
        Button btnClose;
        btnClose = (Button) dialog.findViewById(R.id.btn_close);
        txtQuantity = (TextView) dialog.findViewById(R.id.txt_exchange_quantity);
        txtQuantity.setText("Bạn không thể xác nhận" );
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Intent intent = new Intent(QRCodeScannerActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    @Override
    public void showInfoPersonal(User user) {
        mUser = user;
    }

    @Override
    public void replyExchangeRequestSuccess() {
        showDialogSendQRCodeScannerSuccess();
    }

    @Override
    public void replyExchangeRequestFail() {
        showDialogSendQRCodeScannerErr();
    }

    @Override
    public void confirmExchangeFinishSuccess() {
        showDialogSendQRCodeScannerSuccess();
    }

    @Override
    public void confirmExchangeFinishFail() {
        showDialogSendQRCodeScannerErr();
    }
}
