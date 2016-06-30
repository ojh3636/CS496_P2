package com.project.cs496.tab_app;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.GridView;
import android.widget.LinearLayout.LayoutParams;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.view.WindowManager;
class MyAdapter extends BaseAdapter {
    Context context;
    int layout;
    int img[];
    LayoutInflater inf;

    public MyAdapter(Context context, int layout, int[] img) {
        this.context = context;
        this.layout = layout;
        this.img = img;
        inf = (LayoutInflater) context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return img.length;
    }

    @Override
    public Object getItem(int position) {
        return img[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null)
            convertView = inf.inflate(layout, null);

        Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), img[position]);
        bmp = Bitmap.createScaledBitmap(bmp,320, 240, false);

        ImageView iv = (ImageView) convertView.findViewById(R.id.imageView1);
        iv.setAdjustViewBounds(true);
        iv.setImageBitmap(bmp);
        iv.setMaxWidth(320);
        iv.setMaxHeight(240);
        iv.setLayoutParams(new GridView.LayoutParams(250, 400));
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        iv.setPadding(5, 5, 5, 5);

        iv.setImageResource(img[position]);
        return iv;

    }
}