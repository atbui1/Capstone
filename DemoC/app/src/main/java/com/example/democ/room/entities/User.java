package com.example.democ.room.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "User")
public class User implements Serializable {
    @SerializedName("id")
    @ColumnInfo(name = "userId")
    private int userId;
    @PrimaryKey(autoGenerate = true)
    private int userInfoId;

    @SerializedName("phoneNumber")
    @ColumnInfo(name = "phoneNumber")
    private String phoneNumber;

    @SerializedName("email")
    @ColumnInfo(name = "email")
    private String email;

    @SerializedName("full_name")
    @ColumnInfo(name = "full_name")
    private String full_name;

    @SerializedName("accountId")
    @ColumnInfo(name = "accountId")
    private String accountId;

    @SerializedName("fullName")
    @ColumnInfo(name = "fullName")
    private String fullName;

    @SerializedName("password")
    @ColumnInfo(name = "password")
    private String password;

    @SerializedName("sex")
    @ColumnInfo(name = "sex")
    private String sex;

    @SerializedName("yob")
    @ColumnInfo(name = "yob")
    private String YOB;

    @SerializedName("token")
    @ColumnInfo(name = "token")
    private String token;

    public User() {
    }

//    public User(int userId, int userInfoId, String phoneNumber, String email,
//                String full_name, String password, String sex, String YOB) {
//        this.userId = userId;
//        this.userInfoId = userInfoId;
//        this.phoneNumber = phoneNumber;
//        this.email = email;
//        this.full_name = full_name;
//        this.password = password;
//        this.sex = sex;
//        this.YOB = YOB;
//    }


    public User(int userId, int userInfoId, String phoneNumber, String email, String full_name, String accountId,
                String fullName, String password, String sex, String YOB, String token) {
        this.userId = userId;
        this.userInfoId = userInfoId;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.full_name = full_name;
        this.accountId = accountId;
        this.fullName = fullName;
        this.password = password;
        this.sex = sex;
        this.YOB = YOB;
        this.token = token;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserInfoId() {
        return userInfoId;
    }

    public void setUserInfoId(int userInfoId) {
        this.userInfoId = userInfoId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getYOB() {
        return YOB;
    }

    public void setYOB(String YOB) {
        this.YOB = YOB;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
