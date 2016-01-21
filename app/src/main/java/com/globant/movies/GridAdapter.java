package com.globant.movies;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.globant.movies.model.MoviesModel;
import com.globant.movies.model.Result;
import com.globant.movies.network.NetworkService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by vibhor on 14/01/16.
 */
public class GridAdapter extends RecyclerView.Adapter<GridCardHolder> {
//    private ArrayList<Result> dataset;
    private int expandedPosition = -1;

    public GridAdapter() {
//        this.dataset = dataset;
    }

    @Override
    public GridCardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_card, parent, false);
        GridCardHolder ch = new GridCardHolder(v);
        return ch;
    }

    @Override
    public void onBindViewHolder(GridCardHolder holder, int position) {
        Result movie = MoviesModel.INSTANCE.getMovies().get(position);
        Picasso.with(holder.moviePoster.getContext())
                .load(NetworkService.IMDB_POSTER_BASE_URL + movie.getPosterPath())
                .placeholder(R.drawable.movie)
                .error(R.drawable.movie_error)
                .resize(300, 400)
                .centerCrop()
                .into(holder.moviePoster);
        holder.movieName.setText(movie.getTitle());
        holder.movieDescription.setText(movie.getOverview());

        if (position == expandedPosition) {
            holder.detailedLayout.setVisibility(View.VISIBLE);
        } else {
            holder.detailedLayout.setVisibility(View.GONE);
        }

        holder.cardView.setOnClickListener(v -> {
            Log.i("Movies", "Card " + position + " clicked");
            Intent intent = new Intent(holder.moviePoster.getContext(), DetailsActivity.class);
            intent.putExtra(DetailsActivity.EXTRA_POSITION, position);
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
                    (Activity) holder.moviePoster.getContext(),
                    holder.moviePoster,
                    "poster");
            holder.moviePoster.getContext().startActivity(intent, options.toBundle());
        });
    }

    @Override
    public int getItemCount() {
        return MoviesModel.INSTANCE.getMovies().size();
    }
}
