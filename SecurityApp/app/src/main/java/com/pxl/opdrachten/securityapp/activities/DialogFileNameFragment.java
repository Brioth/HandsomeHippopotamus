package com.pxl.opdrachten.securityapp.activities;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.widget.EditText;

import java.io.File;
import java.io.FileOutputStream;

/**
 * A simple {@link Fragment} subclass.
 */
public class DialogFileNameFragment extends DialogFragment  {
    private static final int FILE_NAME_PICKED = 3;
    private Bitmap secretImage;

    public void SetImage(Bitmap secretImage){
        this.secretImage = secretImage;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        LayoutInflater inflater = getActivity().getLayoutInflater();
//
//        builder.setView(inflater.inflate(R.layout.fragment_dialog, null));
//        builder.setMessage("Set a filename");
//        builder.setPositiveButton("Save", new DialogInterface.OnClickListener(){
//            public void onClick(DialogInterface dialog, int id){
//
//                EditText eTDialog = (EditText) getView().findViewById(R.id.dialogInput);
//                String fileName = eTDialog.getText().toString();
//                File dir = getActivity().getFilesDir();
//
//                File file = new File(dir,fileName);
//                if (file.exists()){
//                    Snackbar.make(getView(), "Filename already exists", Snackbar.LENGTH_LONG).show();
//                } else {
//                    try {
//                        FileOutputStream out = new FileOutputStream(file);
//                        secretImage.compress(Bitmap.CompressFormat.JPEG, 90, out);
//                        out.flush();
//                        out.close();
//                        Snackbar.make(getView(), "Image saved", Snackbar.LENGTH_SHORT).show();
//                    } catch (Exception e){
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });
//        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
//            public void onClick(DialogInterface dialog, int id){
//                Snackbar.make(getView(),"Image not saved",Snackbar.LENGTH_LONG).show();
//            }
//        });

        final EditText editName = new EditText(getActivity());
        editName.setInputType(InputType.TYPE_CLASS_TEXT);

        return new AlertDialog.Builder(getActivity())
                .setTitle("Title")
                .setMessage("Set a filename")
                .setView(editName)
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing (will close dialog)
                    }
                })
                .setPositiveButton(android.R.string.yes,  new DialogInterface.OnClickListener() {
                    @Override
                  public void onClick(DialogInterface dialog, int which) {
                        //File dir = getActivity().getFilesDir();
                        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

//                        if(dir.exists()){
//                            dir.mkdir();
//                        }

                        String name = editName.getText().toString();

                        if(!name.endsWith(".jpeg")){
                            name = name + ".jpeg";
                        }


                        File file = new File(dir,name);


                        if (file.exists()){
//                            Snackbar.make(getView(), "Filename already exists", Snackbar.LENGTH_LONG).show();
                        } else {
                            try {
                                FileOutputStream out = new FileOutputStream(file);

                                secretImage.compress(Bitmap.CompressFormat.JPEG, 90, out);
                                out.flush();
                                out.close();
                                //Snackbar.make(getView(), "Image saved", Snackbar.LENGTH_SHORT).show();
                            } catch (Exception e){
                                e.printStackTrace();
                                //Snackbar.make(getView(), "Something went wrong", Snackbar.LENGTH_SHORT).show();
                            }
                        }
                    }
                })
                .create();

        //return builder.create();
    }
}
