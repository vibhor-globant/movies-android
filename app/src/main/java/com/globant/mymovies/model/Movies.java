package com.globant.mymovies.model;

import android.content.Context;

import com.android.volley.Network;
import com.globant.mymovies.network.NetworkManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by vibhor on 27/11/15.
 */
public class Movies {
    public ArrayList<Movie> mMovies;
    private Gson mGson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
    private Type mGsonClassType = new TypeToken<ArrayList<Movie>>(){}.getType();
    private int mCurrentPage = 0;

    public Movies(Context context) {
        this.mMovies = new ArrayList<>();
        NetworkManager.INSTANCE.init(context);
    }
    public Movies(String json, Context context) {
        this.mMovies = mGson.fromJson(json, this.mGsonClassType);
        this.mCurrentPage = 0;
        NetworkManager.INSTANCE.init(context);
    }

    public void append(String json) {
//        ArrayList<Movie> newMovies = mGson.fromJson(json, this.mGsonClassType);
        Page page = mGson.fromJson(json, new TypeToken<Page>(){}.getType());
//        this.mMovies.addAll(newMovies);
        this.mMovies.addAll(page.page.results);
    }

    public void getNextPage(final GetNextPageCallback callback) {
        NetworkManager.INSTANCE.getPage(this.mCurrentPage + 1, new NetworkManager.GetPageCallback() {
            @Override
            public void onSuccess(String page) {
                Movies.this.append(page);
                Movies.this.mCurrentPage++;
                callback.onSuccess();
            }
            @Override
            public void onError() {
                callback.onError();
            }
        });
    }

    public int getCurrentPageIndex() {
        return this.mCurrentPage;
    }

    public interface GetNextPageCallback {
        public void onSuccess();
        public void onError();
    }
}
