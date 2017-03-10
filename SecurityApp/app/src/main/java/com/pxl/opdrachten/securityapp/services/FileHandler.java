package com.pxl.opdrachten.securityapp.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileHandler extends Service {
    private static final String TAG = "FileHandler";

    public FileHandler() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

//    public static void ClearFile(Context context, String fileName){
//        try{
//                context.deleteFile(fileName);
//            Toast.makeText(context, "File created", Toast.LENGTH_SHORT).show();
//            }
//            catch (Exception e){
//                Log.d(TAG, e.getMessage());
//            }
//
//    }

    public static String ReadFile(Context context, String fileName) {
        StringBuilder finalString = new StringBuilder();

        try {
            FileInputStream inputStream = context.openFileInput(fileName);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String oneLine;

            while ((oneLine=bufferedReader.readLine())!=null){
                finalString.append(oneLine);
            }

            bufferedReader.close();
            inputStream.close();
            inputStreamReader.close();
        } catch (IOException e) {
            Log.d(TAG, e.getMessage());
        } finally {
            return finalString.toString();
        }
    }

    public static void WriteNewFile(Context context, String fileName, String text){
        try{

            FileOutputStream outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(text.getBytes());
            outputStream.close();
        } catch (Exception e){
            Log.d(TAG, e.getMessage());
        }
    }

    public static void AppendText(Context context, String fileName, String text){
        try{

            FileOutputStream outputStream = context.openFileOutput(fileName, Context.MODE_APPEND);
            outputStream.write(text.getBytes());
            outputStream.close();
        } catch (Exception e){
            Log.d(TAG, e.getMessage());
        }
    }

    public static void WriteBytesToFile(Context context, String fileName, byte[] text ){
        try{
//            ClearFile(context, fileName);

            FileOutputStream outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(text);
            outputStream.close();
        } catch (Exception e){
            Log.d(TAG, e.getMessage());
        }
    }

    public static byte[] ReadBytesFromFile(Context context, String fileName){
        File file = new File(context.getFilesDir(),fileName);
        byte[] bytes = new byte[(int)file.length()];
    try{

        FileInputStream fis = new FileInputStream(file);
        DataInputStream dis = new DataInputStream(fis);

    //    bytes = new byte[dis.available()];

        dis.readFully(bytes);
        dis.close();
    } catch (Exception e){
        Log.d(TAG, e.getMessage());
    } finally {
        return bytes;
    }




    }

}
