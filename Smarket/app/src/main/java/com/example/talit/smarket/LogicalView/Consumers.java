package com.example.talit.smarket.LogicalView;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by talit on 13/02/2018.
 */

public class Consumers {
    @SerializedName("UserLogin")
    @Expose
    private Usuario userLogin;
    @SerializedName("UserPass")
    @Expose
    private Usuario userPass;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("LastName")
    @Expose
    private String lastname;
    @SerializedName("TypePhoneId")
    @Expose
    private int typePhoneId;
    @SerializedName("AreaCode")
    @Expose
    private String areaCode;
    @SerializedName("PhoneNumber")
    @Expose
    private String phoneNumber;

    public Consumers(Usuario userLogin, Usuario userPass, String name, String lastname, int typePhoneId, String areaCode, String phoneNumber) {
        this.userLogin = userLogin;
        this.userPass = userPass;
        this.name = name;
        this.lastname = lastname;
        this.typePhoneId = typePhoneId;
        this.areaCode = areaCode;
        this.phoneNumber = phoneNumber;
    }

    public Usuario getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(Usuario userLogin) {
        this.userLogin = userLogin;
    }

    public Usuario getUserPass() {
        return userPass;
    }

    public void setUserPass(Usuario userPass) {
        this.userPass = userPass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getTypePhoneId() {
        return typePhoneId;
    }

    public void setTypePhoneId(int typePhoneId) {
        this.typePhoneId = typePhoneId;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
