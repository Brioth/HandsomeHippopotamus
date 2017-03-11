package com.pxl.opdrachten.securityapp.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.TextView;

import com.pxl.opdrachten.securityapp.R;
import com.pxl.opdrachten.securityapp.services.KeystoreService;
import com.pxl.opdrachten.securityapp.services.SharedPreferencesService;

import org.w3c.dom.Text;

import java.util.Random;

public class InitActivity extends AppCompatActivity {

private boolean initialized = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);
    }

    public void Init(View v) throws Exception{

        if(!initialized){

        KeystoreService.createKeys(this, getResources().getString(R.string.LOGINKEY));
        KeystoreService.createKeys(this, getResources().getString(R.string.NUMBERKEY));

        String usr = random();
        String pass = random();

        String usrPass = usr + getResources().getString(R.string.ARBITRARY_NUMBER_SEQUENCE) + pass;
        String seq = randomSequence();

        SharedPreferencesService.putPref(getResources().getString(R.string.LOGINKEY), KeystoreService.encrypt(getResources().getString(R.string.LOGINKEY),usrPass), this);
        SharedPreferencesService.putPref(getResources().getString(R.string.NUMBERKEY), KeystoreService.encrypt(getResources().getString(R.string.NUMBERKEY),seq), this);

        ViewParent parent = (ViewParent) v.getParent();

        TextView t3 = ((TextView)((View)parent).findViewById(R.id.textView3));
        t3.setText(usr);
        TextView t4 = ((TextView)((View)parent).findViewById(R.id.textView4));
        t4.setText(pass);

        TextView t5 = ((TextView)((View)parent).findViewById(R.id.textView5));
        t5.setText(seq);

        Button b = ((Button) ((View)parent).findViewById(R.id.button));
        b.setText("LOGIN");
        initialized = true;}
        else{
            Intent intent = new Intent(this, PasswordAcitvity.class);
            startActivity(intent);
        }
    }

    public static String random() {
        char[] chars1 = "ABCDEF012GHIJKL345MNOPQR678STUVWXYZ9".toCharArray();
        StringBuilder sb1 = new StringBuilder();
        Random random1 = new Random();
        for (int i = 0; i < 6; i++)
        {
            char c1 = chars1[random1.nextInt(chars1.length)];
            sb1.append(c1);
        }
        String random_string = sb1.toString();
        return random_string;
    }

    public static String randomSequence(){
        String sequence = "";
        Random rand = new Random();
        for (int i = 0; i < 4; i++){
            sequence += rand.nextInt(9) + 1;
        }
        return  sequence;
    }
}
