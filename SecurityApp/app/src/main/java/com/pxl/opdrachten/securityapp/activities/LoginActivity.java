package com.pxl.opdrachten.securityapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.security.KeyStore;
import java.util.Enumeration;

public class LoginActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "EXTRA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Enumeration<String> aliasesKeys = null;
        try {
            KeyStore ks = KeyStore.getInstance("AndroidKeyStore");
            ks.load(null);
            aliasesKeys = ks.aliases();

//            while (aliasesKeys.hasMoreElements()){
//                KeystoreService.Delete(aliasesKeys.nextElement().toString());
//            }
        }catch (Exception e) {

        }

        if(aliasesKeys.hasMoreElements()) {
            Intent intent = new Intent(this, PasswordActivity.class);
            startActivity(intent);
        }
        else
        {
            Intent intent = new Intent(this, CreateAccountActivity.class);
            startActivity(intent);
        }
    }
}
