package com.example.democ.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class VegetableDescription implements Serializable {

    @SerializedName("id")
    private String id;
    @SerializedName("vegContent")
    private String vegContent;
    @SerializedName("vegetableCompositionId")
    private int vegetableCompositionId;
    @SerializedName("accountId")
    private String accountId;
    @SerializedName("vegetableComposition")
    private VegetableComposition vegetableComposition;
    @SerializedName("appAccount")
    private AppAccount appAccount;

    public VegetableDescription(String id, String vegContent, int vegetableCompositionId,
                                String accountId, VegetableComposition vegetableComposition, AppAccount appAccount) {
        this.id = id;
        this.vegContent = vegContent;
        this.vegetableCompositionId = vegetableCompositionId;
        this.accountId = accountId;
        this.vegetableComposition = vegetableComposition;
        this.appAccount = appAccount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVegContent() {
        return vegContent;
    }

    public void setVegContent(String vegContent) {
        this.vegContent = vegContent;
    }

    public int getVegetableCompositionId() {
        return vegetableCompositionId;
    }

    public void setVegetableCompositionId(int vegetableCompositionId) {
        this.vegetableCompositionId = vegetableCompositionId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public VegetableComposition getVegetableComposition() {
        return vegetableComposition;
    }

    public void setVegetableComposition(VegetableComposition vegetableComposition) {
        this.vegetableComposition = vegetableComposition;
    }

    public AppAccount getAppAccount() {
        return appAccount;
    }

    public void setAppAccount(AppAccount appAccount) {
        this.appAccount = appAccount;
    }
}
