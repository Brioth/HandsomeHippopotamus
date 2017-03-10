package com.pxlopdracht.security.activities;

import android.util.Log;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;

/**
 * Created by ccele on 1/21/2017.
 */

public class RSAKeys {
    static final String TAG = "RSAKeys";


    private Key privateKey;
    private Key publicKey;

    public RSAKeys(){
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(1024);
            KeyPair kp = kpg.genKeyPair();
            publicKey = kp.getPublic();
            privateKey = kp.getPrivate();
        } catch (Exception e) {
            Log.e(TAG, "RSA key pair error");
        }
    }

    public Key GetOwnPrivateKey(){
        return privateKey;
    }

    public Key GetOwnPublicKey(){
        return publicKey;
    }

}
