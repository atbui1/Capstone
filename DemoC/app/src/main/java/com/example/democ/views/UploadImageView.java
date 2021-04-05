package com.example.democ.views;

import com.example.democ.model.ImageVegetable;

import java.util.List;

public interface UploadImageView {
    void uploadImageSuccess(ImageVegetable imageVegetables);
    void uploadImageFail();
}
