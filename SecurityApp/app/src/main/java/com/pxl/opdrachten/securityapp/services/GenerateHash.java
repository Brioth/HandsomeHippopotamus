package com.pxl.opdrachten.securityapp.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.security.MessageDigest;

import static android.content.ContentValues.TAG;

public class GenerateHash extends Service {
    public GenerateHash() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    public static byte[] CreateHash(String message) {
        byte[] hashedBytes = null;
             try {
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                 digest.update(message.getBytes());
                hashedBytes = digest.digest();


            } catch (Exception e) {
                 Log.d(TAG, e.getMessage());
            } finally {
                 return hashedBytes;
             }
        }

//    private static String convertByteArrayToHexString(byte[] arrayBytes) {
//        StringBuffer stringBuffer = new StringBuffer();
//        for (int i = 0; i < arrayBytes.length; i++) {
//            stringBuffer.append(Integer.toString((arrayBytes[i] & 0xff) + 0x100, 16)
//                    .substring(1));
//        }
//        return stringBuffer.toString();
//    }
}
