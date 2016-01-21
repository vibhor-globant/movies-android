package com.globant.movies.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.globant.movies.R;
import com.globant.movies.model.MoviesModel;

/**
 * Created by vibhor on 14/01/16.
 */
public class GridAdapter extends RecyclerView.Adapter<GridCardHolder> {
    public GridAdapter() {
    }

    @Override
    public GridCardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_card, parent, false);
        GridCardHolder ch = new GridCardHolder(v);
        return ch;
    }

    @Override
    public void onBindViewHolder(GridCardHolder holder, int position) {
        holder.setPosition(position);
    }

    @Override
    public int getItemCount() {
        return MoviesModel.INSTANCE.getMovies().size();
    }
}
