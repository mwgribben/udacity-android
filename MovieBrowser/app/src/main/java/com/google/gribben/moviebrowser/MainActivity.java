package com.google.gribben.moviebrowser;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.squareup.picasso.Picasso;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity
    implements AdapterView.OnItemClickListener,
    AdapterView.OnItemSelectedListener {
    List<movie> movieList = new ArrayList<movie>();
    List<String> posters = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        movieGetter mg = new movieGetter(this);
        movieList = mg.getMovies();
        for (movie obj : movieList) {
            posters.add(obj.poster);
        }
        GridView g = (GridView) findViewById(R.id.posterGrid);
        PosterAdapter adap = new PosterAdapter(MainActivity.this, movieList);
        g.setAdapter(adap);
        g.setOnItemClickListener(MainActivity.this);
        adap.notifyDataSetChanged();

        Spinner spinner = (Spinner) findViewById(R.id.sortSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sort_array,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        Log.d("GribTracking", "onCreate");
    }


    public void sendToast(String toastString) {
        Context context = getApplicationContext();
        Toast toast = Toast.makeText(context, toastString, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        Log.d("GribTracking","ItemSelected");
        switch (pos) {
            case 0:
                Collections.sort(movieList, new Comparator<movie>() {
                    @Override
                    public int compare(movie m1, movie m2) {
                        return (int) (10*m2.popularity - 10*m1.popularity);
                    }

                });
                posters.clear();
                for (movie obj : movieList) {
                    posters.add(obj.poster);
                }
                break;

            case 1:
                Collections.sort(movieList, new Comparator<movie>() {
                    @Override
                    public int compare(movie m1, movie m2) {
                        return (int) (10*m2.vote - 10*m1.vote);
                    }

                });
                posters.clear();
                for (movie obj : movieList) {
                    posters.add(obj.poster);
                }
                break;
        }
        GridView g = (GridView) findViewById(R.id.posterGrid);
        PosterAdapter adap = (PosterAdapter) g.getAdapter();
        adap.notifyDataSetChanged();
        g.invalidateViews();
    }

    public void onNothingSelected(AdapterView<?> parent) {
        sendToast("Nout");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        Intent intent = new Intent(getApplicationContext(),DetailsActivity.class);
        movie select = movieList.get(position);
        intent.putExtra("movie",select);
        startActivity(intent);
    }
}
