package com.pxl.opdrachten.securityapp.services;

import android.graphics.Bitmap;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ccele on 2/18/2017.
 */

public class ImageHandler {

    public Bitmap EncodeBitmap(Bitmap bitmap, String message){

        //TODO check if message < bitmap size

        //Bitmap mutable = bitmap.copy(Bitmap.Config.RGB_565, true);

        ArrayList<Boolean> srcBin = ToBinArray(message.getBytes());

        ByteBuffer byteBuffer = ByteBuffer.allocate(bitmap.getByteCount());
        bitmap.copyPixelsToBuffer(byteBuffer);
        byte[] byteArray = byteBuffer.array();

        ArrayList<Byte> bytes = new ArrayList<>();

        for (int i = 0; i < 54; i++) {
            bytes.add(byteArray[i]);
        }

        for (int i = 54; i < byteArray.length; i++) {
            byte b = byteArray[i];

            b &= 0xFE;

            if (i-54<srcBin.size()){
                if(srcBin.get(i-54)){
                    b |= 1 << 0;
                }
            }

            bytes.add(b);

        }



        byte[] encodedByteArray = toByteArray(bytes);

        // Convert to Bitmap
        Bitmap.Config configBmp = Bitmap.Config.valueOf(bitmap.getConfig().name());
        Bitmap bitmap_tmp = Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight(), configBmp);
        ByteBuffer buffer = ByteBuffer.wrap(encodedByteArray);
        bitmap_tmp.copyPixelsFromBuffer(buffer);

        return bitmap_tmp;
    }

    public static byte[] toByteArray(List<Byte> in) {
        final int n = in.size();
        byte ret[] = new byte[n];
        for (int i = 0; i < n; i++) {
            ret[i] = in.get(i);
        }
        return ret;
    }

    private ArrayList<Boolean> ToBinArray(byte[] srcBin){

        ArrayList<Boolean> binary = new ArrayList<>();

        for (byte b : srcBin)
        {
            int val = b;
            for (int i = 0; i < 8; i++)
            {
                binary.add((val & 128) == 0 ? false : true);
                val <<= 1;
            }
        }

        return binary;
    }

    public String DecodeBitmap(Bitmap bitmap){

        ByteBuffer byteBuffer = ByteBuffer.allocate(bitmap.getByteCount());
        bitmap.copyPixelsToBuffer(byteBuffer);
        byte[] byteArray = byteBuffer.array();

        ArrayList<Boolean> bitValues = new ArrayList<>();

        int counter = 0;
        for (int i = 54; i< byteArray.length; i++) {
            byte b = byteArray[i];
            int bit = ((b >> 0 )&1);

            bitValues.add(bit == 1 ? true : false);

            counter = (bit == 0) ? counter + 1 : 0;

            if(counter == 16){
                break;
            }

        }

        byte[] byteArrayMessage = convertBoolArrayToByteArray(bitValues);

        return new String(byteArrayMessage);
    }

    private static byte boolsToByte(final ArrayList<Boolean> array, final int start) {
        byte b = 0;
        for (int i = 0; i < 8; i++) {
            if (array.get(start + i))
                b |= 1 << (7 - i);
        }
        return b;
    }

    public static byte[] convertBoolArrayToByteArray(ArrayList<Boolean> boolArr) {
        byte[] byteArr = new byte[boolArr.size()/8];
        for (int i = 0; i < byteArr.length; i++)
            byteArr[i] = boolsToByte(boolArr, 8*i);
        return byteArr;
    }
}
