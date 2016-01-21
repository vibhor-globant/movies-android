package com.globant.movies.view;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.globant.movies.databinding.MovieCardBinding;
import com.globant.movies.viewmodel.MovieCardViewModel;

/**
 * Created by vibhor on 14/01/16.
 */
public class GridCardHolder extends RecyclerView.ViewHolder {
    private MovieCardBinding binding;
    private MovieCardViewModel movieCardViewModel;
    private int position;

    public GridCardHolder(View itemView) {
        super(itemView);

        binding = DataBindingUtil.bind(itemView);
    }

    public void setPosition(int position) {
        this.position = position;

        movieCardViewModel = new MovieCardViewModel(position,
                binding.cardView.getContext(),
                this::startDetailsActivity);

        binding.setViewModel(movieCardViewModel);
    }

    private void startDetailsActivity(CardView cardView) {
        Intent intent = new Intent(cardView.getContext(), DetailsActivity.class);
        intent.putExtra(DetailsActivity.EXTRA_POSITION, position);
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
                (Activity) cardView.getContext(),
                binding.moviePoster,
                "poster");
        cardView.getContext().startActivity(intent, options.toBundle());
    }
}
