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
import static com.pxl.opdrachten.securityapp.services.RSA.RSAEncryption.EncryptRSA;

/**
 * A simple {@link Fragment} subclass.
 */
public class AliceFragment extends Fragment {

    private EditText eTAlice;

    public AliceFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_alice, container, false);
        eTAlice = (EditText) view.findViewById(R.id.AliceTextbox);
        Button btnSend = (Button) view.findViewById(R.id.AliceSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AliceSend(v);
            }
        });
        Button btnRead = (Button) view.findViewById(R.id.AliceRead);
        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AliceRead(v);
            }
        });
        return view;
    }

    public void AliceSend(View v){
        //Generate AESKey Alice
        GenerateAESKey aesGeneratorAlice = new GenerateAESKey();
        aesGeneratorAlice.CreateAESKey();

        //Get message Alice
        String originalMessage = eTAlice.getText().toString();

        //Encrypt message with AESKey
        byte[] encryptedMessage = AESEncryption.EncryptAES(originalMessage.getBytes(),aesGeneratorAlice.GetAESKey());

        //Save encrypted message to File_1
        FileHandler.WriteBytesToFile(this.getContext(), getString(R.string.encryptedText_A), encryptedMessage);

        //Get public key Bob
        PublicKey publicKeyBob = ReadPublicKey(getString(R.string.public_B));

        //Encrypt AESKey with public key Bob and save to File_2
        byte[] encryptedAESKeyAsBytes = EncryptRSA(aesGeneratorAlice.GetAESKey().getEncoded(), publicKeyBob);
        FileHandler.WriteBytesToFile(this.getContext(), getString(R.string.encryptedAESKey_A), encryptedAESKeyAsBytes);

        //Hash original message
        byte[] originalHash = GenerateHash.CreateHash(originalMessage);

        //Encrypt hash with private key Alice and save to File_3
        byte[] encryptedHash = RSAEncryption.EncryptRSA(originalHash, ReadPrivateKey(getString(R.string.private_A)));
        FileHandler.WriteBytesToFile(this.getContext(),getString(R.string.hash_A), encryptedHash);

        //Clear textfield
        eTAlice.setText("");

        //Done
        Snackbar.make(getView(), "Message send", Snackbar.LENGTH_SHORT).show();

    }

    public void AliceRead(View view){
        File file_1 = new File(getContext().getFilesDir(), getString(R.string.encryptedText_B));
        if (file_1.exists()) {
            //Get private key Alice
            PrivateKey privateKeyAlice = ReadPrivateKey(getString(R.string.private_A));

            //Decrypt public key Bob
            byte[] encryptedAESKeyAsBytes = FileHandler.ReadBytesFromFile(getContext(), getString(R.string.encryptedAESKey_B));
            byte[] decryptedAESKeyAsBytes = RSADecryption.DecryptRSA(encryptedAESKeyAsBytes, privateKeyAlice);

            //Get aesKey Bob with public key Bob
            SecretKeySpec aesKeyBob = new SecretKeySpec(decryptedAESKeyAsBytes, "AES");

            //Decrypt File_1 with aesKey Bob
            byte[] encryptedMessage = FileHandler.ReadBytesFromFile(getContext(), getString(R.string.encryptedText_B));
            byte[] originalMessage = (AESDecryption.DecryptAES(encryptedMessage, aesKeyBob));

            //Show decrypted message
            eTAlice.setText(new String(originalMessage));

            //bereken hash boodschap
            byte[] calculatedHash = GenerateHash.CreateHash(new String(originalMessage));

            //decrypteer file3 met publicB
            PublicKey publicKey_B = ReadPublicKey(getString(R.string.public_B));

            byte[] encryptedHashFromFile = FileHandler.ReadBytesFromFile(getContext(), getString(R.string.hash_B));
            byte[] decryptedHashFromFile = RSADecryption.DecryptRSA(encryptedHashFromFile, publicKey_B);

            //Controleer de 2 hashes en geef boodschap
            if (Arrays.equals(calculatedHash, decryptedHashFromFile)) {
                Snackbar.make(getView(), "Bericht is origineel", Snackbar.LENGTH_SHORT).show();
            } else {
                Snackbar.make(getView(), "Bericht is niet origineel", Snackbar.LENGTH_LONG).show();

            }
        } else {
            Snackbar.make(getView(), "Bob heeft niets te zeggen", Snackbar.LENGTH_LONG).show();
        }


    }

    public PublicKey ReadPublicKey(String fileName){
        PublicKey key = null;

        byte[] encryptedKeyAsBytes = FileHandler.ReadBytesFromFile(this.getContext(), fileName);
        try{
            X509EncodedKeySpec spec = new X509EncodedKeySpec(encryptedKeyAsBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            key = keyFactory.generatePublic(spec);
        }catch (Exception e){
            Log.d(TAG, e.getMessage());
        }
        return key;

    }
    private PrivateKey ReadPrivateKey(String fileName){
        PrivateKey key = null;
        byte[] encryptedKeyAsBytes = FileHandler.ReadBytesFromFile(this.getContext(), fileName);

        try{
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(encryptedKeyAsBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            key = keyFactory.generatePrivate(spec);
        }catch (Exception e){
            Log.d(TAG, e.getMessage());
        }
        return key;
    }

}
