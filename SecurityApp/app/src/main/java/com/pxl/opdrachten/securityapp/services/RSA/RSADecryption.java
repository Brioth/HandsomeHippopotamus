package com.pxl.opdrachten.securityapp.services.RSA;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.security.Key;

import javax.crypto.Cipher;

import static android.content.ContentValues.TAG;

public class RSADecryption extends Service {
    public RSADecryption() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public static byte[] DecryptRSA(byte[] encryptedBytes, Key key){
        byte[] decodedBytes = null;
        try {
            Cipher c = Cipher.getInstance("RSA");
            c.init(Cipher.DECRYPT_MODE, key);
            decodedBytes = c.doFinal(encryptedBytes);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }

        return decodedBytes;
    }
}
