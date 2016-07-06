package com.project.cs496.tab_app;

import com.strongloop.android.loopback.callbacks.ListCallback;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.strongloop.android.loopback.RestAdapter;
import com.strongloop.android.loopback.callbacks.VoidCallback;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class TabA_Fragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private String user_id;
    private String user_token;
    private AddressRepository repository;

    ArrayList<Address> data = new ArrayList<Address>();
    private RecyclerView leView;
    public TabA_Fragment() {
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setUser_token(String user_token) {
        this.user_token = user_token;
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static TabA_Fragment newInstance(String receive_user_id,String receive_user_token) {

        TabA_Fragment fragment = new TabA_Fragment();
        fragment.setUser_id(receive_user_id);
        fragment.setUser_token(receive_user_token);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



       /* InputStream inputStream = getResources().openRawResource(R.raw.address);
          ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
*/
       /* ArrayList<Person> data = new ArrayList<Person>();


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

*/
        View rootView = inflater.inflate(R.layout.tab_a_fragment, container, false);
        leView = (RecyclerView) rootView.findViewById(R.id.address_book);

        RestAdapter adapter = new RestAdapter(getActivity(),"http://50.112.20.91/api");

        repository = adapter.createRepository(AddressRepository.class);
        repository.sync(user_token,user_id,new VoidCallback() {
            @Override
            public void onSuccess() {
                Log.d("hahad","hahaha1");
                repository.findAll(new ListCallback<Address>() {
                    @Override
                    public void onSuccess(List<Address> objects) {
                        data = (ArrayList<Address>) objects;
                        AddressAdapter le_Adapter = new AddressAdapter(data,R.layout.tab_a_row);
                        leView.setAdapter(le_Adapter);
                        leView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
                        leView.setItemAnimator(new DefaultItemAnimator());

                    }

                    public void onError(Throwable t){

                    }

                });

            }

            @Override
            public void onError(Throwable t) {
                Log.d("hahad","hahaha2");

            }
        });



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