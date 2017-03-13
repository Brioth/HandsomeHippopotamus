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

import com.pxl.opdrachten.securityapp.R;
import com.pxl.opdrachten.securityapp.services.GenerateHash;

/**
 * Created by ccele on 3/6/2017.
 */

public class PasswordActivity extends AppCompatActivity {
    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
    }

    public void ResolvePassword(View v) throws Exception{

        EditText editUsr = (EditText)findViewById(R.id.usernameInput);
        EditText editPass = (EditText)findViewById(R.id.passwordInput);

        try {
            username = editUsr.getText().toString();
        }catch (Exception e){
            Snackbar.make(v, "Not a valid username", Snackbar.LENGTH_LONG).show();
        }

        try {
            password = editPass.getText().toString();
        }catch (Exception e){
            Snackbar.make(v, "Not a valid password", Snackbar.LENGTH_LONG).show();
        }


        String usrPas = username + password;
        String thisHash = Base64.encodeToString(GenerateHash.CreateHash(usrPas),Base64.DEFAULT).replace(" ", "");

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String savedHash = sharedPref.getString(username+"Pass", "").replace(" ", "");

        if(thisHash.equals(savedHash)){
            Snackbar.make(v, "First login succesfull", Snackbar.LENGTH_SHORT).show();

            Intent intent = new Intent(this, NumberActivity.class);
            intent.putExtra("username", username);
            startActivity(intent);
        } else {
            Snackbar.make(v, "Login failed, please try again", Snackbar.LENGTH_SHORT).show();
        }
    }

    public void goToCreate(View v){
        Intent intent = new Intent(this, CreateAccountActivity.class);
        startActivity(intent);
    }
}

