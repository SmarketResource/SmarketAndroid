package com.example.talit.smarket.Retrofit;

import com.example.talit.smarket.LogicalView.Token;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by talit on 03/02/2018.
 */

public interface GeneratedToken {

    @GET("GetGeneratedToken")
    Call<Token> getToken();
}
