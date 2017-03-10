package com.pxlopdracht.security.services;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by ccele on 1/21/2017.
 */

public class FileManagementService {

        public static String ReadFile(Context context, String fileName) {
            String inputString = null;
            try {
                FileInputStream inputStream = context.openFileInput(fileName);
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder finalString = new StringBuilder();
                String oneLine;

                while ((oneLine=bufferedReader.readLine())!=null){
                    finalString.append(oneLine);
                }

                inputString = finalString.toString();

                bufferedReader.close();
                inputStream.close();
                inputStreamReader.close();



//                CreateIfNotExists(context, fileName);
//
//                BufferedReader inputReader = new BufferedReader(new InputStreamReader(
//                        context.openFileInput(fileName)));
//                StringBuffer stringBuffer = new StringBuffer();
//                while ((inputString = inputReader.readLine()) != null) {
//                    stringBuffer.append(inputString + "\n");
//                }
//
//                Log.d("TEXT", stringBuffer.toString());
//                inputReader.close();

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                return inputString;
            }
        }

    private static void CreateIfNotExists(Context context, String fileName){
        try {

            String ttt = "Dit is een testtext";

            File publicKeys = new File(fileName);
            publicKeys.createNewFile();
            FileOutputStream oFile = new FileOutputStream(publicKeys, false);

            try {
                FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_APPEND | Context.MODE_WORLD_READABLE);
                fos.write(ttt.getBytes());
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean WriteNewFile(Context context, String fileName, String text){
        try{
            FileOutputStream outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(text.getBytes());
            outputStream.close();
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static boolean AppendText(Context context, String fileName, String text){
        try{
            FileOutputStream outputStream = context.openFileOutput(fileName, Context.MODE_APPEND);
            outputStream.write(text.getBytes());
            outputStream.close();
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


}
