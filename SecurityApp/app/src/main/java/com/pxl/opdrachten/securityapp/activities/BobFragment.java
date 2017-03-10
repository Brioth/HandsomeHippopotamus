package com.pxl.opdrachten.securityapp.activities;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.pxl.opdrachten.securityapp.R;
import com.pxl.opdrachten.securityapp.services.AES.AESDecryption;
import com.pxl.opdrachten.securityapp.services.AES.AESEncryption;
import com.pxl.opdrachten.securityapp.services.AES.GenerateAESKey;
import com.pxl.opdrachten.securityapp.services.FileHandler;
import com.pxl.opdrachten.securityapp.services.GenerateHash;
import com.pxl.opdrachten.securityapp.services.RSA.RSADecryption;
import com.pxl.opdrachten.securityapp.services.RSA.RSAEncryption;

import java.io.File;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

import javax.crypto.spec.SecretKeySpec;

import static android.content.ContentValues.TAG;


public class BobFragment extends Fragment {

    private EditText textBox;

    public BobFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bob, container, false);
        textBox = (EditText) view.findViewById(R.id.BobTextbox);
        Button buttonSend = (Button) view.findViewById(R.id.BobSend);
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BobSend(v);
            }
        });
        Button buttonRead = (Button) view.findViewById(R.id.BobRead);
        buttonRead.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                BobRead(v);
            }
        });

        return view;
    }

    public void BobSend(View v){
        //Generate AESKey
        GenerateAESKey aesGeneratorBob = new GenerateAESKey();
        aesGeneratorBob.CreateAESKey();

        //Get message
        String originalMessage = textBox.getText().toString();

        //Encrypt message with AESKey
        byte[] encryptedMessage = AESEncryption.EncryptAES(originalMessage.getBytes(),aesGeneratorBob.GetAESKey());

        //Save encrypted message
        FileHandler.WriteBytesToFile(this.getContext(), getString(R.string.encryptedText_B), encryptedMessage);

        //Laadt public key van Alice
        PublicKey publicKeyAlice = ReadPublicKey(getString(R.string.public_A));

        //Encrypteer AESKey met public key van Alice en save in File_2
        byte[] encryptedAESKeyAsBytes = RSAEncryption.EncryptRSA(aesGeneratorBob.GetAESKey().getEncoded(), publicKeyAlice);
        FileHandler.WriteBytesToFile(this.getContext(), getString(R.string.encryptedAESKey_B), encryptedAESKeyAsBytes);

        //Hash original message
        byte[] originalHash = GenerateHash.CreateHash(originalMessage);

        //Encrypteer hash met private_B en save naar File_3
        byte[] encryptedHash = RSAEncryption.EncryptRSA(originalHash, ReadPrivateKey(getString(R.string.private_B)));
        FileHandler.WriteBytesToFile(this.getContext(),getString(R.string.hash_B), encryptedHash);

        Snackbar.make(getView(), "Message send", Snackbar.LENGTH_SHORT).show();

    }

    public void BobRead(View view){
        File file_1 = new File(getContext().getFilesDir(),getString(R.string.encryptedText_A));
        if (file_1.exists()) {

        //gebruik private_B om file2 te decrypteren, resultaat symm key
        PrivateKey privateKey_B = ReadPrivateKey(getString(R.string.private_B));

        byte[] encryptedAESKeyAsBytes = FileHandler.ReadBytesFromFile(getContext(), getString(R.string.encryptedAESKey_A));
        byte[] decryptedAESKeyAsBytes = RSADecryption.DecryptRSA(encryptedAESKeyAsBytes, privateKey_B);

        SecretKeySpec aesKeyAlice = new SecretKeySpec(decryptedAESKeyAsBytes, "AES");

        //decrypteer file1 met symm key, toon tekst
        byte[] encryptedMessage = FileHandler.ReadBytesFromFile(getContext(), getString(R.string.encryptedText_A));
        byte[] originalMessage = (AESDecryption.DecryptAES(encryptedMessage, aesKeyAlice));

        textBox.setText(new String(originalMessage));

        //bereken hash boodschap
        byte[] calculatedHash = GenerateHash.CreateHash(new String(originalMessage));

        //decrypteer file3 met publicA
        PublicKey publicKey_A = ReadPublicKey(getString(R.string.public_A));

        byte[] encryptedHashFromFile = FileHandler.ReadBytesFromFile(getContext(), getString(R.string.hash_A));
        byte[] decryptedHashFromFile = RSADecryption.DecryptRSA(encryptedHashFromFile,publicKey_A );

        //Controleer de 2 hashes en geef boodschap
        if(Arrays.equals(calculatedHash, decryptedHashFromFile)){
            Snackbar.make(getView(), "Bericht is origineel",Snackbar.LENGTH_SHORT).show();
        } else {
            Snackbar.make(getView(),"Bericht is niet origineel", Snackbar.LENGTH_LONG).show();

        }
        } else {
            Snackbar.make(getView(), "Alice heeft niets te zeggen", Snackbar.LENGTH_SHORT).show();
        }
    }

    public PublicKey ReadPublicKey(String fileName){
        PublicKey key = null;

        byte[] encryptedKey = FileHandler.ReadBytesFromFile(this.getContext(), fileName);
        try{
            X509EncodedKeySpec spec = new X509EncodedKeySpec(encryptedKey);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            key = keyFactory.generatePublic(spec);
        }catch (Exception e){
            Log.d(TAG, e.getMessage());
        }
        return key;

    }

    private PrivateKey ReadPrivateKey(String fileName){
        PrivateKey key = null;
        byte[] encryptedKey = FileHandler.ReadBytesFromFile(this.getContext(), fileName);

        try{
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(encryptedKey);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            key = keyFactory.generatePrivate(spec);
        }catch (Exception e){
            Log.d(TAG, e.getMessage());
        }
        return key;
    }

}
