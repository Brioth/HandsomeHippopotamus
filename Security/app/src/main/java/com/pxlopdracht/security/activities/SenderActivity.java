package com.pxlopdracht.security.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;


import com.pxlopdracht.security.security.R;
//source = http://stackoverflow.com/questions/2020088/sending-email-in-android-using-javamail-api-without-using-the-default-built-in-a

public class SenderActivity extends Activity {
    public final static String EXTRA_MESSAGE = "com.pxlopdracht.security.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sender);
    }

    public void sendUnencrypted(View view){
        try{
            GMailSender sender = new GMailSender("username@gmail.com","password");
            sender.sendMail("SuperSecretMessage",
                    "This is Body",
                    "sender@gmail.com",
                    "user@yahoo.com");
        } catch (Exception e){
            Log.e("SendMail", e.getMessage(), e);
        }
    }

    public void sendEncrypted(View view){
        Intent intent = new Intent(this, AttachmentActivity.class);
        EditText textBox = (EditText) findViewById(R.id.messageInputBox);
        String message = textBox.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

}
