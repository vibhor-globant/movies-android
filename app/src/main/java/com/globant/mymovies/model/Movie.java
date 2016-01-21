package com.globant.mymovies.model;

import java.util.Date;

/**
 * Created by vibhor on 27/11/15.
 */
public class Movie {
    public boolean adult;
    public String backdrop_path;
    public int[] genre_ids;
    public String id;
    public String original_language;
    public String original_title;
    public String overview;
    public Date release_date;
    public String poster_path;
    public float popularity;
    public String title;
    public boolean video;
    public float vote_average;
    public int vote_count;
    public int like_count;
    public int unlike_count;

    public void like() {
        this.like_count++;
    }
    public void unlike() {
        this.unlike_count++;
    }
}
