package com.example.talit.smarket.LogicalView;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by talit on 03/02/2018.
 */

public class Token {
    @SerializedName("Token")
    @Expose
    private String Token;

    public Token(String Token) {
        this.Token = Token;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String Token) {
        this.Token = Token;
    }
}
