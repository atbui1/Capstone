package com.example.democ.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ImageWikiHeader implements Serializable {
    @SerializedName("Content-Disposition")
    private List<String> ContentDisposition;
    @SerializedName("Content-Type")
    private List<String> ContentType;

    public ImageWikiHeader(List<String> contentDisposition, List<String> contentType) {
        ContentDisposition = contentDisposition;
        ContentType = contentType;
    }

    public List<String> getContentDisposition() {
        return ContentDisposition;
    }

    public void setContentDisposition(List<String> contentDisposition) {
        ContentDisposition = contentDisposition;
    }

    public List<String> getContentType() {
        return ContentType;
    }

    public void setContentType(List<String> contentType) {
        ContentType = contentType;
    }
}
