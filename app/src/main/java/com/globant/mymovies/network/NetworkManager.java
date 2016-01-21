package com.globant.mymovies.network;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.globant.mymovies.Constants;
import com.globant.mymovies.OkHttpStack;

/**
 * Created by vibhor on 07/12/15.
 */
public enum NetworkManager {
    INSTANCE;

    private RequestQueue mRequestQueue;
    private NetworkManager() {
    }

    public void init(Context context) {
        this.mRequestQueue = Volley.newRequestQueue(context, new OkHttpStack());
    }

    public void getPage(int index, final GetPageCallback callback) {
        StringRequest request = new StringRequest(Request.Method.GET,
                Constants.NW.GET_PAGE_URL(index),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callback.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                    callback.onError();
            }
        });
        this.mRequestQueue.add(request);
    }

    public interface GetPageCallback {
        void onSuccess(String page);
        void onError();
    }
}
