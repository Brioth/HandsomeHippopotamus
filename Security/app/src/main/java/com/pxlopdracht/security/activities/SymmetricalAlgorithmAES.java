package com.pxlopdracht.security.activities;

import android.util.Base64;
import android.util.Log;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by ccele on 1/21/2017.
 * Source: http://www.developer.com/ws/android/encrypting-with-android-cryptography-api.html
 */

public class SymmetricalAlgorithmAES{
    static final String TAG = "SymmetricAlgorithmAES";
    private String encryptedText;
    private String decryptedText;
    private SecretKeySpec aesKey;

    public SymmetricalAlgorithmAES() {
        GenerateAESKey();
    }

    public void GenerateAESKey(){
        // Set up secret key spec for 128-bit AES encryption and decryption
        SecretKeySpec sks = null;
        try {
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
            sr.setSeed("any data used as random seed".getBytes());
            KeyGenerator kg = KeyGenerator.getInstance("AES");
            kg.init(128, sr);
            sks = new SecretKeySpec((kg.generateKey()).getEncoded(), "AES");
        } catch (Exception e) {
            Log.e(TAG, "AES secret key spec error");
        }
    }

    public void EncryptAES(String original){
        // Encode the original data with AES
        byte[] encodedBytes = null;
        try {
            Cipher c = Cipher.getInstance("AES");
            c.init(Cipher.ENCRYPT_MODE, aesKey);
            encodedBytes = c.doFinal(original.getBytes());
        } catch (Exception e) {
            Log.e(TAG, "AES encryption error");
        }

        encryptedText = Base64.encodeToString(encodedBytes, Base64.DEFAULT);
    }

    public void DecryptAES(String scrambled){
        // Decode the encoded data with AES
        byte[] decodedBytes = null;
        try {
            Cipher c = Cipher.getInstance("AES");
            c.init(Cipher.DECRYPT_MODE, aesKey);
            decodedBytes = c.doFinal(scrambled.getBytes());
        } catch (Exception e) {
            Log.e(TAG, "AES decryption error");
        }

        decryptedText = new String(decodedBytes);
    }


    public String GetEncryptedTextAES(){
        return encryptedText;
    }
    public String GetDecryptedTextAES(){
        return decryptedText;
    }
}
