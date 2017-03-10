package com.pxl.opdrachten.securityapp.services.RSA;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.security.Key;

import javax.crypto.Cipher;

import static android.content.ContentValues.TAG;

public class RSAEncryption extends Service {
    public RSAEncryption() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public static byte[] EncryptRSA(byte[] original, Key key){
        byte[] encodedBytes = null;
        try {
            Cipher c = Cipher.getInstance("RSA");
            c.init(Cipher.ENCRYPT_MODE, key);
            encodedBytes = c.doFinal(original);
        } catch (Exception e) {
            Log.e(TAG, "RSA encryption error");
        }

        return encodedBytes;
    }
}
