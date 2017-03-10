package com.pxl.opdrachten.securityapp.services.RSA;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;

public class GenerateRSAKeys extends Service {

    static final String TAG = "RSAKeys";

    private PrivateKey privateKey;
    private PublicKey publicKey;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void CreateRSAKeys(){
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(2048);
            KeyPair kp = kpg.genKeyPair();
            publicKey = kp.getPublic();
            privateKey = kp.getPrivate();
        } catch (Exception e) {
            Log.e(TAG, "RSA key pair error");
        }
    }


    public PrivateKey GetPrivateKey(){
        return privateKey;
    }

    public PublicKey GetPublicKey() {
        return publicKey;
    }
}
