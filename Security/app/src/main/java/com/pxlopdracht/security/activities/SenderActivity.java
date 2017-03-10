package com.pxlopdracht.security.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.pxlopdracht.security.security.R;
import com.pxlopdracht.security.services.FileManagementService;
//source = http://stackoverflow.com/questions/2020088/sending-email-in-android-using-javamail-api-without-using-the-default-built-in-a

public class SenderActivity extends Activity {
    public final static String EXTRA_MESSAGE = "com.pxlopdracht.security.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sender);

        FileManagementService fileManagementService = new FileManagementService();

        fileManagementService.ReadFile(this, "PublicKeysFile.txt");
    }

    public void sendUnencrypted(View view){
        try{
            String senderMail = "ccelen00@gmail.com";
            GMailSender sender = new GMailSender(senderMail,"Tafellaken0123");

            EditText receiverBox = (EditText) findViewById(R.id.receipientInputBox);
            String receiverMail = getEmail(receiverBox);

            EditText messageBox = (EditText) findViewById(R.id.messageInputBox);
            String message = messageBox.getText().toString();

            sender.sendMail("SuperSecretMessage",
                    message,
                    senderMail,
                    receiverMail);
        } catch (Exception e){
            Log.e("SendMail", e.getMessage(), e);
        }
    }

    private String getEmail(EditText inputField) {
        String uncheckedMail = inputField.getText().toString();
        if (uncheckedMail.contains("@") && uncheckedMail.contains("."))
            return uncheckedMail;
        else
            return "Please Enter a correct email-address";

    }

    public void sendEncrypted(View view){
        Intent intent = new Intent(this, AttachmentActivity.class);
        EditText textBox = (EditText) findViewById(R.id.messageInputBox);
        String message = textBox.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

}
