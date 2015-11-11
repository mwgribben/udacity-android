package com.google.gribben.moviebrowser;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.GridView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by gribben on 9/25/15.
 */
public class movieGetter {

    List<movie> movieList = new ArrayList<movie>();
    List<String> trailerList = new ArrayList<String>();
    private String api_key = "de5f1d0c1639b0481cd6b3b3f9af0efd";

    private Context context;

    public movieGetter(Context context) {
        this.context=context;
    }


    public List getMovies () {

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
                                                result.getString("release_date"),
                                                result.getString("id")
                                        )
                                );
                            }
                            Log.d("GribTracking", "getmovies");


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
        return movieList;
    }

    public List getTrailers(String movieId) {
        JsonObjectRequest request = new JsonObjectRequest(
                "http://api.themoviedb.org/3/movie/"+movieId+"/videos?api_key="
                        +api_key,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray results;
                            results = response.getJSONArray("results");
                            for (int i = 0; i < results.length(); i++) {
                                JSONObject result = results.getJSONObject(i);
                                trailerList.add(result.getString("key"));
                            }
                            Log.d("GribTracking", "gettrailers");
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
        return trailerList;
    }


    public void raiseError(String errorString) {
        AlertDialog alertDialog = new AlertDialog.Builder(this.context).create();
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

}
