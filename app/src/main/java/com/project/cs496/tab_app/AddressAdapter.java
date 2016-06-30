package com.project.cs496.tab_app;

import java.util.ArrayList;
import java.util.zip.Inflater;

import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;

import android.support.v7.widget.RecyclerView.ViewHolder;
/**
 * Created by q on 2016-06-29.
 */
public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder>{

    private ArrayList<Person> items;
    private int itemLayout;

    public AddressAdapter(ArrayList<Person> items,int itemLayout) {
        this.items = items;
        this.itemLayout = itemLayout;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup,int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(itemLayout,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder,int position) {
        Person p = items.get(position);
        viewHolder.top.setText(p.getName());
        viewHolder.bottom.setText(p.getPhoneNumber());



    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView top;
        public TextView bottom;
        public ImageView pic;

        public ViewHolder(View itemView) {
            super(itemView);

            top = (TextView)itemView.findViewById(R.id.top);
            bottom = (TextView)itemView.findViewById(R.id.bottom);

        }

    }


}
