package com.globant.movies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.globant.movies.model.MoviesModel;
import com.globant.movies.network.NetworkService;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Movies";
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerSimpleScrollListener mScrollListener;

//    private MoviesModel mMovies;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MoviesModel.INSTANCE.getObservable().subscribe(position -> {
            runOnUiThread(() -> mAdapter.notifyItemRangeInserted(position.position,
                    position.size));
        });

        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new GridAdapter();
        mRecyclerView.setAdapter(mAdapter);


        mScrollListener = new RecyclerSimpleScrollListener();
        mRecyclerView.addOnScrollListener(mScrollListener);
        mScrollListener.getLastVisibleObservable()
                .subscribe(lastVisible -> MainActivity.this.getNextPage());

        getNextPage();
    }


    public void log(final String message) {
        runOnUiThread(() -> Log.d(TAG, message));
    }

    private void getNextPage() {
        if (MoviesModel.INSTANCE.getCurrentPage() < MoviesModel.INSTANCE.getTotalPages()) {
            NetworkService.INSTANCE.getPage(MoviesModel.INSTANCE.getCurrentPage() + 1).subscribe(
                    page -> {
                        MoviesModel.INSTANCE.appendMoviesFromPage(page);
                    },
                    e -> MainActivity.this.log("Error fetching page " + e));
        }
    }

}
