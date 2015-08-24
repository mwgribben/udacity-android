package com.google.gribben.appportfolio;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openSpotify(View button) {
        Context context = getApplicationContext();
        CharSequence text = "Spotify Opened!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

    }

    public void openScores(View button) {
        Context context = getApplicationContext();
        CharSequence text = "Scores Opened!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

    }

    public void openLibrary(View button) {
        Context context = getApplicationContext();
        CharSequence text = "Library Opened!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

    }

    public void openBigger(View button) {
        Context context = getApplicationContext();
        CharSequence text = "Build it Bigger Opened!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

    }

    public void openXYZ(View button) {
        Context context = getApplicationContext();
        CharSequence text = "XYZ Reader Opened!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

    }
    public void openCapstone(View button) {
        Context context = getApplicationContext();
        CharSequence text = "Capstone Opened!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

    }
}
