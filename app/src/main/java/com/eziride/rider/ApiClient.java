package com.eziride.rider;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
//    static OkHttpClient okHttpClient = new OkHttpClient.Builder()
//            .connectTimeout(1, TimeUnit.MINUTES)
//            .readTimeout(1, TimeUnit.MINUTES)
//            .writeTimeout(1, TimeUnit.MINUTES)
//            .build();

    private static Retrofit retrofit = null;


    static Retrofit getClient() {

        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.2.111/fnb/easy-ride/api/")
                .addConverterFactory(GsonConverterFactory.create())
                //   .client(okHttpClient)
                .build();

        return retrofit;
    }


}
