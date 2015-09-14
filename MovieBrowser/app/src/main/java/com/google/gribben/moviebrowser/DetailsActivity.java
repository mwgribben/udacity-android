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
        Picasso.with(getApplicationContext()).load("http://image.tmdb.org/t/p/w500" + poster).into(posterView);
        TextView titleView = (TextView) findViewById(R.id.detailTitle);
        TextView ratingView = (TextView) findViewById(R.id.detailRating);
        TextView releaseView = (TextView) findViewById(R.id.detailRelease);
        TextView synopsisView = (TextView) findViewById(R.id.detailSynopsis);

        titleView.setText(title);
        ratingView.setText(rating);
        releaseView.setText(release);
        synopsisView.setText(synopsis);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_details, menu);
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
