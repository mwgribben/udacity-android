package com.google.gribben.moviebrowser;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;

/**
 * Created by gribben on 9/10/15.
 */
public class movie implements Parcelable {
    String name;
    double vote;
    double popularity;
    String overview;
    String poster;
    String release;
    String id;

    public movie(String name, double vote, double pop,
                 String overview, String poster, String release, String id) {
        this.name=name;
        this.vote=vote;
        this.popularity=pop;
        this.overview=overview;
        this.poster=poster;
        this.release=release;
        this.id = id;
    }

    public movie(Parcel in) {
        String[] data = new String[5];
        in.readStringArray(data);
        this.name = data[0];
        this.overview=data[1];
        this.poster=data[2];
        this.release=data[3];
        this.id = data[4];

        double[] data2 = new double[2];
        in.readDoubleArray(data2);
        this.vote = data2[0];
        this.popularity = data2[1];

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {this.name,this.overview,this.poster,this.release,this.id});
        dest.writeDoubleArray(new double[] {this.vote,this.popularity});
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<movie> CREATOR = new Parcelable.Creator() {
        public movie createFromParcel(Parcel in) {
            return new movie(in);
        }

        public movie[] newArray(int size){
            return new movie[size];
        }
    };




}
