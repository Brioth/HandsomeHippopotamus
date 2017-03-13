package com.pxl.opdrachten.securityapp.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.pxl.opdrachten.securityapp.R;

import java.util.ArrayList;
import java.util.List;

public class NumberActivity extends AppCompatActivity {

    private List<Integer> sequence = new ArrayList<>();
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number);

        Intent intent = getIntent();
        this.username = intent.getStringExtra("username");

    }

    public void AddToSequence(View v){
        Button b = (Button)v;
        String buttonText = b.getText().toString();

        sequence.add(Integer.parseInt(buttonText));

        int id = getResources().getIdentifier("cb"+sequence.size(), "id", getPackageName());
        CheckBox cb = (CheckBox) findViewById(id);

        cb.setChecked(true);

        if(sequence.size() == 4){
            ValidateSequence(v);
        }
    }

    private void ValidateSequence(View v){
        String numbers = "";

        for(int i : sequence){
            numbers += i;
        }

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String savedPin = sharedPref.getString(username+"Pin", "");

        if(savedPin.equals(numbers)){
            Snackbar.make(v, "Second login succesfull, almost there!",Snackbar.LENGTH_SHORT).show();

            Intent intent = new Intent(this, FingerprintActivity.class);
            startActivity(intent);
        }else{
            ClearSequence();

            Snackbar.make(v, "Wrong Pincode", Snackbar.LENGTH_SHORT).show();
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
