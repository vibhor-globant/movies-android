package com.globant.mymovies;

import android.database.Observable;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.globant.mymovies.model.Movies;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.android.schedulers.HandlerScheduler;

public class MainActivity extends AppCompatActivity {

    private Movies movies;
    private Handler backgroundHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BackgroundThread backgroundThread = new BackgroundThread();
        backgroundThread.start();
        backgroundHandler = new Handler(backgroundThread.getLooper());

        this.movies = new Movies(this);

        rx.Observable<String> urlStream = rx.Observable.just(Constants.NW.GET_PAGE_URL(movies.getCurrentPageIndex() + 1));

        urlStream.subscribeOn(HandlerScheduler.from(backgroundHandler))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {

                    }
                });


        movies = new Movies(this.getApplicationContext());

        movies.getNextPage(new Movies.GetNextPageCallback() {
            @Override
            public void onSuccess() {
                Log.d("Movies", "Got next page");
            }

            @Override
            public void onError() {
                Log.e("Movies", "Unable to get next page");
            }
        });
    }

    static class BackgroundThread extends HandlerThread {
        BackgroundThread() {
            super("Movies-BackgroundThread", Process.THREAD_PRIORITY_BACKGROUND);
        }
    }
}
