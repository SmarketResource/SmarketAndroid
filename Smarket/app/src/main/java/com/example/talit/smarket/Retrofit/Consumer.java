package com.example.talit.smarket.Retrofit;

import com.example.talit.smarket.LogicalView.Consumers;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by talit on 13/02/2018.
 */

public interface Consumer {

    @FormUrlEncoded
    @POST("Consumers/SaveConsumer")
    Call<Consumers> saveConsumer(@Header("Content-Type") String contenType,
                                 @Header("Accept") String accept,
                                 @Header("Authorization") String authorization,
                                 @Field("UserLogin") String userLogin,
                                 @Field("UserPass") String userPass,
                                 @Field("Name") String name,
                                 @Field("LastName") String lastName,
                                 @Field("TypePhoneId") int typePhone,
                                 @Field("AreaCode") String areaCode,
                                 @Field("PhoneNumber") String phoneNumber);
}
