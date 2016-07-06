package com.project.cs496.tab_app;

import java.util.ArrayList;
import java.util.zip.Inflater;

import android.content.Context;
import android.media.Image;
import android.provider.ContactsContract;
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

import com.koushikdutta.ion.Ion;

/**
 * Created by q on 2016-06-29.
 */
public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder>{

    private ArrayList<Address> items;
    private int itemLayout;

    public AddressAdapter(ArrayList<Address> items,int itemLayout) {
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
        Address p = items.get(position);
        Ion.with(viewHolder.pic)
                .load(p.getPicture());
        viewHolder.top.setText(p.getName());
        viewHolder.bottom.setText(p.getUser_id());



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
            pic = (ImageView)itemView.findViewById(R.id.img1);
            top = (TextView)itemView.findViewById(R.id.top);
            bottom = (TextView)itemView.findViewById(R.id.bottom);

        }

    }


}
