package com.project.cs496.tab_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TabC_Fragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */

    public TabC_Fragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static TabC_Fragment newInstance() {
        TabC_Fragment fragment = new TabC_Fragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_c_fragment, container, false);

        SharedPreferences setting;
        setting = getActivity().getSharedPreferences("User_setting",0);

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab3);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),SettingActicity.class);
                startActivity(intent);
            }
        });


        return rootView;
    }
}