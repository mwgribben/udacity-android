package com.google.gribben.moviebrowser;

import android.app.Activity;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Bundle data = getIntent().getExtras();

        movie select = (movie) data.getParcelable("movie");

        ImageView posterView = (ImageView) findViewById(R.id.detailPoster);
        Picasso.with(getApplicationContext())
                .load("http://image.tmdb.org/t/p/w500" + select.poster)
                .error(R.drawable.no_poster).into(posterView);
        TextView titleView = (TextView) findViewById(R.id.detailTitle);
        TextView ratingView = (TextView) findViewById(R.id.detailRating);
        TextView releaseView = (TextView) findViewById(R.id.detailRelease);
        TextView synopsisView = (TextView) findViewById(R.id.detailSynopsis);

        titleView.setText(select.name);
        ratingView.setText("User Rating: " +select.vote + "/10");
        releaseView.setText("Release Date: " +select.release);
        synopsisView.setText(select.overview);

        movieGetter m = new movieGetter(this);

        trailerAdapter itemsAdapter = new trailerAdapter(this,m.getTrailers(select.id));
        ListView listView = (ListView) findViewById(R.id.trailerList);
        listView.setAdapter(itemsAdapter);
    }
}
