package com.eziride.rider;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static Retrofit retrofit = null;

    static Retrofit getClient() {

        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.2.102/fnb/easy-ride/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();



        return retrofit;
    }


}
