package com.google.gribben.moviebrowser;

import java.util.Calendar;

/**
 * Created by gribben on 9/10/15.
 */
public class movie {
    String name;
    double vote;
    double popularity;
    String overview;
    String poster;
    String release;

    public movie(String name, double vote, double pop, String overview, String poster, String release) {
        this.name=name;
        this.vote=vote;
        this.popularity=pop;
        this.overview=overview;
        this.poster=poster;
        this.release=release;
    }

    public String getRelease() {
        //return Integer.toString(release.get(Calendar.MONTH)) + "/" + Integer.toString(release.get(Calendar.DATE)) + "/" + Integer.toString(release.get(Calendar.YEAR));
        return release;
    }
}
