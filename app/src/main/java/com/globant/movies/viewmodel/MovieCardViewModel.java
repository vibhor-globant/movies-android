package com.globant.movies.viewmodel;

import android.content.Context;
import android.databinding.ObservableField;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.view.View;

import com.globant.movies.R;
import com.globant.movies.model.MoviesModel;
import com.globant.movies.model.Result;
import com.globant.movies.network.NetworkService;
import com.squareup.picasso.Picasso;

/**
 * Created by vibhor on 21/01/16.
 */
public class MovieCardViewModel implements ViewModel {
    public ObservableField<Drawable> posterImage;
    private BindablePicassoImageView bindablePicassoImageView;
    private CardClickListener clickListener;

    public MovieCardViewModel(int position, Context context, CardClickListener listener) {
        clickListener = listener;
        posterImage = new ObservableField<>();
        bindablePicassoImageView = new BindablePicassoImageView(posterImage, context.getResources());
        Result movie = MoviesModel.INSTANCE.getMovies().get(position);
        Picasso.with(context)
                .load(NetworkService.IMDB_POSTER_BASE_URL + movie.getPosterPath())
                .placeholder(R.drawable.movie)
                .error(R.drawable.movie_error)
                .resize(300, 400)
                .centerCrop()
                .into(bindablePicassoImageView);
    }

    @Override
    public void destroy() {

    }

    public void onCardClick(View view) {
        clickListener.onClick((CardView) view);
    }

    public interface CardClickListener {
        void onClick(CardView cardView);
    }
}
