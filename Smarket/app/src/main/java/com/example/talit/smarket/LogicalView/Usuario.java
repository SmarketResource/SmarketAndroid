package com.example.talit.smarket.LogicalView;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by talit on 28/12/2017.
 */

public class Usuario {

    @SerializedName("access_token")
    @Expose
    private String access_token;
    @SerializedName("token_type")
    @Expose
    private String token_type;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("TypeUser")
    @Expose
    private String typeUser;
    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("username")
    @Expose
    private String login;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("grant_type")
    @Expose
    private String grant_type;
    private String idConsumidor;

    public Usuario(String idConsumidor, String access_token, String typeUser){
        this.idConsumidor = idConsumidor;
        this.access_token = access_token;
        this.typeUser = typeUser;

    }
    public Usuario(String access_token, String token_type, String email, String typeUser, String error, String login, String password, String grant_type, String idConsumidor) {
        this.access_token = access_token;
        this.token_type = token_type;
        this.email = email;
        this.typeUser = typeUser;
        this.error = error;
        this.login = login;
        this.password = password;
        this.grant_type = grant_type;
        this.idConsumidor = idConsumidor;
    }

    public String getIdConsumidor() {
        return idConsumidor;
    }

    public void setIdConsumidor(String idConsumidor) {
        this.idConsumidor = idConsumidor;
    }

    public String getGrant_type() {
        return grant_type;
    }

    public void setGrant_type(String grant_type) {
        this.grant_type = grant_type;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTypeUser() {
        return typeUser;
    }

    public void setTypeUser(String typeUser) {
        this.typeUser = typeUser;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
