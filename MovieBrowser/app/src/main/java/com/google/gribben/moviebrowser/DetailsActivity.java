package com.google.gribben.moviebrowser;

import android.app.Activity;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Bundle data = getIntent().getExtras();
        String poster = data.getString("poster");
        String title = data.getString("title");
        String rating = data.getString("rating");
        String synopsis = data.getString("synopsis");
        String release = data.getString("release");

        ImageView posterView = (ImageView) findViewById(R.id.detailPoster);
        Picasso.with(getApplicationContext()).load("http://image.tmdb.org/t/p/w500" + poster).error(R.drawable.no_poster).into(posterView);
        TextView titleView = (TextView) findViewById(R.id.detailTitle);
        TextView ratingView = (TextView) findViewById(R.id.detailRating);
        TextView releaseView = (TextView) findViewById(R.id.detailRelease);
        TextView synopsisView = (TextView) findViewById(R.id.detailSynopsis);

        titleView.setText(title);
        ratingView.setText("User Rating: " +rating + "/10");
        releaseView.setText("Release Date: " +release);
        synopsisView.setText(synopsis);

    }
}
