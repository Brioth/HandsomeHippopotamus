package com.pxl.opdrachten.securityapp.services.AES;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.security.Key;

import javax.crypto.Cipher;

import static android.content.ContentValues.TAG;

public class AESEncryption extends Service {

    public AESEncryption() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public static byte[] EncryptAES(byte[] original, Key key){
        // Encode the original data with AES
        byte[] encodedBytes = null;
        try {
            Cipher c = Cipher.getInstance("AES");
            c.init(Cipher.ENCRYPT_MODE, key);
            encodedBytes = c.doFinal(original);
        } catch (Exception e) {
            Log.e(TAG, "AES encryption error");
        }

        return encodedBytes;
    }

}
