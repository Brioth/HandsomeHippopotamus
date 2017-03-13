package com.pxl.opdrachten.securityapp.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.pxl.opdrachten.securityapp.R;
import com.pxl.opdrachten.securityapp.services.GenerateHash;

import java.util.Random;

public class CreateAccountActivity extends AppCompatActivity {

    private String usr;
    private String pass;
    private String pin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
    }

    public void createAccount(View v) throws Exception{

            EditText userNameTextbox = (EditText) findViewById(R.id.usernameTextbox);
            if(!userNameTextbox.getText().toString().equals("")){

                //KeystoreService.createKeys(this, getResources().getString(R.string.LOGINKEY));
                //KeystoreService.createKeys(this, getResources().getString(R.string.NUMBERKEY));

                usr = userNameTextbox.getText().toString();
                pass = createRandomPassword();
                pin = createRandomPincode();

                String usrPass = Base64.encodeToString(GenerateHash.CreateHash(usr+pass),Base64.DEFAULT);

                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(usr+"Pass", usrPass);
                editor.apply();
                editor.putString(usr+"Pin", pin);
                editor.apply();

                showData();


                //SharedPreferencesService.putPref(getResources().getString(R.string.LOGINKEY), KeystoreService.encrypt(getResources().getString(R.string.LOGINKEY),usrPass), this);
                //SharedPreferencesService.putPref(getResources().getString(R.string.NUMBERKEY), KeystoreService.encrypt(getResources().getString(R.string.NUMBERKEY),pin), this);

            } else {
                Snackbar.make(v, "Please add a valid userName", Snackbar.LENGTH_LONG).show();
            }


    }

    public void Continue(View v){
        Intent intent = new Intent(this, PasswordActivity.class);
        startActivity(intent);
    }

    public String createRandomPassword() {
        char[] chars = "ABCDEFstuvwxyz012GHIJKLmnopqr345MNOPQRghijkl678STUVWXYZabcdef9".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 10; i++)
        {
            char c1 = chars[random.nextInt(chars.length)];
            sb.append(c1);
        }
        return sb.toString();
    }

    public String createRandomPincode(){
        String sequence = "";
        Random rand = new Random();
        for (int i = 0; i < 4; i++){
            sequence += rand.nextInt(9) + 1;
        }
        return  sequence;
    }

    public void showData(){
        TextView usernameLabel = (TextView)findViewById(R.id.usernameConfirmedLabel);
        usernameLabel.setVisibility(View.VISIBLE);
        TextView usernameView = (TextView)findViewById(R.id.usernameView);
        usernameView.setText(usr);
        usernameView.setVisibility(View.VISIBLE);

        TextView passwordLabel = (TextView)findViewById(R.id.passwordLabel);
        passwordLabel.setVisibility(View.VISIBLE);
        TextView passwordView = (TextView)findViewById(R.id.passwordView);
        passwordView.setText(pass);
        passwordView.setVisibility(View.VISIBLE);

        TextView pincodeLabel = (TextView)findViewById(R.id.pincodeLabel);
        pincodeLabel.setVisibility(View.VISIBLE);
        TextView pincodeView = (TextView)findViewById(R.id.pincodeView);
        pincodeView.setText(pin);
        pincodeView.setVisibility(View.VISIBLE);

        TextView safeKeepingMessage = (TextView)findViewById(R.id.safeKeepingMessage);
        safeKeepingMessage.setVisibility(View.VISIBLE);

    }

}
