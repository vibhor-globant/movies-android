package com.globant.movies;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.NavUtils;
import android.support.v4.graphics.ColorUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.globant.movies.model.MoviesModel;
import com.globant.movies.model.Result;
import com.globant.movies.network.NetworkService;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DetailsActivity extends AppCompatActivity {

    public final static String EXTRA_POSITION = "com.globant.movies.details.position";
    private ImageView moviePoster;
    private TextView movieName;
    private TextView movieDescription;
    private RelativeLayout rootLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        rootLayout = (RelativeLayout)findViewById(R.id.rootLayout);
        moviePoster = (ImageView)findViewById(R.id.moviePoster);
        movieName = (TextView)findViewById(R.id.movieName);
        movieDescription = (TextView)findViewById(R.id.movieDescription);

        Intent intent = getIntent();
        int position = intent.getIntExtra(DetailsActivity.EXTRA_POSITION, -1);
        if (position >= 0) {
            Result movie = MoviesModel.INSTANCE.getMovies().get(position);
            Picasso.with(getApplicationContext())
                    .load(NetworkService.IMDB_POSTER_BASE_URL + movie.getPosterPath())
                    .placeholder(R.drawable.movie)
                    .error(R.drawable.movie_error)
                    .resize(300, 400)
                    .centerCrop()
                    .into(moviePoster, new Callback() {
                        @Override
                        public void onSuccess() {
                            Bitmap bitmap = ((BitmapDrawable)moviePoster.getDrawable()).getBitmap();
                            if (bitmap != null && !bitmap.isRecycled()) {
                                Palette.from(bitmap).generate(p -> {
                                    List<Palette.Swatch> swatchList = p.getSwatches();
                                    if (swatchList != null && swatchList.size() > 0) {
                                        rootLayout.setBackgroundColor(swatchList.get(0).getRgb());
                                        movieName.setTextColor(swatchList.get(0).getTitleTextColor());
                                        movieDescription.setTextColor(swatchList.get(0).getBodyTextColor());

                                        Window window = DetailsActivity.this.getWindow();
                                        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                                        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                                        float[] hsl = swatchList.get(0).getHsl();
                                        hsl[2] *= 0.9;
                                        window.setStatusBarColor(ColorUtils.HSLToColor(hsl));
                                    }
                               });
                            }
                        }

                        @Override
                        public void onError() {

                        }
                    });
            movieName.setText(movie.getTitle());
            movieDescription.setText(movie.getOverview());
        } else {

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
