package com.project.cs496.tab_app;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.ArrayAdapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class TabA_Fragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */

    public TabA_Fragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static TabA_Fragment newInstance() {
        TabA_Fragment fragment = new TabA_Fragment();


        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        InputStream inputStream = getResources().openRawResource(R.raw.address);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        ArrayList<Person> data = new ArrayList<Person>();


        int ctr;
        try{
            ctr = inputStream.read();
            while(ctr != -1) {
                byteArrayOutputStream.write(ctr);
                ctr = inputStream.read();
            }
        }catch (IOException e){
            e.printStackTrace();
        }

     //   Log.v("Text Data", byteArrayOutputStream.toString());

        try {
            // Parse the data into jsonobject to get original data in form of json.
            JSONObject jObject = new JSONObject(
                    byteArrayOutputStream.toString());

            JSONArray jArray = jObject.getJSONArray("address");

            String Name = "";
            String PhoneNumber = "";

            for (int i = 0; i < jArray.length(); i++) {
                Name = jArray.getJSONObject(i).getString("name");
                PhoneNumber = jArray.getJSONObject(i).getString("phone_number");
            //    Log.v("Name", Name);
           //     Log.v("PhoneNumer", PhoneNumber);
                data.add(new Person(Name,PhoneNumber));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        View rootView = inflater.inflate(R.layout.tab_a_fragment, container, false);
        RecyclerView leView = (RecyclerView) rootView.findViewById(R.id.address_book);
        AddressAdapter le_Adapter = new AddressAdapter(data,R.layout.tab_a_row);
        leView.setAdapter(le_Adapter);
        leView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        leView.setItemAnimator(new DefaultItemAnimator());


       /* FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();


            }
        });*/


        return rootView;
    }
}