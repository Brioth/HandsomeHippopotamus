package com.pxlopdracht.security.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.pxlopdracht.security.security.R;
import com.pxlopdracht.security.services.FileManagementService;

public class DecryptActivity extends AppCompatActivity {

    private String encryptedMessage;
    private String encryptedKey;
    private String encryptedHash;
    private String privateKey;
    private String publicKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decrypt);

        encryptedMessage= FileManagementService.ReadFile(this, "encryptedMessage.txt");
        encryptedKey = GetPublicKey();
        encryptedHash = FileManagementService.ReadFile(this, "encryptedMessage.txt");
        privateKey = FileManagementService.ReadFile(this, "encryptedMessage.txt");
        publicKey = FileManagementService.ReadFile(this, "encryptedMessage.txt");
    }


//    public String ReadFile(String file){
//        try{
//            BufferedReader inputReader = new BufferedReader(new InputStreamReader(openFileInput(file)));
//
//            String inputString;
//            StringBuffer stringBuffer = new StringBuffer();
//            while ((inputString = inputReader.readLine()) != null){
//                stringBuffer.append(inputString+"\n");
//            }
//            return  inputString;
//        } catch (IOException e){
//            e.printStackTrace();
//            return "";
//        }
//    }

    public boolean Decrypt(){
//        try{
//            //gebruik private_B om file_2 te decrypteren = unencrypted symetrische key
//
//            //bereken hash van boodschap
//            //gebruik public_A om file_3 te decrypteren = hash orginele file
//            //als hash hetzelfde is, ok -> pop up met boodschap
//            //als hash niet hetzelfde is -> boodschap is veranderd
//            return true;
//        }
//        catch (){
//
//            return false;
//        }

        return true;
    }
}
