package com.google.gribben.moviebrowser;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gribben on 8/25/15.
 */
public class PosterAdapter extends BaseAdapter {

    private Context mContext;

    List<String> posters = new ArrayList<String>();

    public PosterAdapter(Context c, List<String> p) {
        mContext=c;
        posters=p;
    }

    @Override
    public int getCount() {
        return posters.size();
    }

    @Override
    public Object getItem(int position) {
        return posters.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        ImageView imageView = new ImageView(mContext);
        Picasso.with(mContext).load("http://image.tmdb.org/t/p/w500"+posters.get(position)).into(imageView);
        int width = (int) Math.floor(size.x/2.5);
        int height = (int) Math.floor(width * 1.5185);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, height);
        imageView.setLayoutParams(layoutParams);
        return imageView;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

}
