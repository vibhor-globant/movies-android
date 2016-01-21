package com.globant.movies;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by vibhor on 14/01/16.
 */
public class GridCardHolder extends RecyclerView.ViewHolder {
    public ImageView moviePoster;
    public CardView cardView;
    public LinearLayout detailedLayout;
    public TextView movieName;
    public TextView movieDescription;

    public GridCardHolder(View itemView) {
        super(itemView);
        moviePoster = (ImageView)itemView.findViewById(R.id.moviePoster);
        cardView = (CardView)itemView.findViewById(R.id.card_view);
        detailedLayout = (LinearLayout)itemView.findViewById(R.id.detailedLayout);
        movieName = (TextView)itemView.findViewById(R.id.movieName);
        movieDescription = (TextView)itemView.findViewById(R.id.movieDescription);
    }
}
