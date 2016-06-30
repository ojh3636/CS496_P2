package com.project.cs496.tab_app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.content.res.Resources;

public class TabB_Fragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */

    public TabB_Fragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static TabB_Fragment newInstance() {
        TabB_Fragment fragment = new TabB_Fragment();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_b_fragment, container, false);
        int img[] = {
                R.drawable.img1,R.drawable.img1,R.drawable.img1,R.drawable.img1,R.drawable.img1,R.drawable.img1,R.drawable.j,R.drawable.q,R.drawable.c,R.drawable.e,R.drawable.j,R.drawable.q,R.drawable.c,R.drawable.e,R.drawable.j,R.drawable.q,R.drawable.c,R.drawable.e,R.drawable.j,R.drawable.q,R.drawable.c,R.drawable.e,R.drawable.j,R.drawable.q,
                //R.drawable.img1,R.drawable.e,R.drawable.j,R.drawable.q,R.drawable.c,R.drawable.e,R.drawable.j,R.drawable.q,R.drawable.c,R.drawable.e,R.drawable.j,R.drawable.q,R.drawable.c,R.drawable.e,R.drawable.j,R.drawable.q,R.drawable.c,R.drawable.e,R.drawable.j,R.drawable.q,R.drawable.c,R.drawable.e,R.drawable.j,R.drawable.q,
        };

        MyAdapter adapter = new MyAdapter(getActivity().getApplicationContext(), R.layout.tab_b_row, img);

        GridView gv = (GridView)rootView.findViewById(R.id.gridView1);
        gv.setAdapter(adapter);

        final TextView tv = (TextView) rootView.findViewById(R.id.textView1);


        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view,
                                    int position, long id) {
                tv.setText("position : " + position);
            }
        });


        return rootView;
    }
}