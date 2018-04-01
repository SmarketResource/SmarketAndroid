package com.example.talit.smarket.Retrofit;

import com.example.talit.smarket.LogicalView.CommerceBusiness;
import com.example.talit.smarket.LogicalView.Pearson;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by talit on 16/02/2018.
 */

public interface Commerce {

    @FormUrlEncoded
    @POST("Commerce/SaveCommerce")
    Call<CommerceBusiness> saveCommerce(@Header("Content-Type") String contenType,
                                        @Header("Accept") String accept,
                                        @Header("Authorization") String authorization,
                                        @Field("SocialName") String socialName,
                                        @Field("FantasyName") String fantasyName,
                                        @Field("CNPJ") String cnpj,
                                        @Field("AreaCode") String areaCode,
                                        @Field("Number") String phoneNumber,
                                        @Field("TypePhoneId") int typePhone,
                                        @Field("EmployeeName") String employeeName,
                                        @Field("EmployeeLastName") String employeeLastName,
                                        @Field("CPF") String cpf,
                                        @Field("EmployeeRoleId") int employeeRoleId,
                                        @Field("UserLogin") String userLogin,
                                        @Field("UserPass") String userPasss);
}
