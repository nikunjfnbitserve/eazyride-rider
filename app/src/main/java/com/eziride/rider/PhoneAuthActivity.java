package com.eziride.rider;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.eziride.rider.models.OtpSend;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hbb20.CountryCodePicker;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;

public class PhoneAuthActivity extends AppCompatActivity {

    private CountryCodePicker ccp;
    EditText Phone_number_edt;
    String phone_number;
    Button next_btn;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_auth);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        ccp = findViewById(R.id.phAuth_ccp);
        Phone_number_edt = findViewById(R.id.et_phone_number);
        next_btn = findViewById(R.id.phAuth_next_btn);
        next_btn.setVisibility(View.GONE);

        Phone_number_edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                next_btn.setVisibility(View.GONE);
                if(TextUtils.isEmpty(s)){
                    Phone_number_edt.setError("Phone Number cant be empty");
                } else if(s.length() != 10 ){
                    Phone_number_edt.setError("Invalid Number");
                } else{
                    next_btn.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),OTPVerify.class));
                JsonObject gsonObject = new JsonObject();
                try {
                    JSONObject jsonObj_ = new JSONObject();
                    jsonObj_.put("mobile", Phone_number_edt.getText().toString());
                    jsonObj_.put("role", 1);
                    JsonParser jsonParser = new JsonParser();
                    gsonObject = (JsonObject) jsonParser.parse(jsonObj_.toString());

                    //print parameter
                    Log.e("MY gson.JSON:  ", "AS PARAMETER  " + gsonObject);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

//                try {
//                    if (CommonUtils.isConnectingToInternet(MyActivity.this)) {
//                        final ProgressDialog dialog;
//                        dialog = new ProgressDialog(MyActivity.this);
//                        dialog.setMessage("Loading...");
//                        dialog.setCanceledOnTouchOutside(false);
//                        dialog.show();
//
                        Call<OtpSend> otpSendCall = apiInterface.sendOtp(gsonObject);
                otpSendCall.enqueue(new retrofit2.Callback<OtpSend>() {
                            @Override
                            public void onResponse(Call<OtpSend> otpCall, retrofit2.Response<OtpSend> response) {

                                try {
                                    //print respone
                                    Log.e(" Full json gson => ", new Gson().toJson(response));
                                    JSONObject jsonObj = new JSONObject(new Gson().toJson(response).toString());
                                    Log.e(" responce => ", jsonObj.getJSONObject("body").toString());

                                    if (response.isSuccessful()) {

                                        //dialog.dismiss();
                                        int success = response.body().getStatus();
                                        if (success == 1) {
                                            Toast.makeText(PhoneAuthActivity.this, "otp send success", Toast.LENGTH_SHORT).show();
                                            //startActivity(new Intent(getApplicationContext(),OTPVerify.class));


                                        } else if (success == 0) {



                                       }
                                    } else {
                                        //dialog.dismiss();


                                    }
//
//
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    try {
                                        Log.e("Tag", "error=" + e.toString());

//                                        dialog.dismiss();
                                    } catch (Resources.NotFoundException e1) {
                                        e1.printStackTrace();
                                    }
//
                                }
                            }
//
                            @Override
                            public void onFailure(Call<OtpSend> call, Throwable t) {
                                try {
                                    Log.e("Tag", "error" + t.toString());
//
//                                    dialog.dismiss();
                                } catch (Resources.NotFoundException e) {
                                    e.printStackTrace();
                                }
                            }
//
                        });
//
                    }
//                    else {
 //                       Log.e("Tag", "error= Alert no internet");
//
//
 //                   }
 //               } catch (Resources.NotFoundException e) {
 //                   e.printStackTrace();
//                }


        });



    }

    private void otpCode() {
        startActivity(new Intent(getApplicationContext(),OTPVerify.class));
    }
}
