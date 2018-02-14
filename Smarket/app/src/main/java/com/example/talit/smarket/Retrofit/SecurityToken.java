package com.example.talit.smarket.Retrofit;


import com.example.talit.smarket.LogicalView.Usuario;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by talit on 04/02/2018.
 */

public interface SecurityToken {

    @FormUrlEncoded
    @Headers({
            "Content-Type: application/x-www-form-urlencoded",
            "Accept: application/json"
    })
    @POST("security/token")
    Call<Usuario> getAuthentication(@Field("username") String username,
                                    @Field("password") String password,
                                    @Field("grant_type") String grant_type);
}
