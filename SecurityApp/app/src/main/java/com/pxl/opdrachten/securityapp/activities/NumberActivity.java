package com.pxl.opdrachten.securityapp.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import com.pxl.opdrachten.securityapp.R;
import com.pxl.opdrachten.securityapp.services.KeystoreService;
import com.pxl.opdrachten.securityapp.services.SharedPreferencesService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class NumberActivity extends AppCompatActivity {

    private List<Integer> sequence = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number);
    }

    public void AddToSequence(View v){
        Button b = (Button)v;
        String buttonText = b.getText().toString();

        sequence.add(Integer.parseInt(buttonText));

        int id = getResources().getIdentifier("cb"+sequence.size(), "id", getPackageName());
        CheckBox cb = (CheckBox) findViewById(id);

        cb.setChecked(true);

        if(sequence.size() == 4){
            ValidateSequence();
        }
    }

    private void ValidateSequence(){
        String numbers = "";

        for(int i : sequence){
            numbers += i;
        }

        String s = SharedPreferencesService.getPref(getResources().getString(R.string.NUMBERKEY),this);

        String secret = KeystoreService.decrypt(getResources().getString(R.string.NUMBERKEY), s);

        if(secret.equals(numbers)){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }else{
            ClearSequence();
        }

    }

    private void ClearSequence(){
        sequence = new ArrayList<>();

        for (int i = 1; i <= 4 ; i++) {
            int id = getResources().getIdentifier("cb"+i, "id", getPackageName());
            CheckBox cb = (CheckBox) findViewById(id);

            cb.setChecked(false);
        }
    }
}
