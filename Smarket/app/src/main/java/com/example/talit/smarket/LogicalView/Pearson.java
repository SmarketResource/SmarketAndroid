package com.example.talit.smarket.LogicalView;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by talit on 13/02/2018.
 */

public class Pearson {
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
    private Phone areaCode;
    @SerializedName("PhoneNumber")
    @Expose
    private Phone phoneNumber;
    private String cpf;

    public Pearson(Usuario userLogin, Usuario userPass, String name, String lastname, int typePhoneId, Phone areaCode, Phone phoneNumber, String cpf) {
        this.userLogin = userLogin;
        this.userPass = userPass;
        this.name = name;
        this.lastname = lastname;
        this.typePhoneId = typePhoneId;
        this.areaCode = areaCode;
        this.phoneNumber = phoneNumber;
        this.cpf = cpf;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
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

    public Phone getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(Phone areaCode) {
        this.areaCode = areaCode;
    }

    public Phone getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Phone phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
