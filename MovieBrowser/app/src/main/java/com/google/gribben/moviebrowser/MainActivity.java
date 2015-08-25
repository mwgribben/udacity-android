package com.google.gribben.moviebrowser;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class MainActivity extends Activity
    implements AdapterView.OnItemClickListener {
    private String api_key = "de5f1d0c1639b0481cd6b3b3f9af0efd";
    List<movie> movieList = new ArrayList<movie>();
    List<String> posters = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getMovies();

        Spinner spinner = (Spinner) findViewById(R.id.sortSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sort_array,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    public void getMovies () {

        Calendar today = Calendar.getInstance();

        Calendar end = (Calendar) today.clone();
        end.add(Calendar.MONTH, 1);
        Calendar start = (Calendar) today.clone();
        start.add(Calendar.MONTH, -1);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        JsonObjectRequest request = new JsonObjectRequest(
                "http://api.themoviedb.org/3/discover/movie?api_key="
                        +api_key
                        +"&primary_release_date.gte="
                        +df.format(start.getTime())
                        +"&primary_release_date.lte="
                        +df.format(end.getTime())
                        +"&sort_by=popularity_desc",
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray results;
                            results = response.getJSONArray("results");
                            for (int i = 0; i < results.length(); i++) {
                                JSONObject result = results.getJSONObject(i);
                                movieList.add(
                                        new movie(
                                                result.getString("original_title"),
                                                result.getDouble("vote_average"),
                                                result.getDouble("popularity"),
                                                result.getString("overview"),
                                                result.getString("poster_path"),
                                                result.getString("release_date")
                                        )
                                );
                                posters.add(result.getString("poster_path"));
                            }
                            GridView g = (GridView) findViewById(R.id.posterGrid);
                            g.setAdapter(new PosterAdapter(MainActivity.this, posters));
                            g.setOnItemClickListener(MainActivity.this);

                        } catch (JSONException e) {
                            raiseError(e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        raiseError(error.toString());
                    }
                });

        VolleyApplication.getInstance().getRequestQueue().add(request);
    }

    public class movie {
        public String name;
        public double vote;
        public double popularity;
        public String overview;
        public String poster;
        public Calendar release;

        public movie(String name, double vote, double pop, String overview, String poster, String release) {
            this.name=name;
            this.vote=vote;
            this.popularity=pop;
            this.overview=overview;
            this.poster=poster;
            //this.release = release;
        }

    }

    public void raiseError(String errorString) {
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("Error");
        alertDialog.setMessage(errorString);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    public class SpinnerActivity extends Activity implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            switch (pos) {
                case 0:
                    Collections.sort(movieList, new Comparator<movie>() {
                        @Override
                        public int compare(movie m1, movie m2) {
                            return (int) Math.ceil(m2.popularity - m1.popularity);
                        }

                    });
                    break;

                case 1:
                    Collections.sort(movieList, new Comparator<movie>() {
                        @Override
                        public int compare(movie m1, movie m2) {
                            return (int) Math.ceil(m2.vote - m1.vote);
                        }

                    });
                    break;
            }

            posters.clear();
            for (movie obj : movieList) {
                posters.add(obj.poster);
            }
            GridView g = (GridView) findViewById(R.id.posterGrid);
            g.getAdapter().notify();


        }

        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        raiseError("Click");
    }
}
