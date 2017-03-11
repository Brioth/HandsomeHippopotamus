package com.pxl.opdrachten.securityapp.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.security.KeyPairGeneratorSpec;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pxl.opdrachten.securityapp.R;
import com.pxl.opdrachten.securityapp.enums.LoginState;
import com.pxl.opdrachten.securityapp.services.AES.AESDecryption;
import com.pxl.opdrachten.securityapp.services.AES.AESEncryption;
import com.pxl.opdrachten.securityapp.services.KeystoreService;
import com.pxl.opdrachten.securityapp.services.SharedPreferencesService;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.interfaces.RSAPublicKey;
import java.util.Calendar;
import java.util.Enumeration;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.security.auth.x500.X500Principal;

import static android.R.attr.data;
import static android.R.attr.key;

/**
 * Created by ccele on 3/6/2017.
 */

public class PasswordAcitvity extends AppCompatActivity {
    private String tempUsr = "";
    private String tempPass = "";

    private String secret = "YoTHJNZsJmL99DzkROI7gslw4F855ngBODb3uPa/BcEcSpKVP7bGfSNlmy3KplAZZdVNnyr+BrEnbjAIPLsEoixvm6dxjI1oPMWEV3bvmH6g6dxMxVH3JBuFImGTbe3ZOX03h/7MU9F00G2iLjMeNJ0c/LxrYUKYp+Yo+WgNgu8=";

    private LoginState state = LoginState.Default;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
    }

    public void ResolvePassword(View v) throws Exception{

        EditText editUsr = (EditText) findViewById(R.id.editUsr);
        EditText editPass = (EditText)findViewById(R.id.editPass);

        tempUsr = tempUsr == "" ? (editUsr).getText().toString() : tempUsr;
        tempPass = tempPass == "" ? (editPass).getText().toString() : tempPass;

        String usPas = tempUsr + getResources().getString(R.string.ARBITRARY_NUMBER_SEQUENCE) + tempPass;

        String s = SharedPreferencesService.getPref(getResources().getString(R.string.LOGINKEY),this);

        String secret = KeystoreService.decrypt(getResources().getString(R.string.LOGINKEY), s);

        if(usPas.equals(secret)){
            Intent intent = new Intent(this, NumberActivity.class);
            startActivity(intent);
        }
    }
}

