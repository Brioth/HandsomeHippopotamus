package com.pxl.opdrachten.securityapp.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.pxl.opdrachten.securityapp.R;
import com.pxl.opdrachten.securityapp.services.FileHandler;

public class ShowFilesFragment extends Fragment {
    private TextView textView;
    private ListView listView;


    public ShowFilesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_show_files, container, false);
        textView = (TextView) view.findViewById(R.id.textView);
        textView.setMovementMethod(new ScrollingMovementMethod());
        listView = (ListView) view.findViewById(R.id.listView);


        String[] filesInFolder = getContext().fileList();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, filesInFolder);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int itemPosition = position;
                String itemValue = (String) listView.getItemAtPosition(position);
                textView.setText(FileHandler.ReadFile(getContext(), itemValue));
            }
        });



        return view;
    }


}
