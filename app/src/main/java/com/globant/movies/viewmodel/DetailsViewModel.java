package com.globant.movies.viewmodel;

import android.content.Context;
import android.databinding.Observable;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.ColorUtils;
import android.support.v7.graphics.Palette;

import com.globant.movies.R;
import com.globant.movies.model.MoviesModel;
import com.globant.movies.model.Result;
import com.globant.movies.network.NetworkService;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by vibhor on 21/01/16.
 */
public class DetailsViewModel implements ViewModel {
    public ObservableField<String> movieName;
    public ObservableField<String> movieDescription;
    public ObservableField<Drawable> posterImage;
    public ObservableInt backgroundColor;
    public ObservableInt titleColor;
    public ObservableInt detailsColor;

    private BindablePicassoImageView bindablePicassoImageView;
    private PicassoListener picassoListener;

    public DetailsViewModel(int position, Context context, PicassoListener listener) {
        picassoListener = listener;
        Result movie = MoviesModel.INSTANCE.getMovies().get(position);
        movieName = new ObservableField<>(movie.getTitle());
        movieDescription = new ObservableField<>(movie.getOverview());
        backgroundColor = new ObservableInt(0xffffff);
        titleColor = new ObservableInt(0x000000);
        detailsColor = new ObservableInt(0x000000);

        posterImage = new ObservableField<>();
        bindablePicassoImageView = new BindablePicassoImageView(posterImage, context.getResources());

        posterImage.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                Drawable drawable = posterImage.get();
                if (drawable != null) {
                    Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
                    if (bitmap != null && !bitmap.isRecycled()) {
                        Palette.from(bitmap).generate(p -> {
                            List<Palette.Swatch> swatchList = p.getSwatches();
                            if (swatchList != null && swatchList.size() > 0) {
                                DetailsViewModel.this.backgroundColor.set(swatchList.get(0).getRgb());
                                DetailsViewModel.this.titleColor.set(swatchList.get(0).getTitleTextColor());
                                DetailsViewModel.this.detailsColor.set(swatchList.get(0).getBodyTextColor());
                                float[] hsl = swatchList.get(0).getHsl();
                                hsl[2] *= 0.9;
                                picassoListener.onChangeStatusBarColor(ColorUtils.HSLToColor(hsl));
                            }
                        });
                    }
                }
            }
        });

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

    public interface PicassoListener {
        void onChangeStatusBarColor(int color);
    }

}
