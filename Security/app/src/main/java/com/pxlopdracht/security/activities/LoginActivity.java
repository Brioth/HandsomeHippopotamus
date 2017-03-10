package com.pxlopdracht.security.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.pxlopdracht.security.security.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //log automatisch in met gegevens
        //Indien manuele login check of er al een keypair gekoppeld is aan dit account
        //Indien niet, genereer keypair


    }

    public boolean GenerateRSAKeys(){
        try{
            RSAKeys keypair = new RSAKeys();

        }
    }
}
