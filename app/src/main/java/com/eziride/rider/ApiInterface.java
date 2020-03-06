package com.eziride.rider;

import com.eziride.rider.models.OtpSend;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @POST("user_vendor_login.php")
    Call<OtpSend> sendOtp(@Body JsonObject jsonObject);


}
