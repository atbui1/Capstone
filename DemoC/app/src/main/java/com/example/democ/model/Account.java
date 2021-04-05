package com.example.democ.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Account implements Serializable {

    @SerializedName("id")
    private String id;

    @SerializedName("phoneNumber")
    private String phone;

    @SerializedName("password")
    private String password;

    @SerializedName("fullName")
    private String fullName;

    @SerializedName("birthDate")
    private String YOB;

    @SerializedName("sex")
    private int sex;

    @SerializedName("email")
    private String email;

    public Account(String phone, String password, String fullName, String YOB, int sex, String email) {
        this.phone = phone;
        this.password = password;
        this.fullName = fullName;
        this.YOB = YOB;
        this.sex = sex;
        this.email = email;
    }

    public Account(String id, String phone, String password, String fullName, String YOB, int sex, String email) {
        this.id = id;
        this.phone = phone;
        this.password = password;
        this.fullName = fullName;
        this.YOB = YOB;
        this.sex = sex;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getYOB() {
        return YOB;
    }

    public void setYOB(String YOB) {
        this.YOB = YOB;
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

}
