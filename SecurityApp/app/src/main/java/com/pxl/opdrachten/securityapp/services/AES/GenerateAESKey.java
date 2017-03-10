package com.pxl.opdrachten.securityapp.services.AES;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.security.SecureRandom;

import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

import static android.content.ContentValues.TAG;

public class GenerateAESKey extends Service {

    private SecretKeySpec sks;

    public GenerateAESKey() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void CreateAESKey(){
        // Set up secret key spec for 128-bit AES encryption and decryption
        try {
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
            sr.setSeed("This seed supplements to the existing seed".getBytes());
            KeyGenerator kg = KeyGenerator.getInstance("AES");
            kg.init(256,sr);
            sks = new SecretKeySpec((kg.generateKey()).getEncoded(), "AES");
        } catch (Exception e) {
            Log.e(TAG, "AES secret key spec error");
        }
    }


    public SecretKeySpec GetAESKey(){
        return sks;
    }

}
