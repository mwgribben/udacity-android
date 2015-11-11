package com.google.gribben.moviebrowser;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gribben on 11/10/15.
 */
public class trailerAdapter extends BaseAdapter {

    private Context mContext;

    List<String> keyList = new ArrayList<String>();

    public trailerAdapter(Context c, List<String> s) {
        mContext=c;
        keyList=s;
    }

    @Override
    public int getCount() {
        return keyList.size();
    }

    @Override
    public Object getItem(int position) {
        return keyList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        TextView textView;

        if(convertView==null) {
            textView = new TextView(mContext);
        } else {
            textView = (TextView) convertView;
        }

        textView.setText("Trailer " + (position+1));
        textView.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                mContext.startActivity(new Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("http://www.youtube.com/watch?v=" + getItem(position))
                ));
            }
        });
        return textView;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
