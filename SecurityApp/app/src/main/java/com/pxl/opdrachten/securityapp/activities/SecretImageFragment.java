package com.pxl.opdrachten.securityapp.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.pxl.opdrachten.securityapp.R;
import com.pxl.opdrachten.securityapp.services.ImageHandler;

import java.io.ByteArrayOutputStream;
import java.io.File;


public class SecretImageFragment extends Fragment {
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1;
    private static final int IMAGE_PICKER_SELECT = 2;

    private static final int FILE_NAME_PICKED = 3;

    Button btnCapture;
    Button btnPickFile;
    Button btnSetMessage;
    Button btnGetMessage;
    EditText imgText;
    ImageView imgView;
    DialogFileNameFragment dialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_secret_image, container, false);

        imgView = (ImageView) view.findViewById(R.id.imgView);
        btnCapture = (Button) view.findViewById(R.id.btnCapture);
        btnPickFile = (Button) view.findViewById(R.id.btnPick);
        imgText = (EditText)view.findViewById(R.id.imgText);
        btnSetMessage = (Button) view.findViewById(R.id.btnSetMessage);
        btnGetMessage = (Button) view.findViewById(R.id.btnGetMessage);

        btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,
                        CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);

            }
        });

        btnPickFile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, IMAGE_PICKER_SELECT );
            }
        });

        btnSetMessage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String message = imgText.getText().toString();
                Bitmap bitmap = ((BitmapDrawable)imgView.getDrawable()).getBitmap();

                ImageHandler handler = new ImageHandler();
                Bitmap encodedBitmap = handler.EncodeBitmap(bitmap, message);
                imgView.setImageBitmap(encodedBitmap);

                //ask for filename
                showDialog();

                //save image

                Snackbar.make(getView(), "Secret image saved", Snackbar.LENGTH_SHORT).show();
            }
        });

        btnGetMessage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Bitmap bitmap = ((BitmapDrawable)imgView.getDrawable()).getBitmap();

                ImageHandler handler = new ImageHandler();

                String message = handler.DecodeBitmap(bitmap);

                imgText.setText(message);
            }
        });

        return view;

    }

    private void showDialog() {
        DialogFileNameFragment newFragment = new DialogFileNameFragment ();
        newFragment.SetImage(((BitmapDrawable)imgView.getDrawable()).getBitmap());
        newFragment.show(getActivity().getFragmentManager(), "dialogFileNameFragment");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {

                Bitmap bmp = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();

                bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();

                // convert byte array to Bitmap

                Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0,
                        byteArray.length);

                imgView.setImageBitmap(bitmap);

            }
        }

        if (requestCode == IMAGE_PICKER_SELECT && resultCode == Activity.RESULT_OK) {
           String path = getPathFromCameraData(data, this.getActivity());
            File image = new File(path);
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            Bitmap bitmap = BitmapFactory.decodeFile(image.getAbsolutePath(),bmOptions);
            imgView.setImageBitmap(bitmap);
        }
    }

    public static String getPathFromCameraData(Intent data, Context context) {
        Uri selectedImage = data.getData();
        String[] filePathColumn = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver().query(selectedImage,
                filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();
        return picturePath;
    }
}
