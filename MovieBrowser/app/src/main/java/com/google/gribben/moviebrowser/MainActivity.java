package com.google.gribben.moviebrowser;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends Activity {

    private TextView mTextView;
    private String api_key = "de5f1d0c1639b0481cd6b3b3f9af0efd";

    List<movie> movieList = new ArrayList<movie>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getMovies();
    }

    public void getMovies () {
        mTextView = (TextView) findViewById(R.id.textView);

        Calendar today = Calendar.getInstance();

        Calendar end = (Calendar) today.clone();
        end.add(Calendar.MONTH, 1);
        Calendar start = (Calendar) today.clone();
        start.add(Calendar.MONTH,-1);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        JsonObjectRequest request = new JsonObjectRequest(
                "http://api.themoviedb.org/3/discover/movie?api_key="+api_key+"&primary_release_date.gte="+df.format(start.getTime())+"&primary_release_date.lte="+df.format(end.getTime()),
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray results;
                        try {
                            results = response.getJSONArray("results");

                            for (int i = 0; i < results.length(); i++) {
                                JSONObject result = results.getJSONObject(i);
                                result.getString("original_title");
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
                            }

                            mTextView.setText(movieList.toString());
                        } catch (JSONException e) {
                            mTextView.setText(e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mTextView.setText(error.toString());
                    }
                });
        VolleyApplication.getInstance().getRequestQueue().add(request);

    }

    public class movie {
        private String name;
        private double vote;
        private double popularity;
        private String overview;
        private String poster;
        private Calendar release;

        public movie(String name, double vote, double pop, String overview, String poster, String release) {
            this.name=name;
            this.vote=vote;
            this.popularity=pop;
            this.overview=overview;
            this.poster=poster;
            //this.release = release;
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
}
