package com.google.gribben.moviebrowser;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gribben on 8/25/15.
 */
public class PosterAdapter extends BaseAdapter {

    private Context mContext;

    List<movie> movieList = new ArrayList<movie>();
    List<String> posters = new ArrayList<String>();
    List<String> titles = new ArrayList<String>();

    public PosterAdapter(Context c, List<movie> m) {
        mContext=c;
        movieList=m;
    }

    @Override
    public int getCount() {
        return movieList.size();
    }

    @Override
    public Object getItem(int position) {
        return movieList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView imageView;

        if(convertView==null) {
            imageView = new ImageView(mContext);
            WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int width = (int) Math.floor(size.x/2.5);
            int height = (int) Math.floor(width * 1.5185);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, height);
            imageView.setLayoutParams(layoutParams);

        } else {
            imageView = (ImageView) convertView;
        }
        movie current = movieList.get(position);
        Picasso.with(mContext).load("http://image.tmdb.org/t/p/w500"+current.poster).error(new BitmapDrawable(TitleToPoster(current.name))).into(imageView);

        return imageView;
    }

    private Bitmap TitleToPoster(String text) {
        Paint paint = new Paint();
        paint.setTextSize(100);
        paint.setColor(Color.BLACK);
        paint.setTextAlign(Paint.Align.LEFT);
        float baseline = -paint.ascent(); // ascent() is negative
        int width = (int) (paint.measureText(text) + 0.5f); // round
        int height = (int) (baseline + paint.descent() + 0.5f);
        Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(image);
        canvas.drawText(text, 0, baseline, paint);
        Log.d("GribTracking","Bitmap made");
        return image;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

}
