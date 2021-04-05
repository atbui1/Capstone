package com.example.democ.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Members implements Serializable {
    @SerializedName("id")
    private int id;
    @SerializedName("fullName")
    private String fullName;
    @SerializedName("birthDate")
    private String birthDate;
    @SerializedName("sex")
    private int sex;
    @SerializedName("email")
    private String email;
    @SerializedName("createdDate")
    private String createdDate;
    @SerializedName("accountId")
    private String accountId;

    public Members(int id, String fullName, String birthDate, int sex, String email, String createdDate, String accountId) {
        this.id = id;
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.sex = sex;
        this.email = email;
        this.createdDate = createdDate;
        this.accountId = accountId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}
