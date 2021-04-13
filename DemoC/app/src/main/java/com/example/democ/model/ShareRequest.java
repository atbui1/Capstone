package com.example.democ.model;

import java.util.List;

public class ShareRequest {
    private String content;
    private int quantity;
    private int status;
    private String vegetableId;
    private List<String> vegetableNeedId;

    public ShareRequest(String content, int quantity, int status, String vegetableId, List<String> vegetableNeedId) {
        this.content = content;
        this.quantity = quantity;
        this.status = status;
        this.vegetableId = vegetableId;
        this.vegetableNeedId = vegetableNeedId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getVegetableId() {
        return vegetableId;
    }

    public void setVegetableId(String vegetableId) {
        this.vegetableId = vegetableId;
    }

    public List<String> getVegetableNeedId() {
        return vegetableNeedId;
    }

    public void setVegetableNeedId(List<String> vegetableNeedId) {
        this.vegetableNeedId = vegetableNeedId;
    }
}
