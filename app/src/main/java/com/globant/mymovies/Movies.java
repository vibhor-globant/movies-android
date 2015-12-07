package com.globant.mymovies;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by vibhor on 27/11/15.
 */
public class Movies {
    public ArrayList<Movie> mMovies;
    private Gson mGson;

    Movies() {
        this.mMovies = new ArrayList<Movie>();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("yyyy-MM-dd");
        mGson = gsonBuilder.create();
    }
    Movies(String json) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("yyyy-MM-dd");
        mGson = gsonBuilder.create();
        this.mMovies = mGson.fromJson(json, new TypeToken<ArrayList<Movie>>(){}.getType());
    }

    public void append(String json) {
        ArrayList<Movie> newMovies = mGson.fromJson(json, new TypeToken<ArrayList<Movie>>(){}.getType());
        this.mMovies.addAll(newMovies);
    }
}
