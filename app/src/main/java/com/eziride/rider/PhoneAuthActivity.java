package com.eziride.rider;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hbb20.CountryCodePicker;

public class PhoneAuthActivity extends AppCompatActivity {

    private CountryCodePicker ccp;
    EditText Phone_number_edt;
    String phone_number;
    Button next_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_auth);

        ccp=findViewById(R.id.phAuth_ccp);
        Phone_number_edt=findViewById(R.id.phAuth_number_edt);
        next_btn=findViewById(R.id.phAuth_next_btn);


        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otpCode();
            }
        });


    }

    private void otpCode() {
        startActivity(new Intent(getApplicationContext(),OTPVerify.class));
    }
}
