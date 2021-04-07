package com.example.democ.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ImageWiki implements Serializable {
    @SerializedName("contentDisposition")
    private String contentDisposition;
    @SerializedName("contentType")
    private String contentType;
    @SerializedName("headers")
    private ImageWikiHeader imageWikiHeader;
    @SerializedName("length")
    private int length;
    @SerializedName("name")
    private String name;
    @SerializedName("fileName")
    private String fileName;

    public ImageWiki(String contentDisposition, String contentType, ImageWikiHeader imageWikiHeader, int length, String name, String fileName) {
        this.contentDisposition = contentDisposition;
        this.contentType = contentType;
        this.imageWikiHeader = imageWikiHeader;
        this.length = length;
        this.name = name;
        this.fileName = fileName;
    }

    public String getContentDisposition() {
        return contentDisposition;
    }

    public void setContentDisposition(String contentDisposition) {
        this.contentDisposition = contentDisposition;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public ImageWikiHeader getImageWikiHeader() {
        return imageWikiHeader;
    }

    public void setImageWikiHeader(ImageWikiHeader imageWikiHeader) {
        this.imageWikiHeader = imageWikiHeader;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
