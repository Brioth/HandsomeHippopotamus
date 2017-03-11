package com.pxlopdracht.security.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.security.KeyChain;

import com.pxlopdracht.security.security.R;

import java.security.KeyStore;
import java.util.ArrayList;
import java.util.Enumeration;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Enumeration<String> aliases = null;
        try {
            KeyStore ks = KeyStore.getInstance("AndroidKeyStore");
            ks.load(null);
            aliases = ks.aliases();

        }catch (Exception e) {

        }


        if(aliases != null) {
            setContentView(R.layout.activity_login);
        }else{
            setContentView(R.layout.activity_register);
        }
    }
}
