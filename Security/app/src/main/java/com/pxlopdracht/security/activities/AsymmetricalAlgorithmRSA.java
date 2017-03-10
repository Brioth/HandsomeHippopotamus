package com.pxlopdracht.security.activities;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import com.pxlopdracht.security.services.FileManagementService;

import java.security.Key;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

/**
 * Created by ccele on 1/21/2017.
 * source: http://www.developer.com/ws/android/encrypting-with-android-cryptography-api.html
 * source: http://stackoverflow.com/questions/11532989/android-decrypt-rsa-text-using-a-public-key-stored-in-a-file
 */

public class AsymmetricalAlgorithmRSA {
    static final String TAG = "AsymmetricAlgorithmRSA";
    private String encryptedText;
    private String decryptedText;

    private Context context;

    public AsymmetricalAlgorithmRSA(Context context) {
        this.context = context;
    }

    public void ReadPublicKey(){
        // reads the public key stored in a file
        String encryptedKey = FileManagementService.ReadFile(context, "publicKey.txt");
        encryptedKey = encryptedKey.replace("-----BEGIN PUBLIC KEY-----","");
        encryptedKey = encryptedKey.replace("-----END PUBLIC KEY-----","");
//        InputStream is = mContext.getResources().openRawResource(R.raw.sm_public);
//        BufferedReader br = new BufferedReader(new InputStreamReader(is));
//        List<String> lines = new ArrayList<>();
//        String line;
//        while ((line = br.readLine()) != null)
//            lines.add(line);
//
//        // removes the first and last lines of the file (comments)
//        if (lines.size() > 1 && lines.get(0).startsWith("-----") && lines.get(lines.size()-1).startsWith("-----")) {
//            lines.remove(0);
//            lines.remove(lines.size()-1);
//        }
//
//        // concats the remaining lines to a single String
//        StringBuilder sb = new StringBuilder();
//        for (String aLine: lines)
//            sb.append(aLine);
//        String keyString = sb.toString();
        Log.d("log", "keyString:"+encryptedKey);

        // converts the String to a PublicKey instance
        try{
            byte[] keyBytes = Base64.decode(encryptedKey.getBytes("utf-8"), Base64.DEFAULT);
            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey key = keyFactory.generatePublic(spec);
        }catch (Exception e){

        }

}

    public void EncryptRSA(String original, Key privateKey){
        // Encode the original data with RSA private key
        byte[] encodedBytes = null;
        try {
            Cipher c = Cipher.getInstance("RSA");
            c.init(Cipher.ENCRYPT_MODE, privateKey);
            encodedBytes = c.doFinal(original.getBytes());
        } catch (Exception e) {
            Log.e(TAG, "RSA encryption error");
        }

        encryptedText = Base64.encodeToString(encodedBytes, Base64.DEFAULT);
    }

    public void DecryptRSA(String scrambled, Key publicKey){
        // Decode the encoded data with RSA public key
        byte[] decodedBytes = null;
        try {
            Cipher c = Cipher.getInstance("RSA");
            c.init(Cipher.DECRYPT_MODE, publicKey);
            decodedBytes = c.doFinal(scrambled.getBytes());
        } catch (Exception e) {
            Log.e(TAG, "RSA decryption error");
        }

        decryptedText = new String(decodedBytes);
    }

    public String GetEncryptedTextRSA(){
        return encryptedText;
    }

    public String getDecryptedTextRSA(){
        return decryptedText;
    }
}