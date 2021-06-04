package com.example.democ.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AccountData implements Serializable {

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
    @SerializedName("provinceId")
    private int provinceId;
    @SerializedName("districtId")
    private int districtId;
    @SerializedName("wardId")
    private int wardId;
    @SerializedName("email")
    private String email;
    @SerializedName("address")
    private String address;
    @SerializedName("avatarRequest")
    private String avatarRequest;
    @SerializedName("avatarResponse")
    private String avatarResponse;
    @SerializedName("isFriend")
    private int isFriend;
    @SerializedName("idRequest")
    private int idRequest;

    /*old request*/
    public AccountData(String phone, String password, String fullName, String YOB, int sex, String email) {
        this.phone = phone;
        this.password = password;
        this.fullName = fullName;
        this.YOB = YOB;
        this.sex = sex;
        this.email = email;
    }

/*them address request*/
    public AccountData(String phone, String password, String fullName, String YOB, int sex, String email, String address) {
        this.phone = phone;
        this.password = password;
        this.fullName = fullName;
        this.YOB = YOB;
        this.sex = sex;
        this.email = email;
        this.address = address;
    }
    /*them address response*/
    public AccountData(String id, String phone, String password, String fullName, String YOB, int sex,
                       String email, String address, int idRequest) {
        this.id = id;
        this.phone = phone;
        this.password = password;
        this.fullName = fullName;
        this.YOB = YOB;
        this.sex = sex;
        this.email = email;
        this.address = address;
        this.idRequest = idRequest;
    }

    /*new update account - api/AccountDetail/Update*/
    public AccountData(String id, String fullName, String YOB, int sex, int provinceId, int districtId,
                       int wardId, String email, String address) {
        this.id = id;
        this.fullName = fullName;
        this.YOB = YOB;
        this.sex = sex;
        this.provinceId = provinceId;
        this.districtId = districtId;
        this.wardId = wardId;
        this.email = email;
        this.address = address;
    }

    /*GetAccountDetailByPhoneNumber response*/
    public AccountData(String id, String phone, String password, String fullName, String YOB, int sex,
                       int provinceId, int districtId, int wardId, String email, String address,
                       String avatarRequest, String avatarResponse, int isFriend, int idRequest) {
        this.id = id;
        this.phone = phone;
        this.password = password;
        this.fullName = fullName;
        this.YOB = YOB;
        this.sex = sex;
        this.provinceId = provinceId;
        this.districtId = districtId;
        this.wardId = wardId;
        this.email = email;
        this.address = address;
        this.avatarRequest = avatarRequest;
        this.avatarResponse = avatarResponse;
        this.isFriend = isFriend;
        this.idRequest = idRequest;
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

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }

    public int getWardId() {
        return wardId;
    }

    public void setWardId(int wardId) {
        this.wardId = wardId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAvatarRequest() {
        return avatarRequest;
    }

    public void setAvatarRequest(String avatarRequest) {
        this.avatarRequest = avatarRequest;
    }

    public String getAvatarResponse() {
        return avatarResponse;
    }

    public void setAvatarResponse(String avatarResponse) {
        this.avatarResponse = avatarResponse;
    }

    public int getIsFriend() {
        return isFriend;
    }

    public void setIsFriend(int isFriend) {
        this.isFriend = isFriend;
    }

    public int getIdRequest() {
        return idRequest;
    }

    public void setIdRequest(int idRequest) {
        this.idRequest = idRequest;
    }
}
