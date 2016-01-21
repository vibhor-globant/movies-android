package com.globant.movies.network;

import com.globant.movies.model.Page;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by vibhor on 13/01/16.
 */
public enum NetworkService {
    INSTANCE;

    public static final String IMDB_POSTER_BASE_URL = "http://image.tmdb.org/t/p/w300";

        private static final String SERVER_BASE_URL = "http://192.168.5.29:3000/";
//    private static final String SERVER_BASE_URL = "http://172.19.5.95:3000/";
    private NetworkApi mApis;

    NetworkService() {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SERVER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        mApis = retrofit.create(NetworkApi.class);
    }

    public NetworkApi getApi() {
        return mApis;
    }

    public Observable<Page> getPage(int pageNum) {
        return mApis.getPage(pageNum)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
