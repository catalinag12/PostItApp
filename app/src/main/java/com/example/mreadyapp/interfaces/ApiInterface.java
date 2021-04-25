package com.example.mreadyapp.interfaces;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("auth/register")
    Call<ResponseBody> register(
  @Field("Username") String Username,
  @Field("Password") String Password,
  @Field("Display_name") String Display_name
);

    @FormUrlEncoded
    @POST("auth/login")
    Call<ResponseBody> login(
            @Field("Username") String Username,
            @Field("Password") String Password
    );

    @FormUrlEncoded
    @POST("posts")
    Call<ResponseBody> posts(
            @Field("message") String message,
            @Header("Authorization")  String token
    );

    @GET("posts")
    Call<ResponseBody>getPosts();
}
