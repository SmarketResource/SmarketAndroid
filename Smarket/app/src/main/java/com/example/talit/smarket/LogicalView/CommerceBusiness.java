package com.example.talit.smarket.LogicalView;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by talit on 15/02/2018.
 */

public class CommerceBusiness {

    @SerializedName("SocialName")
    @Expose
    private String socialName;
    @SerializedName("FantasyName")
    @Expose
    private String fantasyName;
    @SerializedName("CNPJ")
    @Expose
    private String cnpj;
    @SerializedName("AreaCode")
    @Expose
    private Phone areaCode;
    @SerializedName("Number")
    @Expose
    private Phone number;
    @SerializedName("TypePhoneId")
    @Expose
    private Phone typePhoneId;
    @SerializedName("EmployeeName")
    @Expose
    private String employeeName;
    @SerializedName("EmployeeLastName")
    @Expose
    private String employeeLastName;
    @SerializedName("CPF")
    @Expose
    private Pearson cpf;
    @SerializedName("EmployeeRoleId")
    @Expose
    private String employeeRoleId;
    @SerializedName("UserLogin")
    @Expose
    private Usuario userLogin;
    @SerializedName("UserPass")
    @Expose
    private Usuario userPass;

    public CommerceBusiness(String socialName, String fantasyName, String cnpj, Phone areaCode, Phone number, Phone typePhoneId, String employeeName, String employeeLastName, Pearson cpf, String employeeRoleId, Usuario userLogin, Usuario userPass) {
        this.socialName = socialName;
        this.fantasyName = fantasyName;
        this.cnpj = cnpj;
        this.areaCode = areaCode;
        this.number = number;
        this.typePhoneId = typePhoneId;
        this.employeeName = employeeName;
        this.employeeLastName = employeeLastName;
        this.cpf = cpf;
        this.employeeRoleId = employeeRoleId;
        this.userLogin = userLogin;
        this.userPass = userPass;
    }

    public String getSocialName() {
        return socialName;
    }

    public void setSocialName(String socialName) {
        this.socialName = socialName;
    }

    public String getFantasyName() {
        return fantasyName;
    }

    public void setFantasyName(String fantasyName) {
        this.fantasyName = fantasyName;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public Phone getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(Phone areaCode) {
        this.areaCode = areaCode;
    }

    public Phone getNumber() {
        return number;
    }

    public void setNumber(Phone number) {
        this.number = number;
    }

    public Phone getTypePhoneId() {
        return typePhoneId;
    }

    public void setTypePhoneId(Phone typePhoneId) {
        this.typePhoneId = typePhoneId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeLastName() {
        return employeeLastName;
    }

    public void setEmployeeLastName(String employeeLastName) {
        this.employeeLastName = employeeLastName;
    }

    public Pearson getCpf() {
        return cpf;
    }

    public void setCpf(Pearson cpf) {
        this.cpf = cpf;
    }

    public String getEmployeeRoleId() {
        return employeeRoleId;
    }

    public void setEmployeeRoleId(String employeeRoleId) {
        this.employeeRoleId = employeeRoleId;
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
}
