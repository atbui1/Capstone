package com.example.democ.views;

import com.example.democ.model.QRCodeData;

public interface GetQRCodeView {
    void GetQRCodeSuccess(QRCodeData qrCodeData);
    void GetQRCodeFail();
}
