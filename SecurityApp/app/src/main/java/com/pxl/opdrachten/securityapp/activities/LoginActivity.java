package com.pxl.opdrachten.securityapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.pxl.opdrachten.securityapp.R;
import com.pxl.opdrachten.securityapp.services.KeystoreService;

import java.security.KeyStore;
import java.util.Enumeration;
import java.util.StringTokenizer;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class LoginActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "EXTRA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Enumeration<String> aliases = null;
        try {
            KeyStore ks = KeyStore.getInstance("AndroidKeyStore");
            ks.load(null);
            aliases = ks.aliases();

//            while (aliases.hasMoreElements()){
//                KeystoreService.Delete(aliases.nextElement().toString());
//            }
        }catch (Exception e) {

        }

        if(aliases.hasMoreElements()) {
            Intent intent = new Intent(this, PasswordAcitvity.class);
            startActivity(intent);
        }
        else
        {
            Intent intent = new Intent(this, InitActivity.class);
            startActivity(intent);
        }
    }
}
