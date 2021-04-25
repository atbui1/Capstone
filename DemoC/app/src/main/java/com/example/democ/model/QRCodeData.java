package com.example.democ.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class QRCodeData implements Serializable {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("url")
    private String url;
    @SerializedName("exchangeId")
    private String exchangeId;

    public QRCodeData(String id, String name, String url, String exchangeId) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.exchangeId = exchangeId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getExchangeId() {
        return exchangeId;
    }

    public void setExchangeId(String exchangeId) {
        this.exchangeId = exchangeId;
    }

}
