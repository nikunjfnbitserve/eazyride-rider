package com.eziride.rider;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class Register extends AppCompatActivity {

    Button phoneauthbtn;

    ImageView logo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        phoneauthbtn=findViewById(R.id.register_phone_btn);
        logo=findViewById(R.id.register_logo);

        logo=findViewById(R.id.logo);

        //animation code

       // Animation animation1= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.move);
       // logo.setAnimation(animation1);




        phoneauthbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                phoneAuth();
            }


        });



    }
    private void phoneAuth() {

        startActivity(new Intent(getApplicationContext(),PhoneAuthActivity.class));
    }
}
