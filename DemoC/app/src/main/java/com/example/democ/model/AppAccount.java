package com.example.democ.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class AppAccount implements Serializable {

    @Expose
    @SerializedName("id")
    private String id;
    @Expose
    @SerializedName("phoneNumber")
    private String phoneNumber;
    @Expose
    @SerializedName("passWord")
    private String passWord;
    @Expose
    @SerializedName("status")
    private boolean status;
    @Expose
    @SerializedName("roleId")
    private int roleId;
    @Expose
    @SerializedName("role")
    private RoleAppAccount role;
    @SerializedName("members")
    private List<Members> membersList;

    public AppAccount(String id, String phoneNumber, String passWord, boolean status, int roleId, RoleAppAccount role, List<Members> membersList) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.passWord = passWord;
        this.status = status;
        this.roleId = roleId;
        this.role = role;
        this.membersList = membersList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public RoleAppAccount getRole() {
        return role;
    }

    public void setRole(RoleAppAccount role) {
        this.role = role;
    }

    public List<Members> getMembersList() {
        return membersList;
    }

    public void setMembersList(List<Members> membersList) {
        this.membersList = membersList;
    }
}
