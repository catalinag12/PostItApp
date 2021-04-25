package com.example.mreadyapp.retrofit;

import com.example.mreadyapp.interfaces.ApiInterface;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL="https://intern-hackathon.mready.net/api/";
    private static RetrofitClient myInstance;
    private Retrofit retrofit;

    private RetrofitClient(){
        retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized RetrofitClient getInstance(){
        if(myInstance==null){
            myInstance=new RetrofitClient();

        }
        return myInstance;
    }

    public ApiInterface getApi(){
        return  retrofit.create(ApiInterface.class);
    }

}
